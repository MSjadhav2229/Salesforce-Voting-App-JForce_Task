package com.example.jforce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jforce.Entity.User;
import com.example.jforce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User getUserByEmailid(String emailid) {
		return repository.findByEmailid(emailid);

	}

	public User getUserByPassword(String password) {
		return repository.findByPassword(password);
	}

	public void incrementvote_counting(User user) {
        if (user != null) {
            user.setRepeater(user.getRepeater() +1);
            repository.save(user); // Update the user in the database
        }
    }



}
