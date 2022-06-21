package services;

import java.sql.Date;
import java.util.Scanner;
import java.util.Vector;

import database.ReservationRepository;
import entities.Reservation;
import entities.Room;
import entities.Transaction;
import entities.User;

public class ReservationService {
	
	private static ReservationService instance;
	Scanner sc = new Scanner(System.in);
	ReservationRepository reservationRepo = ReservationRepository.getInstance();
	UserService userService = UserService.getInstance();
	
	public static ReservationService getInstance() {
		if(instance == null) instance = new ReservationService();
		return instance;
	}
	
	public void createReservation() {
	 	Date dateIn, dateOut;
	 	String dateInStr, dateOutStr, paymentMethod;
		Integer roomNo;
		
		// Get date range
		System.out.print("Enter your check in date [yyyy-mm-dd]:");
		dateInStr = sc.nextLine();
		
		System.out.print("Enter your check out date [yyyy-mm-dd]:");
		dateOutStr = sc.nextLine();
		
		// Get rooms
		System.out.println("Getting available rooms on these date...");
		RoomService roomService = RoomService.getInstance();
		Vector<Room> rooms = roomService.getAvailableRoomsBetween(dateInStr, dateOutStr);
		
		if(rooms.isEmpty()) {
			System.out.println("No room available for that day");
			System.out.println();
			return;
		}
		
		System.out.printf("%7s | %8s | %7s | %12s\n", "Room No", "Capacity", "Price", "Type");
		for (Room room : rooms) {
			System.out.printf("%7d | %8d | %7d | %12s\n", room.getRoomNo(), room.getCapacity(), room.getPricePerNight(), room.getRoomType());
		}
		
		do {
			System.out.print("Enter room no: ");
			roomNo = askInt();
		} while(!isRoomAvailable(roomNo, rooms));
		
		// Get payment details
		do {
			System.out.print("Choose payment method [virtual account | gopay | ovo]: ");
			paymentMethod = sc.nextLine();
		} while(!paymentMethod.equalsIgnoreCase("virtual account") 
				&& !paymentMethod.equalsIgnoreCase("gopay") 
				&& !paymentMethod.equalsIgnoreCase("ovo"));
		
		// Confirmation
		boolean confirmed = false;
		String confirmation;
		do {
			System.out.print("Are you sure to make this reservation ? [yes | no]: ");
			confirmation = sc.nextLine();
		} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
		
		confirmed = confirmation.equalsIgnoreCase("yes");
		if(!confirmed) {
			System.out.println("Okay, reservation not created");
			System.out.println("Press enter to continue..");
			sc.nextLine();
			return;
		}
		
		// Get current user
		UserService userService = UserService.getInstance();
		User currUser = userService.getLoggedInUser();
		
		// Insert reservation
		dateIn = Date.valueOf(dateInStr);
		dateOut = Date.valueOf(dateOutStr);
		Reservation newReservation = new Reservation(dateIn, dateOut, currUser.getId(), roomNo);
		
		// Insert transaction
		Integer newReservationId = reservationRepo.insertReservation(newReservation);
		Integer roomPrice = roomService.getRoomByNo(roomNo).getPricePerNight();
		
		Transaction newTransaction = new Transaction(newReservationId, currUser.getId(), paymentMethod, roomPrice);
		TransactionService transService = TransactionService.getInstance();
		transService.createTransaction(newTransaction);
		
		System.out.println("Thank you, reservation created !");
		System.out.println("Press enter to continue..");
		sc.nextLine();
		
	}
	
	public void cancelReservation() {
		getUserReservations();
	
		User loggedInUser = userService.getLoggedInUser();
		Vector<Reservation> reservations = reservationRepo.getReservationByUserId(loggedInUser.getId());

		if (reservations.isEmpty()) {
			System.out.println("No reservation made");
			return;
		} else {
			boolean confirmed = false;
			String confirmation;
			
			do {
				System.out.print("Are you sure you want to cancel this reservation? [yes | no]: ");
				confirmation = sc.nextLine();
			} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
			
			confirmed = confirmation.equalsIgnoreCase("yes");
			
			if(!confirmed) {
				System.out.println("Alright, have a nice day!");
				System.out.println("Press enter to continue..");
				sc.nextLine();
				return;
			} else {
				System.out.println("Cancel Reservation Form");
				System.out.println("=======================");
				System.out.printf("Insert your reservation ID: ");
				
				int reserveID = Integer.parseInt(sc.nextLine());
				
				do {
					System.out.print("Are you sure you want to cancel this reservation? [yes | no]: ");
					confirmation = sc.nextLine();
				} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
				
				confirmed = confirmation.equalsIgnoreCase("yes");
				
				if(!confirmed) {
					System.out.println("Alright, your reservation is safe with us!");
					System.out.println("Press enter to continue..");
					sc.nextLine();
					return;
				}
				
				reservationRepo.cancelReservation(reserveID);
				
				TransactionService transService = TransactionService.getInstance();
				transService.deleteTransaction(reserveID);
				
				System.out.printf("Reservation (ID: %d) has been cancelled!\n", reserveID);
				System.out.println("press enter to continue...");
				sc.nextLine();
			}
		}
	}
	
