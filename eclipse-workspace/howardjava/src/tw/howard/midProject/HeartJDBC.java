package tw.howard.midProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class HeartJDBC {

	public static void insert() {
		try {
			Properties prop = new Properties();
			prop.put("user", "root");
			prop.put("password", "root");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/heartattack", prop);
			Statement st = con.createStatement();
			st.execute("USE ppp");
			st.execute("CREATE TABLE t1(f1 varchar(10), f2 varchar(10))");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

}
