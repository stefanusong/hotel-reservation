package entities;

import java.sql.Date;

public class Transaction {
	private Integer id;
	private Integer reservationId;
	private Integer customerId;
	private Date transactionDate;
	private String paymentMethod;
	private Integer totalPayment;
	
	public Transaction(Integer id, Integer reservationId, Integer customerId, Date transactionDate,
			String paymentMethod, Integer totalPayment) {
		this.id = id;
		this.reservationId = reservationId;
		this.customerId = customerId;
		this.transactionDate = transactionDate;
		this.paymentMethod = paymentMethod;
		this.totalPayment = totalPayment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Integer totalPayment) {
		this.totalPayment = totalPayment;
	}
	
}
