package com.ABCJobsPortal.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ABCJobsPortal.model.BulkEmail;
import com.ABCJobsPortal.model.City;
import com.ABCJobsPortal.model.UserDetails;
import com.ABCJobsPortal.repository.CityRepository;
import com.ABCJobsPortal.service.BulkEmailService;
import com.ABCJobsPortal.service.UserDetailsService;
import com.ABCJobsPortal.service.UsersService;

import helper.Profile;

@Controller
public class AdminController {
	@Autowired
	UsersService us;
	
	@Autowired
	UserDetailsService ud;
	
	@Autowired
	BulkEmailService bs;
	
	@Autowired
	CityRepository cr;
	
	@RequestMapping(value="/admin") 
	public ModelAndView index(Model model, HttpSession session) {
		String name = ud.getDetailsById(session.getAttribute("userId").toString()).split(",")[1];
		model.addAttribute("adminName", name);
		return new ModelAndView("administrator/index");
	}
	
	@RequestMapping(value="/admin/send-bulk")
	public ModelAndView sendBulk(@ModelAttribute("sendBulk") BulkEmail bulkEmail, HttpSession session) {
		return new ModelAndView("administrator/send-bulk");
	}
	
	@RequestMapping(value="/admin/send-bulk", method = RequestMethod.POST)
	public String sb(@ModelAttribute("sendBulk") BulkEmail bulkEmail, HttpSession session) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		
		bulkEmail.setSendBy("1");
		bulkEmail.setCreatedAt(dtf.format(now));
		bs.sendEmail(bulkEmail.getEmailSubject(), bulkEmail.getEmailBody(), null);
		
		bs.saveToDB(bulkEmail);
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/admin/all-users") 
	public ModelAndView allUsers(Model model, HttpSession session) {
		List<UserDetails> users = ud.getAllUserDetails();
		String cd = null;
		for(City c: cr.findAll()) {
			if(users.get(1).getCityId().equals(c.getCityId().toString())) {
				cd = c.getCountry().getCountryName();
			}
		}
		System.out.println("OK " + cd);
		model.addAttribute("users", users);
		model.addAttribute("cities", cr.findAll());
		return new ModelAndView("administrator/all-users");
	}
	
	@RequestMapping(value="/admin/delete/{id}") 
	public String deleteUserById(@PathVariable("id") Long id, Model model) {
		boolean isDeleted = us.deleteUserById(id);
		if(isDeleted) {
			model.addAttribute("err", "Cannot delete this user");
		}
		
		return "redirect:/admin/all-users";
	}
	
	@RequestMapping(value = "/admin/profile/{id}")
	public ModelAndView profile(@PathVariable("id") Long id, Model model, HttpSession session, Profile profile) {
		UserDetails userDetails = ud.getDetailsById(id);
		
		profile.setId(userDetails.getUserDetailsId());
		profile.setFirstName(userDetails.getFirstName());
		profile.setLastName(userDetails.getLastName());
		profile.setFullName(userDetails.getFirstName() + " " + userDetails.getLastName());
		profile.setTitle(userDetails.getTitle());
		profile.setAbout(userDetails.getAbout());
		profile.setCompany(userDetails.getCompanyName());
		profile.setEmail(userDetails.getUser().getEmail());
		profile.setWebsite(userDetails.getWebsite());

		
		this.setModel(profile, model, session);
		return new ModelAndView("administrator/user-profile");
	}
	
	@RequestMapping(value="admin/profile/update-profile", method = RequestMethod.POST) // update profile POST
	public String up(@ModelAttribute("profile") UserDetails userDetails, Model model, HttpSession session) {
		Long userDetailsId = userDetails.getUserDetailsId();
		ud.updateProfile(userDetailsId, userDetails);
		return "redirect:../all-users";
	}
	
	private void setModel(Profile profile, Model model, HttpSession session) {
		model.addAttribute("id", profile.getId());
		model.addAttribute("f", profile.getFirstName().charAt(0));
		model.addAttribute("l", profile.getLastName().charAt(0));
		
		model.addAttribute("firstName", profile.getFirstName());
		model.addAttribute("lastName", profile.getLastName());
		
		model.addAttribute("fullName", profile.getFullName());
		model.addAttribute("title", profile.getTitle());
		model.addAttribute("about", profile.getAbout());
		model.addAttribute("company", profile.getCompany());
		model.addAttribute("email", profile.getEmail());
		model.addAttribute("website", profile.getWebsite());
	}
}
