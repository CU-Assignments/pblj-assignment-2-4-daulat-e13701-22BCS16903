Student.java
public class Student {
 private int studentId;
 private String name;
 private String department;
 private double marks;
 public Student(int studentId, String name, String department, double marks) {
 this.studentId = studentId;
 this.name = name;
 this.department = department;
 this.marks = marks;
 }
 public int getStudentId() { return studentId; }
 public String getName() { return name; }
 public String getDepartment() { return department; }
 public double getMarks() { return marks; }
 @Override
 public String toString() {
 return String.format("ID: %d | Name: %s | Dept: %s | Marks: %.2f", studentId, name,
department, marks);
 }
}
StudentController.java
  import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class StudentController {
 private Connection conn;
 public StudentController() {
 try {
 Class.forName("com.mysql.cj.jdbc.Driver");
 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "Jigyasu",
"Root1234");
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 public void addStudent(Student student) {
 String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";
 try (PreparedStatement stmt = conn.prepareStatement(sql)) {
 stmt.setInt(1, student.getStudentId());
 stmt.setString(2, student.getName());
 stmt.setString(3, student.getDepartment());
 stmt.setDouble(4, student.getMarks());
 stmt.executeUpdate();
 System.out.println("Student added successfully.");
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 public List<Student> getAllStudents() {
 List<Student> list = new ArrayList<>();
 String sql = "SELECT * FROM Student";
 try (Statement stmt = conn.createStatement();
 ResultSet rs = stmt.executeQuery(sql)) {
 while (rs.next()) {
 list.add(new Student(rs.getInt("StudentID"), rs.getString("Name"),
rs.getString("Department"), rs.getDouble("Marks")));
 }
 } catch (Exception e) {
 e.printStackTrace();
 }
 return list;
 }
 public void updateStudentMarks(int id, double newMarks) {
 String sql = "UPDATE Student SET Marks = ? WHERE StudentID = ?";
 try (PreparedStatement stmt = conn.prepareStatement(sql)) {
 stmt.setDouble(1, newMarks);
 stmt.setInt(2, id);
 int rows = stmt.executeUpdate();
 System.out.println(rows > 0 ? "Marks updated." : "Student not found.");
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
   public void deleteStudent(int id) {
 String sql = "DELETE FROM Student WHERE StudentID = ?";
 try (PreparedStatement stmt = conn.prepareStatement(sql)) {
 stmt.setInt(1, id);
 int rows = stmt.executeUpdate();
 System.out.println(rows > 0 ? "Student deleted." : "Student not found.");
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 public void closeConnection() {
 try {
 if (conn != null) conn.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
}
StudentApp.java
import java.util.List;
import java.util.Scanner;
public class StudentApp {
 public static void main(String[] args) {
 StudentController controller = new StudentController();
 Scanner sc = new Scanner(System.in);
 while (true) {
 System.out.println("\n--- Student Management ---");
 System.out.println("1. Add Student");
 System.out.println("2. View Students");
 System.out.println("3. Update Marks");
 System.out.println("4. Delete Student");
 System.out.println("5. Exit");
 System.out.print("Choose an option: ");
 int choice = sc.nextInt();
 switch (choice) {
 case 1:
 System.out.print("Enter ID: ");
 int id = sc.nextInt();
 sc.nextLine();
 System.out.print("Enter Name: ");
 String name = sc.nextLine();
 System.out.print("Enter Department: ");
 String dept = sc.nextLine();
 System.out.print("Enter Marks: ");
 double marks = sc.nextDouble();
 Student s = new Student(id, name, dept, marks);
 controller.addStudent(s);
 break;
 case 2:
 List<Student> students = controller.getAllStudents();
 for (Student stu : students) {
   System.out.println(stu);
 }
 break;
 case 3:
 System.out.print("Enter ID to update marks: ");
 int uid = sc.nextInt();
 System.out.print("Enter new marks: ");
 double newMarks = sc.nextDouble();
 controller.updateStudentMarks(uid, newMarks);
 break;
 case 4:
 System.out.print("Enter ID to delete: ");
 int did = sc.nextInt();
 controller.deleteStudent(did);
 break;
 case 5:
 controller.closeConnection();
 System.out.println("Exiting...");
 return;
 default:
 System.out.println("Invalid option.");
 }
 }
 }
}
