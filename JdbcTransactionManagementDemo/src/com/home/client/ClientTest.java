package com.home.client;

import java.sql.SQLException;
import com.home.exception.InsufficientAccountBalance;
import com.home.model.Account;
import com.home.service.BankService;
import com.home.service.impl.BankServiceImpl;

public class ClientTest {

	public static void main(String[] args) throws InsufficientAccountBalance, SQLException {
	
		BankService bankService = new BankServiceImpl();
		
		Account fromAccount=new Account();
		fromAccount.setAccountNo(123456789L);
		
		Account toAccount=new Account();
		toAccount.setAccountNo(987654321L);
		
		bankService.transferFund(fromAccount, toAccount, 1100.0);
	}
}
