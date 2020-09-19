package com.home.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.home.dao.BankDao;
import com.home.exception.InsufficientAccountBalance;
import com.home.model.Account;

public class BankDaoImpl implements BankDao {

	@Override
	public void withdraw(Connection connection, Account fromAccount, Account toAccount, Double amount)
			throws InsufficientAccountBalance, SQLException {
		Account accountFromDB = getAccountInfoFromDB(connection, fromAccount.getAccountNo());
		if (accountFromDB != null) {
			if (accountFromDB.getAccountBalance() < amount)
				throw new InsufficientAccountBalance("Insufficient account balance");
		} else
			throw new RuntimeException("Source account doesn't exist!!!");
		Double accountBalance = accountFromDB.getAccountBalance() - amount;
		String sql = "update bank_table set account_balance=? where account_no=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setDouble(1, accountBalance);
		ps.setLong(2, fromAccount.getAccountNo());
		int update = ps.executeUpdate();
		if (update > 0) {
			System.out.println("Amount Rs. " + amount + " is transfered from Account No: " + fromAccount.getAccountNo()
					+ " to Acount No: " + toAccount.getAccountNo());
		}
	}

	@Override
	public void deposit(Connection connection, Account fromAccount, Account toAccount, Double amount)
			throws SQLException {
		Account accountFromDB = getAccountInfoFromDB(connection, toAccount.getAccountNo());
		if(accountFromDB==null)
			throw new RuntimeException("Destination account doesn't exist!!!");
		Double accountBalance = accountFromDB.getAccountBalance() + amount;
		String sql = "update bank_table set account_balance=? where account_no=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setDouble(1, accountBalance);
		ps.setLong(2, toAccount.getAccountNo());
		int update = ps.executeUpdate();
		if (update > 0) {
			System.out.println("Amount Rs. " + amount + " is deposited in Account No: " + toAccount.getAccountNo());
		}
		// throw new RuntimeException();
	}

	private Account getAccountInfoFromDB(Connection connection, Long accountNo) {
		Account account = new Account();
		String sql = "select * from bank_table where account_no=?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, accountNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				account.setAccountHolderName(rs.getString("account_holder_name"));
				account.setAccountNo(rs.getLong("account_no"));
				account.setAccountBalance(rs.getDouble("account_balance"));
				account.setAccountType(rs.getString("account_type"));
				return account;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
