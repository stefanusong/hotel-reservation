package services;

import java.util.Scanner;

import database.RoomRepository;

public class RoomService {
	private static RoomService instance;
	Scanner sc = new Scanner(System.in);
	RoomRepository roomRepo = RoomRepository.getInstance();
	
	public static RoomService getInstance() {
		if(instance == null) instance = new RoomService();
		return instance;
	}
	
	
}
