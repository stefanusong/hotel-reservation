package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Admin;
import entities.Customer;
import entities.User;

public class UserRepository {
	private static UserRepository instance;
	private Connect connect;
	
	private UserRepository() {
		connect = Connect.getConnection();
	}
	
	public static UserRepository getInstance() {
		if(instance == null) instance = new UserRepository();
		return instance;
	}
	
	public void insertUser(User user) {
		if(user instanceof Customer) {
			Customer newCustomer = (Customer) user;
			
			String query = String.format("INSERT INTO users(email, name, password, customer_phone, customer_age,role)\r\n" + 
					"VALUES('%s', '%s', '%s', '%s', %d, 'customer');",
					newCustomer.getEmail(), newCustomer.getName(), 
					newCustomer.getPassword(), newCustomer.getCustomerPhoneNumber(),
					newCustomer.getCustomerAge());
			
			connect.executeUpdate(query);
		} else {
			System.out.println("Create admin is unimplemented");
		}
	}
	
	public User getUserByEmail(String email) {
		User user = null;

		try {
			String query = String.format("SELECT * FROM users WHERE email LIKE '%s' LIMIT 1", email);
			ResultSet res = connect.executeQuery(query);
			
			while(res.next()) {
				String role = res.getString("role");
				if(role.equalsIgnoreCase("customer")) {
					user = new Customer(
							res.getString("email"),
							res.getString("name"),
							res.getString("password"),
							res.getString("customer_phone"),
							res.getInt("customer_age")
							);
				} else {
					user = new Admin(
							res.getString("email"),
							res.getString("name"),
							res.getString("password"),
							res.getDate("joined_date"),
							res.getString("branch_office")
							);
				}
				
				user.setId(res.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean isUserExists(String email, String role) {
		boolean isExists = false;
		
		try {
			String query = String.format("SELECT * FROM users WHERE email LIKE '%s' AND role LIKE '%s'", email, role);
			isExists = connect.executeQuery(query).next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isExists;
	}
	
	public boolean isPasswordMatch(String email, String password) {
		boolean isMatch = false;
		
		try {
			String query = String.format("SELECT * FROM users WHERE email LIKE '%s' AND password = '%s'", email, password);
			isMatch = connect.executeQuery(query).next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isMatch;
	}
}
