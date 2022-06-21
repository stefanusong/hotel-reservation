package entities;

public class Report {
	private Integer totalTransaction;
	private Integer totalCustomer;
	private String favPaymentMethod;
	private Integer totalIncome;
	
	public Report(Integer totalTransaction, Integer totalCustomer, String favPaymentMethod, Integer totalIncome) {
		this.totalTransaction = totalTransaction;
		this.totalCustomer = totalCustomer;
		this.favPaymentMethod = favPaymentMethod;
		this.totalIncome = totalIncome;
	}

	public Integer getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(Integer totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	public Integer getTotalCustomer() {
		return totalCustomer;
	}

	public void setTotalCustomer(Integer totalCustomer) {
		this.totalCustomer = totalCustomer;
	}

	public String getFavPaymentMethod() {
		return favPaymentMethod;
	}

	public void setFavPaymentMethod(String favPaymentMethod) {
		this.favPaymentMethod = favPaymentMethod;
	}

	public Integer getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Integer totalIncome) {
		this.totalIncome = totalIncome;
	}

	public void printReport() {
		System.out.printf("%12s: %s\n", "Total Transaction", totalTransaction);
		System.out.printf("%12s: %s\n", "Total Customer", totalCustomer);
		System.out.printf("%12s: %s\n", "Favorite Payment", favPaymentMethod);
		System.out.printf("%12s: %s\n", "Total Income", totalIncome);
	}
	
}
