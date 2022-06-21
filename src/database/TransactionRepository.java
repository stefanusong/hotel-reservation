package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Report;
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
	
	public void deleteTransaction(int ID) {
		String query = String.format("DELETE FROM `transactions` WHERE reservation_id = %d", ID);
		connect.executeUpdate(query);
	}
	
	public Report getMonthlyReport() {
		Report monthlyReport = null;
		
		try {
			String query = "SELECT \n" + 
					"	COUNT(id) AS total_transaction,\n" + 
					"	COUNT(DISTINCT customer_id) AS total_customer,\n" + 
					"	(\n" + 
					"		SELECT payment_method FROM transactions\n" + 
					"		WHERE MONTH(transaction_date) = MONTH(CURRENT_DATE)\n" + 
					"		AND YEAR(transaction_date) = YEAR(CURRENT_DATE)\n" + 
					"		GROUP BY payment_method\n" + 
					"		ORDER BY COUNT(*) DESC\n" + 
					"		LIMIT 0,1\n" + 
					"	) AS fav_payment,\n" + 
					"	SUM(total_payment) AS total_income\n" + 
					"FROM \n" + 
					"	transactions\n" + 
					"WHERE \n" + 
					"	MONTH(transaction_date) = MONTH(CURRENT_DATE)\n" + 
					"	AND YEAR(transaction_date) = YEAR(CURRENT_DATE);";
			ResultSet res = connect.executeQuery(query);
			
			if(res.next()) {
				monthlyReport = new Report(res.getInt("total_transaction"), res.getInt("total_customer"), 
						res.getString("fav_payment") ,res.getInt("total_income"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return monthlyReport;
	}
	
	public Report getAnualReport() {
		Report monthlyReport = null;
		
		try {
			String query = "SELECT \n" + 
					"	COUNT(id) AS total_transaction,\n" + 
					"	COUNT(DISTINCT customer_id) AS total_customer,\n" + 
					"	(\n" + 
					"		SELECT payment_method FROM transactions\n" + 
					"		WHERE YEAR(transaction_date) = YEAR(CURRENT_DATE)\n" + 
					"		GROUP BY payment_method\n" + 
					"		ORDER BY COUNT(*) DESC\n" + 
					"		LIMIT 0,1\n" + 
					"	) AS fav_payment,\n" + 
					"	SUM(total_payment) AS total_income\n" + 
					"FROM \n" + 
					"	transactions\n" + 
					"WHERE \n" + 
					"	YEAR(transaction_date) = YEAR(CURRENT_DATE);";
			ResultSet res = connect.executeQuery(query);
			
			if(res.next()) {
				monthlyReport = new Report(res.getInt("total_transaction"), res.getInt("total_customer"), 
						res.getString("fav_payment") ,res.getInt("total_income"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return monthlyReport;
	}
	
	public Report getAllTimeReport() {
		Report monthlyReport = null;
		
		try {
			String query = "SELECT \n" + 
					"	COUNT(id) AS total_transaction,\n" + 
					"	COUNT(DISTINCT customer_id) AS total_customer,\n" + 
					"	(\n" + 
					"		SELECT payment_method FROM transactions\n" + 
					"		GROUP BY payment_method\n" + 
					"		ORDER BY COUNT(*) DESC\n" + 
					"		LIMIT 0,1\n" + 
					"	) AS fav_payment,\n" + 
					"	SUM(total_payment) AS total_income\n" + 
					"FROM \n" + 
					"	transactions;";
			ResultSet res = connect.executeQuery(query);
			
			if(res.next()) {
				monthlyReport = new Report(res.getInt("total_transaction"), res.getInt("total_customer"), 
						res.getString("fav_payment") ,res.getInt("total_income"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return monthlyReport;
	}
	
	
}
