package com.springboot.first.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
}
