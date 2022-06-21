package services;

import java.util.Scanner;
import java.util.Vector;

import database.RoomRepository;
import entities.Room;

public class RoomService {
	private static RoomService instance;
	Scanner sc = new Scanner(System.in);
	RoomRepository roomRepo = RoomRepository.getInstance();
	
	public static RoomService getInstance() {
		if(instance == null) instance = new RoomService();
		return instance;
	}
	
	public Vector<Room> getAvailableRoomsBetween(String dateIn, String dateOut) {
		return roomRepo.getRoomsByDate(dateIn, dateOut);
	}
	
	public Room getRoomByNo(Integer roomNo) {
		return roomRepo.getRoomByNo(roomNo);
	}

	public void getRooms() {
		Vector<Room> room = new Vector<>();
		room = roomRepo.getRooms();

		System.out.println("Rooms");
		System.out.println("=======================");
		
		if(room.isEmpty()) {
			System.out.println("There's no room exist");
			return;
		}
		
		System.out.println(String.format(
				"| %-5s | %-10s | %-15s | %-15s |", 
				"No", "Capacity", 
				"Price per night", "Type"));
		
		for(Room r: room) {
			System.out.println(String.format(
				"| %-5d | %-10d | %-15d | %-15s |", 
				r.getRoomNo(), r.getCapacity(), 
				r.getPricePerNight(),r.getRoomType()));
		}
		System.out.println();
	}
	
	public void insertRoom(){		
		Integer roomNo;
		Integer capacity;
		Integer pricePerNight;
		String roomType;
		
		System.out.print("Room Number:");
		roomNo = sc.nextInt();
		sc.nextLine();
		System.out.print("Capacity:");
		capacity = sc.nextInt();
		sc.nextLine();
		System.out.print("Price per Night:");
		pricePerNight = sc.nextInt();
		sc.nextLine();
		System.out.print("Room Type:");
		roomType = sc.nextLine();
		
		roomRepo.insertRoom(new Room(roomNo, capacity, pricePerNight, roomType));
		
		System.out.println("Room has been inserted");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public void updateRoom() {
		Integer roomNo;
		Integer capacity;
		Integer pricePerNight;
		String roomType;
		
		System.out.print("Room Number:");
		roomNo = sc.nextInt();
		sc.nextLine();
		System.out.print("Capacity:");
		capacity = sc.nextInt();
		sc.nextLine();
		System.out.print("Price per Night:");
		pricePerNight = sc.nextInt();
		sc.nextLine();
		System.out.print("Room Type:");
		roomType = sc.nextLine();
		
		roomRepo.updateRoom(new Room(roomNo, capacity, pricePerNight, roomType));
		
		System.out.println("Room has been updated");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public void deleteRoom() {
		Integer roomNo;
		
		System.out.print("Room Number:");
		roomNo = sc.nextInt();
		sc.nextLine();
		
		roomRepo.deleteRoom(roomNo);
		
		System.out.println("Room has been deleted");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
}
