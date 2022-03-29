package com.springboot.first.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.first.app.model.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	Card findBycardnumber(String cardnumber);
	
	@Query(value = "select * from cards WHERE cardnumber= :cardnumber and bank_id=:id",nativeQuery=true)
	Card findByIdAndcardnumber(@Param("id") long Id,@Param("cardnumber") String cardnumber);
}
