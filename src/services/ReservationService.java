package services;

import java.util.Scanner;
import java.util.Vector;

import database.ReservationRepository;
import entities.Reservation;
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
		System.out.println("Oops, not implemented yet");
		/* steps:
		 1. get reservation data from user input (date in, date out, room no)
		 2. check di table reservations, apakah di range date itu udah ada room no tsb yg direserve
		 3. kalo roomnya available di tanggal itu, next pura2 minta user buat pay (println gitu aja paling)
		 4. kalo yes, insert reservation sama transaction
		 5. kalo no, return;
		 */
	}
	
	public void getUserReservations() {
		System.out.println("Your reservations");
		System.out.println("=======================");
		User loggedInUser = userService.getLoggedInUser();
		Vector<Reservation> reservations = reservationRepo.getReservationByUserId(loggedInUser.getId());
		
		System.out.printf("%3s | %16s | %10s | %10s | %8s |\n", "No.", "Reservation Date", "Date In", "Date Out", "Room No");
		int ctr = 0;
		for (Reservation reservation : reservations) {
			System.out.printf("%3d | %16s | %10s | %10s | %8s |\n", 
					++ctr, 
					reservation.getReservationDate().toString(), 
					reservation.getDateIn().toString(),
					reservation.getDateOut().toString(),
					reservation.getRoomId().toString()
			);
		}
		
		System.out.println("Press enter to continue..");
		sc.nextLine();
	}
}
