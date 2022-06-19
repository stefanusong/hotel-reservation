package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import entities.Room;

public class RoomRepository {
	private static RoomRepository instance;
	private Connect connect;
	
	private RoomRepository() {
		connect = Connect.getConnection();
	}
	
	public static RoomRepository getInstance() {
		if(instance == null) instance = new RoomRepository();
		return instance;
	}
	
	public Vector<Room> getRooms() {
		Vector<Room> rooms = new Vector<>();
		
		try {
			String query = "SELECT * FROM rooms";
			ResultSet res = connect.executeQuery(query);
			while(res.next()) {
				Room currRoom = new Room(
						res.getInt("room_no"),
						res.getInt("capacity"),
						res.getInt("price_per_night"),
						res.getString("room_type")
						);
				
				rooms.add(currRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rooms;
	}
}
