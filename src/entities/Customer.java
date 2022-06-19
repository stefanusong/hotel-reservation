package entities;

public class Customer extends User {
	private String CustomerPhoneNumber;
	private Integer CustomerAge;
	
	public Customer(String email, String name, String password, String customerPhoneNumber,
			Integer customerAge) {
		super(email, name, password, "customer");
		CustomerPhoneNumber = customerPhoneNumber;
		CustomerAge = customerAge;
	}
	
	public String getCustomerPhoneNumber() {
		return CustomerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		CustomerPhoneNumber = customerPhoneNumber;
	}
	public Integer getCustomerAge() {
		return CustomerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		CustomerAge = customerAge;
	}
	
}
