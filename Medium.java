import java.sql.*;
import java.util.Scanner;
public class ProductCRUD {
 static final String JDBC_URL = "jdbc:mysql://localhost:3306/testdb";
 static final String USERNAME = "Jigyasu";
 static final String PASSWORD = "Root1234";
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME,
PASSWORD)) {
 Class.forName("com.mysql.cj.jdbc.Driver");
 conn.setAutoCommit(false);
 while (true) {
 System.out.println("\n--- Product Management ---");
 System.out.println("1. Add Product");
 System.out.println("2. View Products");
 System.out.println("3. Update Product");
 System.out.println("4. Delete Product");
 System.out.println("5. Exit");
 System.out.print("Choose an option: ");
 int choice = scanner.nextInt();
 switch (choice) {
 case 1:
     addProduct(conn, scanner);
 break;
 case 2:
 viewProducts(conn);
 break;
 case 3:
 updateProduct(conn, scanner);
 break;
 case 4:
 deleteProduct(conn, scanner);
 break;
 case 5:
 conn.close();
 System.out.println("Program terminated.");
 return;
 default:
 System.out.println("Invalid option.");
 }
 }
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 static void addProduct(Connection conn, Scanner scanner) {
 try {
 System.out.print("Enter Product Name: ");
 String name = scanner.next();
 System.out.print("Enter Price: ");
 double price = scanner.nextDouble();
 System.out.print("Enter Quantity: ");
 int quantity = scanner.nextInt();
 String sql = "INSERT INTO Product(ProductName, Price, Quantity) VALUES (?, ?, ?)";
 try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 pstmt.setString(1, name);
 pstmt.setDouble(2, price);
 pstmt.setInt(3, quantity);
 pstmt.executeUpdate();
 conn.commit();
 System.out.println("Product added successfully.");
 }
 } catch (Exception e) {
 try {
 conn.rollback();
 System.out.println("Transaction rolled back.");
 } catch (SQLException ex) {
 ex.printStackTrace();
 }
 e.printStackTrace();
 }
 }
  static void viewProducts(Connection conn) {
 try (Statement stmt = conn.createStatement()) {
 ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
 System.out.println("\nProductID | ProductName | Price | Quantity");
 while (rs.next()) {
 System.out.printf("%d | %s | %.2f | %d\n",
 rs.getInt("ProductID"),
 rs.getString("ProductName"),
 rs.getDouble("Price"),
 rs.getInt("Quantity"));
 }
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 static void updateProduct(Connection conn, Scanner scanner) {
 try {
 System.out.print("Enter Product ID to update: ");
 int id = scanner.nextInt();
 System.out.print("Enter new Price: ");
 double price = scanner.nextDouble();
 System.out.print("Enter new Quantity: ");
 int quantity = scanner.nextInt();
 String sql = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
 try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 pstmt.setDouble(1, price);
 pstmt.setInt(2, quantity);
 pstmt.setInt(3, id);
 int rows = pstmt.executeUpdate();
 if (rows > 0) {
 conn.commit();
 System.out.println("Product updated successfully.");
 } else {
 System.out.println("Product not found.");
 }
 }
 } catch (Exception e) {
 try {
 conn.rollback();
 } catch (SQLException ex) {
 ex.printStackTrace();
 }
 e.printStackTrace();
 }
 }
 static void deleteProduct(Connection conn, Scanner scanner) {
 try {
 System.out.print("Enter Product ID to delete: ");
 int id = scanner.nextInt();
   String sql = "DELETE FROM Product WHERE ProductID=?";
 try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 pstmt.setInt(1, id);
 int rows = pstmt.executeUpdate();
 if (rows > 0) {
 conn.commit();
 System.out.println("Product deleted successfully.");
 } else {
 System.out.println("Product not found.");
 }
 }
 } catch (Exception e) {
 try {
 conn.rollback();
 } catch (SQLException ex) {
 ex.printStackTrace();
 }
 e.printStackTrace();
 }
 }
}
