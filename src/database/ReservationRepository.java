package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import entities.Reservation;

public class ReservationRepository {
	private static ReservationRepository instance;
	private Connect connect;
	
	private ReservationRepository() {
		connect = Connect.getConnection();
	}
	
	public static ReservationRepository getInstance() {
		if(instance == null) instance = new ReservationRepository();
		return instance;
	}

	public Vector<Reservation> getReservationByUserId(Integer userId) {
		Vector<Reservation> reservations = new Vector<>();
		
		try {
			String query = String.format("SELECT * FROM reservations WHERE id = %d", userId);
			ResultSet res = connect.executeQuery(query);
			
			while(res.next()) {
				Reservation currReservation = new Reservation(
							res.getInt("id"),
							res.getDate("reservation_date"),
							res.getDate("date_in"),
							res.getDate("date_out"),
							res.getInt("customer_id"),
							res.getInt("room_no")
						);
				
				reservations.add(currReservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservations;
	}
}