package com.springboot.first.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
