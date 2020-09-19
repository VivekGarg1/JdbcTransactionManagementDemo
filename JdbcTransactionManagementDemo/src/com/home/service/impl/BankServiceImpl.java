package com.home.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.home.dao.BankDao;
import com.home.dao.impl.BankDaoImpl;
import com.home.exception.InsufficientAccountBalance;
import com.home.model.Account;
import com.home.service.BankService;
import com.home.util.JdbcUtil;

public class BankServiceImpl implements BankService {

	BankDao bankDao=new BankDaoImpl();
	
	@Override
	public void transferFund(Account fromAccount, Account toAccount, Double amount) throws SQLException {
		Connection connection=JdbcUtil.getConnection();
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		try {
			connection.setAutoCommit(false);
			bankDao.withdraw(connection,fromAccount, toAccount, amount);
			bankDao.deposit(connection,fromAccount, toAccount, amount);
			connection.commit();
		} catch (InsufficientAccountBalance e) {
			System.out.println(e.getMessage());
			if(connection != null)
				connection.rollback();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}

}
