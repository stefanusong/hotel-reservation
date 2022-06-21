package services;

import java.util.Scanner;

import database.TransactionRepository;
import entities.Transaction;

public class TransactionService {
	private static TransactionService instance;
	Scanner sc = new Scanner(System.in);
	TransactionRepository reservationRepo = TransactionRepository.getInstance();
	UserService userService = UserService.getInstance();
	
	public static TransactionService getInstance() {
		if(instance == null) instance = new TransactionService();
		return instance;
	}
	
	public void createTransaction(Transaction newTransaction) {
		reservationRepo.insertTransaction(newTransaction);
	}
	
	public void deleteTransaction(int ID) {
		reservationRepo.deleteTransaction(ID);
	}
}