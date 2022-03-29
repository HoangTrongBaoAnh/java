package com.springboot.first.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.DTO.CardDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.Bank;
import com.springboot.first.app.model.Card;
import com.springboot.first.app.model.User;
import com.springboot.first.app.model.Wallet;
import com.springboot.first.app.repository.BankRepository;
import com.springboot.first.app.repository.CardRepository;
import com.springboot.first.app.repository.UserRepository;
import com.springboot.first.app.repository.WalletRepository;
import com.springboot.first.app.service.impl.UserDetailsImpl;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/card")
public class CardController {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<List<Card>> getall(){
		return new ResponseEntity<List<Card>>(cardRepository.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/wallet")
	public ResponseEntity<List<Wallet>> getallwallet(){
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new ResourceNotFoundException("User","name",userDetails.getUsername()));
		
		return new ResponseEntity<List<Wallet>>(walletRepository.findAllByUserId(user.getId()),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Card> creaBank(@ModelAttribute CardDTO cardDTO){
		Card card = new Card();
		card.setBalance(cardDTO.getBalance());
		card.setCardnumber(cardDTO.getCardnumber());
		card.setSecuritycode(cardDTO.getSecuritycode());
		Bank bank = bankRepository.findById(cardDTO.getBank_id()).orElseThrow(()-> new ResourceNotFoundException("Bank", "id", cardDTO.getBank_id()));
		card.setBank(bank);
		return new ResponseEntity<Card>(cardRepository.save(card),HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Card> updaBank(@PathVariable long id,@ModelAttribute CardDTO cardDTO){
		Card card = cardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("card", "id", id));
		card.setBalance(cardDTO.getBalance());
		card.setCardnumber(cardDTO.getCardnumber());
		card.setSecuritycode(cardDTO.getSecuritycode());
		Bank bank = bankRepository.findById(cardDTO.getBank_id()).orElseThrow(()-> new ResourceNotFoundException("Bank", "id", cardDTO.getBank_id()));
		card.setBank(bank);
		return new ResponseEntity<Card>(cardRepository.save(card),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleBank(@PathVariable long id){
		Card card = cardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("card", "id", id));
		cardRepository.delete(card);
		return new ResponseEntity<String>("deletesuccessfully",HttpStatus.OK);
	}
	
	@PostMapping("/addtowallet/{id}/bank/{bank_id}/cardnumber/{cardnumber}")
	public ResponseEntity<String> addcardtowallet(@PathVariable String cardnumber, @PathVariable long id,@PathVariable long bank_id){
		Card card = cardRepository.findByIdAndcardnumber(bank_id, cardnumber);
		if(card == null) {
			return new ResponseEntity<String>("Card not found",HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user_id", "id", id));
		Wallet wallet2 = new Wallet();
		wallet2.setCard(card);
		
		List<Wallet> wallets = walletRepository.findAllByUserId(id);
		
		for(Wallet wallet : wallets) {
			if(wallet.getCardNumber().equals(cardnumber)) {
				return new ResponseEntity<String>("This card already in ur wallet",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if(wallets.size() > 0) {
			wallet2.setActive(false);
		}
		else {
			wallet2.setActive(true);
		}
		
		//wallet2.setActive(false);
		wallet2.setBalance(card.getBalance());
		wallet2.setCardNumber(card.getCardnumber());
		wallet2.setUser(user);
		walletRepository.save(wallet2);
		return new ResponseEntity<String>("add succesfully",HttpStatus.OK);
		
		
	}
	
	@PostMapping("/remove/wallet/{cardnumber}")
	public ResponseEntity<String> removefromwallet(@PathVariable String cardnumber){
		Wallet wallet = walletRepository.findBycardNumber(cardnumber);
//		Card card = cardRepository.findBycardnumber(cardnumber);
//		
//		card.setWallet(null);
//		cardRepository.save(card);
		if(wallet == null) {
			return new ResponseEntity<String>("wallet not found",HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		User user = wallet.getUser();
		boolean active = wallet.getactive();
		if(active==false) {
			walletRepository.deletebycardnumber(cardnumber);
			return new ResponseEntity<String>("delete succesfully",HttpStatus.OK);
		}
		else {
			List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
			for(Wallet wallet2 : wallets) {
				if(wallet2 != wallet) {
					wallet2.setActive(true);
					walletRepository.save(wallet2);
					break;
				}
			}
			walletRepository.deletebycardnumber(cardnumber);
			return new ResponseEntity<String>("delete succesfully",HttpStatus.OK);
		}
	}
	
	@PostMapping("wallet/{cardnumber}")
	public ResponseEntity<String> setactive(@PathVariable String cardnumber){
		Wallet wallet = walletRepository.findBycardNumber(cardnumber);

		if(wallet == null) {
			return new ResponseEntity<String>("wallet not found",HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		User user = wallet.getUser();
		boolean active = wallet.getactive();
		if(active==false) {
			List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
			for(Wallet wallet2 : wallets) {
				if(wallet2.getactive() == true) {
					wallet2.setActive(false);
					walletRepository.save(wallet2);
					break;
				}
				
			}
			wallet.setActive(true);
			walletRepository.save(wallet);
			return new ResponseEntity<String>("set actice",HttpStatus.OK);
		}
		else {
			wallet.setActive(false);
			walletRepository.save(wallet);
			return new ResponseEntity<String>("setactive",HttpStatus.OK);
		}
		
		
	}
}