	public void getUserReservations() {
		System.out.println("Your reservations");
		System.out.println("=======================");
		User loggedInUser = userService.getLoggedInUser();
		Vector<Reservation> reservations = reservationRepo.getReservationByUserId(loggedInUser.getId());
		
		if(reservations.size() == 0) {
			System.out.println("You don't have any reservation yet.");
		} else {
			System.out.printf("%3s | %16s | %10s | %10s | %8s |\n", "No.", "Reservation Date", "Date In", "Date Out", "Room No");
			int ctr = 0;
			for (Reservation reservation : reservations) {
				if (reservation.getStatus().equals("reserved")) {
					System.out.printf("%3d | %16s | %10s | %10s | %8s |\n", 
							++ctr, 
							reservation.getReservationDate().toString(), 
							reservation.getDateIn().toString(),
							reservation.getDateOut().toString(),
							reservation.getRoomId().toString()
					);
				}
			}
		}
		
		System.out.println("Press enter to continue..");
		sc.nextLine();
	}
	
	private boolean isRoomAvailable(Integer roomNo, Vector<Room> rooms) {
		for (Room room : rooms) {
			if(room.getRoomNo() == roomNo) return true;
		}
		
		return false;
	}
	
	public void getReservationAdmin(String status) {
		Vector<Reservation> reservations = reservationRepo.getReservation(status);
		printReservation(reservations);
	}
	
	public void createReservationAdmin() {
		Date dateIn, dateOut;
	 	String dateInStr, dateOutStr, paymentMethod;
		Integer roomNo, userId;
		
		//show user info
		UserService userService = UserService.getInstance();
		userService.displayAllUser();
		
		//Get user id
		do {
			System.out.print("Input user id: ");
			userId = askInt();
		} while(!userService.validateUser(userId));
		
		// Get date range
		System.out.print("Enter your check in date [yyyy-mm-dd]:");
		dateInStr = sc.nextLine();
		
		System.out.print("Enter your check out date [yyyy-mm-dd]:");
		dateOutStr = sc.nextLine();
		
		// Get rooms
		System.out.println("Getting available rooms on these date...");
		RoomService roomService = RoomService.getInstance();
		Vector<Room> rooms = roomService.getAvailableRoomsBetween(dateInStr, dateOutStr);
		
		if(rooms.isEmpty()) {
			System.out.println("No room available for that day");
			System.out.println();
			return;
		}
		
		System.out.printf("%7s | %8s | %7s | %12s\n", "Room No", "Capacity", "Price", "Type");
		for (Room room : rooms) {
			System.out.printf("%7d | %8d | %7d | %12s\n", room.getRoomNo(), room.getCapacity(), room.getPricePerNight(), room.getRoomType());
		}
		
		do {
			System.out.print("Enter room no: ");
			roomNo = askInt();
		} while(!isRoomAvailable(roomNo, rooms));
		
		// Get payment details
		do {
			System.out.print("Choose payment method [virtual account | gopay | ovo]: ");
			paymentMethod = sc.nextLine();
		} while(!paymentMethod.equalsIgnoreCase("virtual account") 
				&& !paymentMethod.equalsIgnoreCase("gopay") 
				&& !paymentMethod.equalsIgnoreCase("ovo"));
		
		// Confirmation
		boolean confirmed = false;
		String confirmation;
		do {
			System.out.print("Are you sure to make this reservation ? [yes | no]: ");
			confirmation = sc.nextLine();
		} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
		
		confirmed = confirmation.equalsIgnoreCase("yes");
		if(!confirmed) {
			System.out.println("Okay, reservation not created");
			System.out.println("Press enter to continue..");
			sc.nextLine();
			return;
		}
		
		// Insert reservation
		dateIn = Date.valueOf(dateInStr);
		dateOut = Date.valueOf(dateOutStr);
		Reservation newReservation = new Reservation(dateIn, dateOut, userId, roomNo);
		
		// Insert transaction
		Integer newReservationId = reservationRepo.insertReservation(newReservation);
		Integer roomPrice = roomService.getRoomByNo(roomNo).getPricePerNight();
		
		Transaction newTransaction = new Transaction(newReservationId, userId, paymentMethod, roomPrice);
		TransactionService transService = TransactionService.getInstance();
		transService.createTransaction(newTransaction);
		
		System.out.println("Thank you, reservation created !");
		System.out.println("Press enter to continue..");
		sc.nextLine();
	}
	
