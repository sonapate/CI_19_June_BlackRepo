package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.bean.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

import static org.junit.Assert.assertEquals;

public class AccountTest {
	
	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);//dummy object accountRepository object is created
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	// use story for createAccount
	//1 when the amount is less than 500 then it should throw exception
	//2 when the valid info is passed account object should be created successfully
	
	@Test(expected = InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500ItShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101,200);
		
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountObjectShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account.getAmount(),accountService.createAccount(101, 5000).getAmount());
	}
	
	
	 /* deposit  amount
	 * 1.when the account number is invalid system should throw exception
	 * 2.when the valid info(103,6000) is passed amount should be deposit successfully */
	 
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsInvalidForDepositAmountShouldThrowException() throws InvalidAccountNumberException
	{   
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int tamount= accountService.depositAmount(102, 6000);
		System.out.println("Total amount in account " + tamount);
		
	}
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeDepositedSuccessfully() throws InvalidAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(103);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(103)).thenReturn(account);
		int tamount= accountService.depositAmount(103, 6000);
		System.out.println("Total amount in "+ account.getAccountNumber()+ " account " + tamount);
		
	}
	
	
	/* withdraw amount
	 * 1.when the account number is invalid system should throw exception
	 * 2.when the account has insufficient balance system should throw exception
	 * 3.when the valid info(103,6000) is passed amount should be withdraw successfully */
	 
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsInvalidForWithdrawAmountShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException
	{   
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int tamount= accountService.withdrawAmount(102, 2000);
		System.out.println("Total amount in account " + tamount);
		
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTheAmountIsInsufficientForWithdrawAmountShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException
	{   
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int tamount= accountService.withdrawAmount(101, 6000);
		System.out.println("Total amount in account " + tamount);
		
	}
	
	
	@Test
	public void whenTheAccountInfoIsValidForWithdrawAmountThenAmountShouldBeWithdrawSuccessfully() throws InvalidAccountNumberException, InsufficientBalanceException
	{   
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int tamount= accountService.withdrawAmount(101, 2000);
		System.out.println("Total amount in account " + tamount);
		
	}

	

}
