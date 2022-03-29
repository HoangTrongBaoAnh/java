package com.springboot.first.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.DTO.BillInfoDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.BillInfo;
import com.springboot.first.app.model.Category;
import com.springboot.first.app.repository.BIllInfoRepository;
import com.springboot.first.app.repository.CategoryRepository;
import com.springboot.first.app.service.BillService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bill")
public class BillController {
	@Autowired
	private BillService billService;
	
	@GetMapping
	public ResponseEntity<List<BillInfo>> getallbills(){
		
		return new ResponseEntity<List<BillInfo>>(billService.getBillInfos(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/category/{category_id}")
	public ResponseEntity<BillInfo> getbill(@PathVariable String id,@PathVariable Long category_id){
		
		//categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("category","id",category_id));
		
		return new ResponseEntity<BillInfo>(billService.findbyusercode(id, category_id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<BillInfo> createbill(@RequestBody BillInfoDTO billInfoDTO){
		
		return new ResponseEntity<BillInfo>(billService.creBillInfo(billInfoDTO),HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<BillInfo> updatebill(@PathVariable long id){
		
		return new ResponseEntity<BillInfo>(billService.updaBillInfo(id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCagetory(@PathVariable long id){
		billService.deleBillInfo(id);
		return new ResponseEntity<String>("delete successfully",HttpStatus.OK);
	}
}
