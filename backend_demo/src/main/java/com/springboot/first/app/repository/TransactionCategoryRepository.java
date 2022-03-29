package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.TransactionCategory;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>{

}
