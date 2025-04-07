import java.sql.*;
public class FetchEmployeeData {
 public static void main(String[] args) {
 String jdbcURL = "jdbc:mysql://localhost:3306/testdb";
 String username = "Jigyasu";
 String password = "Root1234";
 try {
 Class.forName("com.mysql.cj.jdbc.Driver");
 Connection conn = DriverManager.getConnection(jdbcURL, username, password);
 Statement stmt = conn.createStatement();
 String query = "SELECT EmpID, Name, Salary FROM Employee";
 ResultSet rs = stmt.executeQuery(query);
 while (rs.next()) {
 int empId = rs.getInt("EmpID");
 String name = rs.getString("Name");
 double salary = rs.getDouble("Salary");
   System.out.printf("EmpID: %d | Name: %s | Salary: %.2f\n", empId, name, salary);
 }
 rs.close();
 stmt.close();
 conn.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
}
