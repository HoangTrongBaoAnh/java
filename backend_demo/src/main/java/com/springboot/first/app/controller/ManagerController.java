package com.springboot.first.app.controller;

import java.util.List;

import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.model.Manager;
import com.springboot.first.app.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	private ManagerService managerService;
	
	public ManagerController(ManagerService managerService) {
		super();
		this.managerService = managerService;
	}

	@GetMapping()
	public List<Manager> getManagers(){
		return managerService.getManagers();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Manager> getSingleManager(@PathVariable long id){
		return new ResponseEntity<Manager>(managerService.getSingleManager(id), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Manager> saveManager(@RequestBody Manager manager){
		return new ResponseEntity<Manager>(managerService.saveManager(manager), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Manager> editManager(@RequestBody Manager manager,@PathVariable long id){
		return new ResponseEntity<Manager>(managerService.editManager(manager, id), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		managerService.deleteManager(id);
		return new ResponseEntity<String>("Delete succesfully", HttpStatus.OK);
	}
	
	
}
