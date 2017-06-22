package com.capgemini.repository;

import com.capgemini.bean.Account;

public interface AccountRepository {
	
	boolean save(Account account);
	
	Account searchAccount(int accountNumber);

}
