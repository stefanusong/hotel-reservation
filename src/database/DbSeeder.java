package database;

import java.sql.SQLException;

public class DbSeeder {
	Connect con = Connect.getConnection();
	
	public void seedAdmins() {
		try {
			String query1 = "SELECT * FROM users WHERE role = 'admin'";
			boolean isAnyAdmin = con.executeQuery(query1).next();
			if(!isAnyAdmin) {
				String query2 = "INSERT INTO users(email, name, password, joined_date, branch_office, role)\r\n" + 
						"VALUES('admin@gmail.com', 'Superadmin', 'password', CURRENT_DATE, 'Jakarta', 'admin');";
				con.executeUpdate(query2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void seedRooms() {
		try {
			String query1 = "SELECT * FROM rooms";
			boolean isAnyRoom = con.executeQuery(query1).next();
			if(!isAnyRoom) {
				String query2 = "INSERT INTO rooms VALUES\r\n" + 
						"(1,2,500000,'regular'),\r\n" + 
						"(2,4,1000000,'premium'),\r\n" + 
						"(3,4,1500000,'luxury');";
				con.executeUpdate(query2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
