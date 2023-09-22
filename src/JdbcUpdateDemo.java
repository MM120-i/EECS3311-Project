import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A sample program that demonstrates how to execute SQL UPDATE statement
 * using JDBC.
 * @author www.codejava.net
 *
 */
public class JdbcUpdateDemo {

	public static void main(String[] args) {
		String dbURL = "jdbc:mysql://localhost/feedback?";
		String username = "root";
		String password = "Feng1234";

		try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

			String sql = "UPDATE Users SET password=?, fullname=?, email=? WHERE username=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "123456789");
			statement.setString(2, "William Henry Bill Gates");
			statement.setString(3, "bill@gatesfoundation.org");
			statement.setString(4, "bill");

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully!");
			}


		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}