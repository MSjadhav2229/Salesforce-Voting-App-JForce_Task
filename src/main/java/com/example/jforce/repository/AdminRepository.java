package com.example.jforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jforce.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByCandidates(String candidates);

}
