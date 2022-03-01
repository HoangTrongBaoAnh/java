package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
