package com.springboot.first.app.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.DTO.TransactionDTO;
import com.springboot.first.app.DTO.TransactionDtoAdd;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.User;
import com.springboot.first.app.model.Wallet;
import com.springboot.first.app.model.Card;
import com.springboot.first.app.model.Transaction;
import com.springboot.first.app.model.TransactionCategory;
import com.springboot.first.app.repository.CardRepository;
import com.springboot.first.app.repository.TransactionCategoryRepository;
import com.springboot.first.app.repository.TransactionRepository;
import com.springboot.first.app.repository.UserRepository;
import com.springboot.first.app.repository.WalletRepository;
import com.springboot.first.app.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionsController {
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@PostMapping("/balance/{id}")
	public ResponseEntity<User> updateBalence(@PathVariable long id,@RequestBody User user){
		User usert = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		if(usert.getBalance() != null) {
			BigDecimal sum;
			BigDecimal reveiverbalance = usert.getBalance();
			sum = reveiverbalance.add(user.getBalance());
			usert.setBalance(sum);
		}
		else {
			usert.setBalance(user.getBalance());
		}
		
		userRepository.save(usert);
		
		
		return new ResponseEntity<User>(usert,HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BigDecimal> getBalance(@PathVariable long id){
		User usert = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		
		
		return new ResponseEntity<BigDecimal>(usert.getBalance(),HttpStatus.OK);
		
	}
	
	@GetMapping("/alltransaction/{id}")
	public ResponseEntity<Map<String, Object>> gettransactions(@PathVariable long id,@RequestParam(defaultValue = "0") int page){
		
		try {
			List<TransactionDTO> transactionDTOs = new ArrayList<TransactionDTO>();
			Pageable paging = PageRequest.of(page, 8);
			Page<TransactionDTO> pageTransc = userRepository.findallTransactions(id,paging);
			
			transactionDTOs = pageTransc.getContent();
			
//			List<TransactionCategory> transactionCategories = new ArrayList<>();
//			transactionDTOs.forEach(item -> {
//				TransactionCategory trans = transactionCategoryRepository.findById(item.gettrans_category_id()).orElseThrow(()->new ResourceNotFoundException("cate","id",item.gettrans_category_id()));
//				transactionCategories.add(trans);
//			});
			//transactionDTOs.(transactionCategories);
			//TransactionCategory category = transactionCategoryRepository.findById(trnTransaction.TransactionCategory_id).orElseThrow(()->new ResourceNotFoundException("cate","id",trnTransaction.TransactionCategory_id));
			
			Map<String, Object> response = new HashMap<>();
		      response.put("transactions", transactionDTOs);
		      //response.put("categories", transactionCategories);
		      response.put("currentPage", pageTransc.getNumber());
		      response.put("totalItems", pageTransc.getTotalElements());
		      response.put("totalPages", pageTransc.getTotalPages());
		      return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		catch(Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
		
//		return new ResponseEntity<List<TransactionDTO>>(userRepository.findallTransactions(id),HttpStatus.OK);
		
	}
	
	@PostMapping("/cashin/{cardnumber}")
	public ResponseEntity<Map<String, Object>> cashin(@RequestBody TransactionDtoAdd trnTransaction, @PathVariable String cardnumber){
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nameString = userDetails.getUsername();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new ResourceNotFoundException("User","name",nameString));
		Transaction transaction = new Transaction();
		transaction.setAmount(trnTransaction.getAmount());
		transaction.setUser(user);
		transaction.setFrom(user.getUsername());
		
		Wallet wallet = walletRepository.findBycardNumber(cardnumber);
		Card card = cardRepository.findBycardnumber(cardnumber);
		
		if(wallet == null || card == null) {
			Map<String, Object> response = new HashMap<>();
		      response.put("error", "card or id not found");
		      //response.put("categories", transactionCategories);
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		TransactionCategory transactionCategory = transactionCategoryRepository.findById(trnTransaction.getTransactionCategory_id()).orElseThrow(()->new ResourceNotFoundException("cate","id",trnTransaction.TransactionCategory_id));
		if(transactionCategory.getName().equals("topup")) {
			
			
			BigDecimal sum = user.getBalance().add(trnTransaction.getAmount());
			user.setBalance(sum);
			userRepository.save(user);
			
			BigDecimal substract = wallet.getBalance().subtract(trnTransaction.getAmount());
			if(substract.signum() != 1) {
				Map<String, Object> response = new HashMap<>();
			      response.put("error", "balance not enough to process this transaction");
			      //response.put("categories", transactionCategories);
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			wallet.setBalance(substract);
			walletRepository.save(wallet);
			
			card.setBalance(substract);
			cardRepository.save(card);
		}
		else {
			BigDecimal sum = user.getBalance().subtract(trnTransaction.getAmount());
			user.setBalance(sum);
			userRepository.save(user);
			
			BigDecimal substract = wallet.getBalance().add(trnTransaction.getAmount());
			wallet.setBalance(substract);
			walletRepository.save(wallet);
			
			card.setBalance(substract);
			cardRepository.save(card);
			
			
		}
		
		
		
		Date date = new Date();
		
		transaction.setTransactionDate(date);
		
		TransactionCategory category = transactionCategoryRepository.findById(trnTransaction.TransactionCategory_id).orElseThrow(()->new ResourceNotFoundException("cate","id",trnTransaction.TransactionCategory_id));
		
		transaction.setTransactionCategory(category);
		transactionRepository.save(transaction);

		Map<String, Object> response = new HashMap<>();
	      response.put("transaction", transaction);
	      //response.put("categories", transactionCategories);
	    return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<Transaction> trans(@RequestBody TransactionDtoAdd trnTransaction){
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nameString = userDetails.getUsername();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new ResourceNotFoundException("User","name",nameString));
		Transaction transaction = new Transaction();
		transaction.setAmount(trnTransaction.getAmount());
		transaction.setUser(user);
		transaction.setFrom(user.getUsername());
		transaction.setDescription(trnTransaction.getDescription());
		User receiver = userRepository.findByUsername(trnTransaction.getTo()).orElseThrow(()->new ResourceNotFoundException("User","name",nameString));
		BigDecimal sum;
		transaction.setTo(receiver.getUsername());
		BigDecimal reveiverbalance = receiver.getBalance();
		sum = reveiverbalance.add(trnTransaction.getAmount());
		receiver.setBalance(sum);
		userRepository.save(receiver);
		
		TransactionCategory transactionCategory = transactionCategoryRepository.findById(trnTransaction.getTransactionCategory_id()).orElseThrow(()->new ResourceNotFoundException("cate","id",trnTransaction.TransactionCategory_id));
		if(transactionCategory.getName().equals("topup")) {
			
			sum = user.getBalance().add(trnTransaction.getAmount());
			user.setBalance(sum);
		}
		else {
			BigDecimal substract;
			substract = user.getBalance().subtract(transaction.getAmount());
			user.setBalance(substract);
		}
		
		userRepository.save(user);
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//		DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
//		LocalDate localDate = LocalDate.now();
		transaction.setTransactionDate(date);
		
		TransactionCategory category = transactionCategoryRepository.findById(trnTransaction.TransactionCategory_id).orElseThrow(()->new ResourceNotFoundException("cate","id",trnTransaction.TransactionCategory_id));
		
		transaction.setTransactionCategory(category);
		
		
		transactionRepository.save(transaction);
		return new ResponseEntity<Transaction>( transaction,HttpStatus.CREATED);
		
	}
}
