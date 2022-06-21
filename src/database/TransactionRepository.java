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
		String query = String.format("INSERT INTO transactions(reservation_id, customer_id, transaction_date, payment_method, total_payment)\r\n" + 
				"VALUES('%d', '%d', CURRENT_DATE, '%s', '%d');", 
				newTransaction.getReservationId(), newTransaction.getCustomerId(),
				newTransaction.getPaymentMethod(), newTransaction.getTotalPayment());
		connect.executeUpdate(query);
	}
}
