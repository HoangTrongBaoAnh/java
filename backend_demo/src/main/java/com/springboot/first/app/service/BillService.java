package com.springboot.first.app.service;

import java.util.List;

import com.springboot.first.app.DTO.BillInfoDTO;
import com.springboot.first.app.model.BillInfo;

public interface BillService {
	List<BillInfo> getBillInfos();
	BillInfo findbyusercode(String usercode,long category_id);
	BillInfo creBillInfo(BillInfoDTO billInfoDTO);
	BillInfo updaBillInfo(Long id);
	void deleBillInfo(long id);
}
