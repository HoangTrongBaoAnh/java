package com.springboot.first.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.first.app.DTO.TransactionDTO;
import com.springboot.first.app.model.Transaction;
import com.springboot.first.app.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
	List<User> findByUsernameContaining(String title);
	
	@Query(value = "select DISTINCT MONTH(transaction_date) as transcmonth,transactions.*,name as category from transactions, users, transaction_categories WHERE users.id=transactions.user_id and transaction_categories.id=trans_category_id and user_id= :id order by transactions.id DESC",nativeQuery=true)
	Page<TransactionDTO> findallTransactions(@Param("id") long id,Pageable pageable);

}
