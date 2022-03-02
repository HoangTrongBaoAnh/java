package com.springboot.first.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
