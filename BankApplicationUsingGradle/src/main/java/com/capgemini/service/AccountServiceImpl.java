package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.bean.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountRepository;
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	@Override
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account=new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if (accountRepository.save(account)) {
			return account;
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#depositeAmount(int, int)
	 */
	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {
		Account account = accountRepository.searchAccount(accountNumber);
		if (account == null) {
			throw new InvalidAccountNumberException();
		}

		else {
			int Tamount = account.getAmount() + amount;
			account.setAmount(Tamount);
			return account.getAmount();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)
	 */
	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = accountRepository.searchAccount(accountNumber);
		if (account == null) {
			throw new InvalidAccountNumberException();
		}

		else {
			if (amount > account.getAmount()) {
				throw new InsufficientBalanceException();
			}

			else {
				int Tamount = account.getAmount() - amount;
				account.setAmount(Tamount);
				return account.getAmount();
			}
		}

	}

}
