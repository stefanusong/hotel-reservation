package entities;
public class User {
	private Integer Id;
	private String Email;
	private String Name;
	private String Password;
	private String Role;
	
	public User(String email, String name, String password, String role) {
		Email = email;
		Name = name;
		Password = password;
		Role = role;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public void EditProfile(String Email,String Password) {
		setEmail(Email);
		setPassword(Password);
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}

}
