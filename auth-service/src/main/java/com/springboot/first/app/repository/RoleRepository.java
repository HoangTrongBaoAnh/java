package com.springboot.first.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.Model.ERole;
import com.springboot.first.app.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(ERole name);

}
