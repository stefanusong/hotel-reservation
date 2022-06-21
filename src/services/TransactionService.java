package services;

import java.util.Scanner;

import database.TransactionRepository;
import entities.Report;
import entities.Transaction;

public class TransactionService {
	private static TransactionService instance;
	Scanner sc = new Scanner(System.in);
	TransactionRepository transactionRepo = TransactionRepository.getInstance();
	UserService userService = UserService.getInstance();
	
	public static TransactionService getInstance() {
		if(instance == null) instance = new TransactionService();
		return instance;
	}
	
	public void createTransaction(Transaction newTransaction) {
		transactionRepo.insertTransaction(newTransaction);
	}
	
	public void deleteTransaction(int ID) {
		transactionRepo.deleteTransaction(ID);
	}
	
	public void getReport() {
		System.out.println("\n===================");
		System.out.println("Transaction Report");
		System.out.println("===================");

		System.out.println("\n=========This Month=========");
		Report monthlyReport = transactionRepo.getMonthlyReport();
		monthlyReport.printReport();
		
		System.out.println("\n=========This Year=========");
		Report anualReport = transactionRepo.getAnualReport();
		anualReport.printReport();
		
		System.out.println("\n=========All Time=========");
		Report allTimeReport = transactionRepo.getAllTimeReport();
		allTimeReport.printReport();
	}
}