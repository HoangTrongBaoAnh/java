package com.springboot.first.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.first.app.DTO.TransactionDTO;
import com.springboot.first.app.model.BillInfo;

@Repository
public interface BIllInfoRepository extends JpaRepository<BillInfo, Long>{
	//Optional<BillInfo> findBycustomercode(String username);
	
	@Query(value = "select * from bill_infos,categories WHERE categories.id=bill_infos.cagetory_id and customer_code=:customercode and cagetory_id=:id",nativeQuery=true)
	Optional<BillInfo> findBycustomercode(@Param("customercode") String customercode,@Param("id") Long id);
}
