package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{

}
