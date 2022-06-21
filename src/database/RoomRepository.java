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
	
	public Room getRoomByNo(Integer roomNo) {
		try {
			String query = "SELECT * FROM rooms WHERE room_no = " + roomNo;
			ResultSet res = connect.executeQuery(query);
			while(res.next()) {
				Room currRoom = new Room(
						res.getInt("room_no"),
						res.getInt("capacity"),
						res.getInt("price_per_night"),
						res.getString("room_type")
						);
				
				return currRoom;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Vector<Room> getRoomsByDate(String dateIn, String dateOut) {
		Vector<Room> rooms = new Vector<>();
		
		try {
			String query = String.format("SELECT * FROM rooms\r\n" + 
					"WHERE room_no NOT IN\r\n" + 
					"(\r\n" + 
					"	SELECT room_no FROM reservations\r\n" + 
					"	WHERE \r\n" + 
					"	status <> 'cancelled' AND\r\n" + 
					"	(\r\n" + 
					"		(date_in <= '%s' AND '%s' <= date_out) OR\r\n" + 
					"		(date_in <= '%s' AND '%s' <= date_out)\r\n" + 
					"	)\r\n" + 
					");",dateIn, dateIn, dateOut, dateOut);
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
	
	public void insertRoom(Room room) {
		String query = String.format(
				"INSERT INTO `rooms`(`room_no`, `capacity`, `price_per_night`, `room_type`) \r\n" + 
				"VALUES (%d, %d, %d, '%s')"
				, room.getRoomNo(), room.getCapacity()
				, room.getPricePerNight(), room.getRoomType());
		
		connect.executeUpdate(query);
	}
	
	public void updateRoom(Room room) {
		String query = String.format(
				"UPDATE `rooms` \r\n" + 
				"SET `capacity`=%d,`price_per_night`=%d,`room_type`='%s' \r\n" + 
				"WHERE `room_no`=%d"
				, room.getCapacity(), room.getPricePerNight()
				, room.getRoomType(), room.getRoomNo());
		
		connect.executeUpdate(query);
	}
	
	public void deleteRoom(Integer roomNo) {
		String query = "DELETE FROM `rooms` WHERE room_no=" + roomNo;
		
		connect.executeUpdate(query);
	}
	
}
