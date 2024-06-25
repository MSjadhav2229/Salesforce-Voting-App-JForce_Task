package com.example.jforce.Controller;
//https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security

//implementation 'org.springframework.boot:spring-boot-starter-security:3.1.1'
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jforce.Entity.Admin;
import com.example.jforce.Entity.User;
import com.example.jforce.repository.AdminRepository;
import com.example.jforce.repository.UserRepository;
import com.example.jforce.service.AdminService;
import com.example.jforce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller

public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserService service;

	@Autowired
	private AdminService adminService;

//	@GetMapping("/")
//	public String homePage() {
//		return "Home.html";
//
//	}

//	@GetMapping("/Registrationn")
//	public String registrationPagE(User user) {
//		return "Registration.html";
//	}

	@GetMapping("/")
	public String registrationPagE(User user) {
		return "Registration.html";
	}

	@PostMapping("/register")
	public String show2(@ModelAttribute("user") User user) {
		System.err.println(user);
		userRepo.save(user);
		System.out.println(user);
		return "redirect:/";
	}

	@GetMapping("/loginCredential")
	public String loginDirectry(User user) {
		return "login.html";
	}

	@PostMapping("/loginCredential")

	public String loginProcess(@RequestParam("emailid") String emailid, @RequestParam("password") String password) {
		System.out.println(emailid);
		System.out.println(password);
		User DB_emailid = service.getUserByEmailid(emailid);
		System.out.println(DB_emailid);
		User DB_password = service.getUserByPassword(password);
		User user = userRepo.findByEmailid(emailid);
		int repeaterValue = user.getRepeater();

		if ("admin@admin.com".equals(emailid) && "admin".equals(password)) {
			return "redirect:/pieChart";
		} else if (DB_password != null && password.equals(DB_password.getPassword()) && repeaterValue != 0) {
			return "loginfail"; // you already voted
		} else if (DB_password != null && password.equals(DB_password.getPassword()) && repeaterValue == 0) {
			service.incrementvote_counting(DB_emailid); // Save the updated user
			return "Voting Booth";
		}

		return "logincrediatialmsg"; // YOU PUTTING LOGIN CREDENTIAL IS WRONG PLEASE TRY AGAIN ...!!
	}

	@PostMapping("/vote")
	public ResponseEntity<String> vote(@RequestParam String candidates) {
		Admin admin = adminRepository.findByCandidates(candidates);
		if (admin != null) {
			adminService.incrementVotingCount(candidates);
//			service.incrementvote_counting(DB_emailid);

			return ResponseEntity.ok("Vote counted for " + candidates);
		} else {
			return ResponseEntity.badRequest().body("Candidate not found");
		}

	}

	@GetMapping("/pieChart")
	public String pieChart(Model model) {
		model.addAttribute("Candidate_1", adminRepository.getReferenceById(1).getVotingcount());
		model.addAttribute("Candidate_2", adminRepository.getReferenceById(2).getVotingcount());
		model.addAttribute("Candidate_3", adminRepository.getReferenceById(3).getVotingcount());
		model.addAttribute("Candidate_4", adminRepository.getReferenceById(4).getVotingcount());

		return "Chart"; // //apache echart

//		return "pieChart"; //highchart
	}

		@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
		public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
			return "redirect:/loginCredential";
		}

}