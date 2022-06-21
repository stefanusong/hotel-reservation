package services;

import java.util.Scanner;

import database.UserRepository;
import entities.Admin;
import entities.Customer;
import entities.User;

public class UserService {
	
	private static UserService instance;
	private Scanner sc = new Scanner(System.in);
	private UserRepository userRepo = UserRepository.getInstance();
	private User loggedInUser;
	
	public static UserService getInstance() {
		if(instance == null) instance = new UserService();
		return instance;
	}
	
	public boolean loginAs(String role) {
		boolean isSuccess = false;
		String email, password;
		
		System.out.print("Email: ");
		email = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();
		
		if(!userRepo.isUserExists(email, role)) {
			System.out.println("Email not found");
		} else if(!userRepo.isPasswordMatch(email, password)) {
			System.out.println("Incorrect password");
		} else {
			loggedInUser = userRepo.getUserByEmail(email);
			isSuccess = true;
			System.out.println("Login succesful!");
		}
		
		return isSuccess;
	}
	
	public void register() {
		System.out.println("Let's register your account");
		System.out.println("===========================");
		
		String email, name, password, phone;
		Integer age;
		
		System.out.print("Email: ");
		email = sc.nextLine();
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();
		System.out.print("Phone: ");
		phone = sc.nextLine();
		System.out.print("Age: ");
		age = askInt();
		
		Customer newCustomer = new Customer(email, name, password, phone, age);
		userRepo.insertUser(newCustomer);
		
		System.out.println("Your account has been registered!");
		sc.nextLine();
		System.out.println();
	}
	
	public void getUserInfo() {
		System.out.println("Your Profile");
		System.out.println("============");
		System.out.printf("1. User ID\t: %d\n", loggedInUser.getId());
		System.out.printf("2. Name\t\t: %s\n", loggedInUser.getName());
		System.out.printf("3. Email\t: %s\n", loggedInUser.getEmail());
		
		
		if (loggedInUser.getRole().equals("customer")) {
			Customer customerLoggedIn = (Customer) loggedInUser;
			System.out.printf("4. Phone Number\t: %s\n", customerLoggedIn.getCustomerPhoneNumber());
		} else {
			Admin adminLoggedIn = (Admin) loggedInUser;
			System.out.printf("4. Branch Office: %s\n", adminLoggedIn.getBranchOffice());
			System.out.printf("[Joined since %s]\n", adminLoggedIn.getJoinedDate().toString());
		}
		
		System.out.println("Press enter to continue..");
		sc.nextLine();
	}
	
	private int askInt() {
		int number;
		
		try {
			number = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Please ENTER a number!");
			number = -57;
		} finally {
			sc.nextLine();
		}
		
		return number;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}
	
}
