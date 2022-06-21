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
	
	public Integer insertReservation(Reservation newReservation) {
		Integer createdId = 0;
		try {
			String query = String.format("INSERT INTO reservations(customer_id, room_no, reservation_date, date_in, date_out, status)\r\n" + 
					"VALUES('%d', '%d', CURRENT_DATE, '%s', '%s', 'reserved');", 
					newReservation.getCustomerId(), newReservation.getRoomId(), 
					newReservation.getDateIn().toString(), newReservation.getDateOut().toString());
			connect.executeUpdate(query);
			
			String query2 = "SELECT id FROM reservations ORDER BY id DESC LIMIT 1";
			ResultSet res = connect.executeQuery(query2);
			if(res.next()) {
				createdId = res.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return createdId;
	}
	
	public void cancelReservation(int reservationID) {
		try {
			String query = String.format("UPDATE `reservations` SET `status`='cancelled' WHERE id = %d", reservationID);
			connect.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateReservation(int id, Reservation updatedReservation) {
		try {
			String query = String.format(
				"UPDATE `reservations` \r\n" + 
				"SET `room_no`=%d,`status`='%s'\r\n" + 
				",`date_in`='%s'\r\n" + 
				",`date_out`='%s' \r\n" + 
				"WHERE `id`=%d"
				, updatedReservation.getRoomId(), updatedReservation.getStatus()
				, updatedReservation.getDateIn().toString(), updatedReservation.getDateOut().toString()
				, id);
						
			connect.executeUpdate(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<Reservation> getReservation(String status){
		Vector<Reservation> reservation = new Vector<>();
		String query = "SELECT * FROM reservations WHERE status='" + status + "'";
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				reservation.add(new Reservation(
					rs.getInt("id"), 
					rs.getDate("reservation_date"),
					rs.getDate("date_in"),
					rs.getDate("date_out"),
					rs.getInt("customer_id"),
					rs.getInt("room_no"),
					status
					));
			}
		} catch (SQLException e) {
			
		}
		return reservation;
	}
	
	public Vector<Reservation> getReservationByUserId(Integer userId) {
		Vector<Reservation> reservations = new Vector<>();
		
		try {
			String query = String.format("SELECT * FROM reservations WHERE customer_id = %d AND status NOT LIKE 'cancelled';", userId);
			ResultSet res = connect.executeQuery(query);
			
			while(res.next()) {
				Reservation currReservation = new Reservation(
							res.getInt("id"),
							res.getDate("reservation_date"),
							res.getDate("date_in"),
							res.getDate("date_out"),
							res.getInt("customer_id"),
							res.getInt("room_no"),
							res.getString("status")
						);
				
				reservations.add(currReservation);
			}
		} catch (SQLException e) {
	//			e.printStackTrace();
		}
		
		return reservations;
	}

	public boolean validateRerservation(Integer reservationId) {
		String query = String.format("SELECT * FROM `reservations` WHERE id=%d AND status LIKE 'reserved'", reservationId);
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
}