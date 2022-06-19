package entities;
import java.util.Date;

public class Admin extends User {
	private Date JoinedDate;
	private String BranchOffice;
	public Date getJoinedDate() {
		return JoinedDate;
	}
	public void setJoinedDate(Date joinedDate) {
		JoinedDate = joinedDate;
	}
	public String getBranchOffice() {
		return BranchOffice;
	}
	public void setBranchOffice(String branchOffice) {
		BranchOffice = branchOffice;
	}
	public Admin(String email, String name, String password, Date joinedDate, String branchOffice) {
		super(email, name, password, "admin");
		JoinedDate = joinedDate;
		BranchOffice = branchOffice;
	}

}
