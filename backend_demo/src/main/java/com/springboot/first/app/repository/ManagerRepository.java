package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Long>{
	
}
