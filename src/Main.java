import java.util.Scanner;
import database.DbSeeder;
import services.ReservationService;
import services.UserService;

public class Main {
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		seedTables();
		displayAuthMenu();
	}
	
	private void seedTables() {
		DbSeeder seeder = new DbSeeder();
		seeder.seedAdmins();
		seeder.seedRooms();
	}
	
	private void displayAuthMenu() {
		boolean exit = false;
		
		do {
			System.out.println("\n" + 
					"\n" + 
					"  _    _       _       _    ______ _      _____                                _                   \n" + 
					" | |  | |     | |     | |  |  ____| |    |  __ \\                              | |                  \n" + 
					" | |__| | ___ | |_ ___| |  | |__  | |_   | |__) |___  ___  ___ _ ____   ____ _| |_ ___  _ __ _   _ \n" + 
					" |  __  |/ _ \\| __/ _ \\ |  |  __| | __|  |  _  // _ \\/ __|/ _ \\ '__\\ \\ / / _` | __/ _ \\| '__| | | |\n" + 
					" | |  | | (_) | ||  __/ |  | |____| |_   | | \\ \\  __/\\__ \\  __/ |   \\ V / (_| | || (_) | |  | |_| |\n" + 
					" |_|  |_|\\___/ \\__\\___|_|  |______|\\__|  |_|  \\_\\___||___/\\___|_|    \\_/ \\__,_|\\__\\___/|_|   \\__, |\n" + 
					"                                                                                              __/ |\n" + 
					"                                                                                             |___/ \n" + 
					"\n" + 
					"");
			
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("> ");
			
			int choice = sc.nextInt();
			
			switch(choice) {
				case 1:
					displayLoginMenu();
					break;
				case 2:
					displayRegisterMenu();
					break;
				case 3:
					exit = true;
					break;
			}
		} while(!exit);
	}
	
	private void displayLoginMenu() {		
		boolean exit = false;
		
		do {
			System.out.println("Login");
			System.out.println("1. Login as customer");
			System.out.println("2. Login as admin");
			System.out.println("3. Cancel");
			System.out.print("> ");
			int choice = askInt();
			
			
			UserService userService = UserService.getInstance();
			boolean isLoginSuccess = false;

			switch (choice) {
				case 1:
					isLoginSuccess = userService.loginAs("customer");
					break;
				case 2:
					isLoginSuccess = userService.loginAs("admin");
					break;
				case 3:
					exit = true;
				default:
					break;
			}
			
			if(isLoginSuccess) {
				boolean isAdmin = choice == 2;
				if(isAdmin) {
					showAdminPanel();
				} else {
					showCustomerPanel();
				}
			}
			
		} while(!exit);
	}
	
	private void displayRegisterMenu() {
		UserService userService = UserService.getInstance();
		userService.register();
	}
	
	private void showCustomerPanel() {
		ReservationService reservationService = ReservationService.getInstance();
		
		while (true) {
			int option;
			
			System.out.println("1. Make a reservation");
			System.out.println("2. View your reservations");
			System.out.println("3. Cancel a reservation");
			System.out.println("4. View your profile");
			System.out.println("5. Logout");
			
			do {
				System.out.print("[1 - 5] >> ");
				option = askInt();
			} while (option < 1 || option > 5);
			
			switch (option) {
			case 1:
				reservationService.createReservation();
				break;
			case 2:
				reservationService.getUserReservations();
				break;
			case 3:
				System.out.println("Cancelling..");
				break;
			case 4:
				System.out.println("Fetching profile..");
				System.out.println("Viewing.. viewing..");
				break;
			case 5:
				System.out.print("Logging out.. [Press ENTER to continue] ");
				sc.nextLine();
				System.out.println();
				return;
			}
		}
	}
	
	private void showAdminPanel() {
		while (true) {
			int option;
			
			System.out.println("1. Manage Rooms");
			System.out.println("2. Manage Payments");
			System.out.println("3. View Profile");
			System.out.println("4. Logout");
			
			do {
				System.out.print("[1 - 4] >> ");
				option = askInt();
			} while (option < 1 || option > 4);
			
			switch (option) {
			case 1:
				System.out.println("Viewing rooms..");
				System.out.println("Managing rooms..");
				break;
			case 2:
				System.out.println("Viewing available payments..");
				System.out.println("Managing payments..");
				break;
			case 3:
				System.out.println("Fetching profile..");
				System.out.println("Viewing.. viewing..");
				break;
			case 4:
				System.out.print("Logging out.. [Press ENTER to continue] ");
				sc.nextLine();
				System.out.println();
				return;
			}
		}
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
}
