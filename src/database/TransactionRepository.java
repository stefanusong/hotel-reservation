package database;

import entities.Transaction;

public class TransactionRepository {
	private static TransactionRepository instance;
	private Connect connect;
	
	private TransactionRepository() {
		connect = Connect.getConnection();
	}
	
	public static TransactionRepository getInstance() {
		if(instance == null) instance = new TransactionRepository();
		return instance;
	}
	
	public void insertTransaction(Transaction newTransaction) {
		// insert transaction here..
	}
}
