package com.home.service;

import java.sql.SQLException;

import com.home.exception.InsufficientAccountBalance;
import com.home.model.Account;

public interface BankService {
	
	public abstract void transferFund(Account fromAccount,Account toAccount,Double amount) throws InsufficientAccountBalance, SQLException;

}
