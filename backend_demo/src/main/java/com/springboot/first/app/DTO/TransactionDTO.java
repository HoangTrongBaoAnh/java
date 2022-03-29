package com.springboot.first.app.DTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.first.app.model.TransactionCategory;

public interface TransactionDTO {
	
	public Long getid();
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	public Date gettransaction_date();
	
	
	public String getfroms();
	
	
	public String gettos();
	
	
	public BigInteger getamount();
	
	public String getdescription();
	
	public Long gettrans_category_id();
	
	public String getcategory();
	
	public String gettranscmonth();
	
}