	public void cancelReservationAdmin() {
		Integer userId;
		
		//show user info
		UserService userService = UserService.getInstance();
		userService.displayAllUser();
		
		//Get user id
		do {
			System.out.print("Input user id: ");
			userId = askInt();
		} while(!userService.validateUser(userId));
		
		User loggedInUser = userService.getUserById(userId);
		Vector<Reservation> reservations = reservationRepo.getReservationByUserId(loggedInUser.getId());

		if (reservations.isEmpty()) {
			System.out.println("No reservation made");
			return;
		} else {
			boolean confirmed = false;
			String confirmation;
			
			do {
				System.out.print("Are you sure you want to cancel this reservation? [yes | no]: ");
				confirmation = sc.nextLine();
			} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
			
			confirmed = confirmation.equalsIgnoreCase("yes");
			
			if(!confirmed) {
				System.out.println("Alright, have a nice day!");
				System.out.println("Press enter to continue..");
				sc.nextLine();
				return;
			} else {
				printReservation(reservations);
				System.out.println("Cancel Reservation Form");
				System.out.println("=======================");
				System.out.printf("Insert your reservation ID: ");
				
				int reserveID = Integer.parseInt(sc.nextLine());
				
				do {
					System.out.print("Are you sure you want to cancel this reservation? [yes | no]: ");
					confirmation = sc.nextLine();
				} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
				
				confirmed = confirmation.equalsIgnoreCase("yes");
				
				if(!confirmed) {
					System.out.println("Alright, your reservation is safe with us!");
					System.out.println("Press enter to continue..");
					sc.nextLine();
					return;
				}
				
				reservationRepo.cancelReservation(reserveID);
				
				TransactionService transService = TransactionService.getInstance();
				transService.deleteTransaction(reserveID);
				
				System.out.printf("Reservation (ID: %d) has been cancelled!\n", reserveID);
				System.out.println("press enter to continue...");
				sc.nextLine();
			}
		}
	}
	
	public void updateReservationAdmin() {
		Date dateIn, dateOut;
	 	String dateInStr, dateOutStr, status;
		Integer roomNo, userId, reservationId;
		
		//show user info
		UserService userService = UserService.getInstance();
		userService.displayAllUser();
		
		//Get user id
		do {
			System.out.print("Input user id: ");
			userId = askInt();
		} while(!userService.validateUser(userId));
		
		//get reservation id
		
		User loggedInUser = userService.getUserById(userId);
		Vector<Reservation> reservations = reservationRepo.getReservationByUserId(loggedInUser.getId());

		if (reservations.isEmpty()) {
			System.out.println("No reservation made");
			System.out.println();
			return;
		} 
		
		printReservation(reservations);
		
		do {
			System.out.printf("Insert your reservation ID: ");
			reservationId = askInt();			
		}while (!reservationRepo.validateRerservation(reservationId));
		
		// Get date range
		System.out.print("Enter your check in date [yyyy-mm-dd]:");
		dateInStr = sc.nextLine();
		
		System.out.print("Enter your check out date [yyyy-mm-dd]:");
		dateOutStr = sc.nextLine();
		
		// Get rooms
		System.out.println("Getting available rooms on these date...");
		RoomService roomService = RoomService.getInstance();
		Vector<Room> rooms = roomService.getAvailableRoomsBetween(dateInStr, dateOutStr);
		
		if(rooms.isEmpty()) {
			System.out.println("No room available for that day");
			System.out.println();
			return;
		}
		
		System.out.printf("%7s | %8s | %7s | %12s\n", "Room No", "Capacity", "Price", "Type");
		for (Room room : rooms) {
			System.out.printf("%7d | %8d | %7d | %12s\n", room.getRoomNo(), room.getCapacity(), room.getPricePerNight(), room.getRoomType());
		}
		
		do {
			System.out.print("Enter room no: ");
			roomNo = askInt();
		} while(!isRoomAvailable(roomNo, rooms));
		
		//Get Status
		do {
			System.out.println("Enter reservation status [reserved | cancelled]: ");
			status = sc.nextLine();
		} while(!status.equalsIgnoreCase("reserved") && !status.equalsIgnoreCase("cancelled"));
		
		// Confirmation
		boolean confirmed = false;
		String confirmation;
		do {
			System.out.print("Are you sure to update this reservation ? [yes | no]: ");
			confirmation = sc.nextLine();
		} while(!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no"));
		
		confirmed = confirmation.equalsIgnoreCase("yes");
		if(!confirmed) {
			System.out.println("Okay, reservation not updated");
			System.out.println("Press enter to continue..");
			sc.nextLine();
			return;
		}
		
		// Insert reservation
		dateIn = Date.valueOf(dateInStr);
		dateOut = Date.valueOf(dateOutStr);
		Reservation reservation = new Reservation(dateIn, dateOut, userId, roomNo);
		reservation.setStatus(status);
		
		// Update transaction
		reservationRepo.updateReservation(reservationId, reservation);

		
		System.out.println("Thank you, reservation updated !");
		System.out.println("Press enter to continue..");
		sc.nextLine();

	}
	
	private void printReservation(Vector<Reservation> reservations) {
		System.out.println(String.format(
				"| %-5s | %-12s | %-12s | %-18s | %-15s | %-15s |"
				, "Id", "Customer Id","Room Id"
				, "Reservation Date", "Date In", "Date Out"));
		
		for(Reservation r: reservations) {
			System.out.println(String.format(
				"| %-5d | %-12d | %-12d | %-18s | %-15s | %-15s |"
				, r.getId(), r.getCustomerId(), r.getRoomId()
				, r.getReservationDate().toString(), r.getDateIn().toString(), r.getDateOut().toString()));
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
