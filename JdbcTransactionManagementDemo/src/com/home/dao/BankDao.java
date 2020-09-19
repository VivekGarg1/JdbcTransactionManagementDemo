package com.home.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.home.exception.InsufficientAccountBalance;
import com.home.model.Account;

public interface BankDao {
	
	public abstract void withdraw(Connection connection, Account fromAccount,Account toAccount,Double amount) throws InsufficientAccountBalance, SQLException;
	public abstract void deposit(Connection connection, Account fromAccount,Account toAccount,Double amount) throws SQLException;

}
