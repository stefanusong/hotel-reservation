package entities;

public class Room {
	private Integer roomNo;
	private Integer capacity;
	private Integer pricePerNight;
	private String roomType;
	
	public Room(Integer roomNo, Integer capacity, Integer pricePerNight, String roomType) {
		this.roomNo = roomNo;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.roomType = roomType;
	}

	public Integer getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(Integer pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
}
