package entities;
import java.sql.Date;

public class Reservation {
	private Integer id;
	private Date reservationDate;
	private Date dateIn;
	private Date dateOut;
	private Integer customerId;
	private Integer roomId;
	
	public Reservation(Integer id, Date reservationDate, Date dateIn, Date dateOut, Integer customerId,
			Integer roomId) {
		this.id = id;
		this.reservationDate = reservationDate;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.customerId = customerId;
		this.roomId = roomId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

}
