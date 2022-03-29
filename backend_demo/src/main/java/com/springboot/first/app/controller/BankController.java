package com.springboot.first.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.DTO.BankDTO;
import com.springboot.first.app.DTO.categoryDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.helper.upload;
import com.springboot.first.app.model.Bank;
import com.springboot.first.app.model.Category;
import com.springboot.first.app.repository.BankRepository;
@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api/bank")
public class BankController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@GetMapping
	public ResponseEntity<List<Bank>> getall(){
		return new ResponseEntity<List<Bank>>(bankRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Bank> creaBank(@ModelAttribute BankDTO bankDTO) throws IOException{
		Bank bank = new Bank();
		bank.setName(bankDTO.getName());
		upload upload = new upload();
		bank.setUrl(upload.uploadImage(bankDTO.getUrl()));
		return new ResponseEntity<Bank>(bankRepository.save(bank),HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Bank> bankupdate(@ModelAttribute BankDTO bankDTO,@PathVariable long id) throws IOException{
		Bank bank = bankRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("bank", "id", id));
		bank.setName(bankDTO.getName());
		bank.setUrl(bank.getUrl());
		if(bankDTO.getUrl() != null) {
			upload upload = new upload();
			bank.setUrl(upload.uploadImage(bankDTO.getUrl()));
		}
		bankRepository.save(bank);
		return new ResponseEntity<Bank>(bank,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBank(@PathVariable long id){
		bankRepository.deleteById(id);
		return new ResponseEntity<String>("delete successfully",HttpStatus.OK);
	}
}
