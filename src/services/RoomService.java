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
}
