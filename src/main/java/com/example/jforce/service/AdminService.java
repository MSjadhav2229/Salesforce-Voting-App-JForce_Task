package com.example.jforce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jforce.Entity.Admin;
import com.example.jforce.Entity.User;
import com.example.jforce.repository.AdminRepository;
import com.example.jforce.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	public void incrementVotingCount(String candidateName) {
		Admin admin = adminRepository.findByCandidates(candidateName);
		if (admin != null) {
			admin.setVotingcount(admin.getVotingcount() + 1);
			adminRepository.save(admin);
		}
	}
	
//	public void incrementvote_counting(String emailid) {
//		User user = userRepository.findByEmailid(emailid);
//		if (user!=null) {
////			user.setVoteCounting(user.getVoteCounting()+1);
//			user.setRepeater(user.getRepeater()+1);
//			userRepository.save(user);
//		}
//	}
}
