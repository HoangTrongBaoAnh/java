package com.springboot.first.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.first.app.DTO.BillInfoDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.BillInfo;
import com.springboot.first.app.model.Category;
import com.springboot.first.app.repository.BIllInfoRepository;
import com.springboot.first.app.repository.CategoryRepository;
import com.springboot.first.app.service.BillService;

@Service
public class BIllServiceImpl implements BillService{
	@Autowired
	private BIllInfoRepository bIllInfoRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public BillInfo findbyusercode(String usercode,long category_id) {
		// TODO Auto-generated method stub
		return bIllInfoRepository.findBycustomercode(usercode,category_id).orElseThrow(()->new ResourceNotFoundException("bill","id",usercode));
	}

	@Override
	public BillInfo creBillInfo(BillInfoDTO billInfoDTO) {
		BillInfo billInfo = new BillInfo();
		billInfo.setAddress(billInfoDTO.getAddress());
		billInfo.setAmount(billInfoDTO.getAmount());
		billInfo.setCustomercode(billInfoDTO.getCustomer_code());
		billInfo.setStatus(false);
		Category category = categoryRepository.findById(billInfoDTO.getCategory_id()).orElseThrow();
		billInfo.setCategory(category);
		bIllInfoRepository.save(billInfo);
		return billInfo;
	}

	@Override
	public void deleBillInfo(long id) {
		bIllInfoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("BillInfo", "id", id));
		bIllInfoRepository.deleteById(id);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BillInfo> getBillInfos() {
		// TODO Auto-generated method stub
		return bIllInfoRepository.findAll();
	}

	@Override
	public BillInfo updaBillInfo(Long id) {
		BillInfo billInfo = bIllInfoRepository.findById(id).orElseThrow();
		billInfo.setStatus(true);
		bIllInfoRepository.save(billInfo);
		return bIllInfoRepository.save(billInfo);
	}

}
