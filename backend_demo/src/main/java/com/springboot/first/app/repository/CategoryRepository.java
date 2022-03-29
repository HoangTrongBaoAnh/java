package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
