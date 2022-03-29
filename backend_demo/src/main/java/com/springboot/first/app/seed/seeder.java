package com.springboot.first.app.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.springboot.first.app.model.ERole;
import com.springboot.first.app.model.Role;
import com.springboot.first.app.repository.RoleRepository;

@Component
public class seeder implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		initData();
		
	}
	
	private void initData() {
		if(roleRepository.count() <= 0) {
			Role role1 = new Role();
			role1.setName(ERole.ROLE_USER);
			roleRepository.save(role1);
			
			Role role2 = new Role();
			role2.setName(ERole.ROLE_ADMIN);
			roleRepository.save(role2);
			
			Role role3 = new Role();
			role3.setName(ERole.ROLE_MODERATOR);
			roleRepository.save(role3);
//			INSERT INTO roles(name) VALUES('ROLE_USER');
//			INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
//			INSERT INTO roles(name) VALUES('ROLE_ADMIN');
		}
		
		System.out.println(roleRepository.count());
	}

}
