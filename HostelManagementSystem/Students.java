package HostelManagementSystem;
import java.nio.channels.IllegalChannelGroupException;
import java.sql.*;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.UnknownFormatConversionException;

public class Students {
    private Scanner input;
    private Connection connection;

    public Students(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }
    public void addStudents() {
        input.nextLine();
        System.out.print("Enter student name: ");
        String studentName = input.nextLine();
        System.out.print("Enter your Course: ");
        String courseName = input.nextLine();
        System.out.print("Enter student mobile number: ");
        String studentPhoneNumber = input.nextLine();
        System.out.print("Enter student email: ");
        String studentEmail = input.nextLine();
        System.out.print("Enter parent name: ");
        String parentName = input.nextLine();
        System.out.print("Enter parent mobile number: ");
        String parentPhoneNumber = input.nextLine();
        System.out.print("Enter student address: ");
        String address = input.nextLine();
        System.out.print("Enter student room number: ");
        int roomNumber = input.nextInt();

        String query = "INSERT INTO students(name, course, phone_number, email, parent_name, parent_number, address, room_number)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, studentPhoneNumber);
            preparedStatement.setString(4, studentEmail);
            preparedStatement.setString(5, parentName);
            preparedStatement.setString(6, parentPhoneNumber);
            preparedStatement.setString(7, address);
            preparedStatement.setInt(8, roomNumber);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Inserted Successfully!!!");
            }else {
                System.out.println("Student Inserted Failed!!!");
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewAllstudents() {
        String query = "SELECT * FROM students";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("All Students Details...");
            System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            System.out.println("| SID   | Student Name          | Course          | Phone number      | Email                     | Parent Name           | Phone number       | Address              | Room number   | Joining Date         |");
            System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            while (resultSet.next()) {
                int studentId = resultSet.getInt("sid");
                String studentName = resultSet.getString("name");
                String courseName = resultSet.getString("course");
                String studentPhoneNumber = resultSet.getString("phone_number");
                String studentEmail = resultSet.getString("email");
                String parentName = resultSet.getString("parent_name");
                String parentPhoneNumber = resultSet.getString("parent_number");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String joiningDate = resultSet.getString("joining_date").toString();

                System.out.printf("| %-6d | %-22s | %-16s | %-18s | %-26s | %-22s | %-19s | %-21s | %-14s | %-21s |\n", studentId, studentName, courseName, studentPhoneNumber, studentEmail, parentName, parentPhoneNumber, address, roomNumber, joiningDate);
                System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewParticularStudent() {
        System.out.print("Enter student id(sid): ");
        int studentId = input.nextInt();
        input.nextLine();
        System.out.print("Enter student name: ");
        String studentName = input.nextLine();

        String query = "SELECT * from students WHERE sid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("All Students Details...");
            System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            System.out.println("| SID   | Student Name          | Course          | Phone number      | Email                     | Parent Name           | Phone number       | Address              | Room number   | Joining Date         |");
            System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            while (resultSet.next()) {
                int stdID = resultSet.getInt("sid");
                String stdName = resultSet.getString("name");
                String courseName = resultSet.getString("course");
                String studentPhoneNumber = resultSet.getString("phone_number");
                String studentEmail = resultSet.getString("email");
                String parentName = resultSet.getString("parent_name");
                String parentPhoneNumber = resultSet.getString("parent_number");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String joiningDate = resultSet.getString("joining_date").toString();

                System.out.printf("| %-6d | %-22s | %-16s | %-18s | %-26s | %-22s | %-19s | %-21s | %-14s | %-21s |\n", stdID, stdName, courseName, studentPhoneNumber, studentEmail, parentName, parentPhoneNumber, address, roomNumber, joiningDate);
                System.out.println("*.......|.......................|.................|...................|...........................|.......................|....................|......................|...............|......................|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateStudents() {
        System.out.print("Enter student id(sid) to updates: ");
        int studentId = input.nextInt();
        input.nextLine();

        System.out.print("Enter new student name: ");
        String newStudentName = input.nextLine();
        System.out.print("Enter new student course: ");
        String newCourseName = input.nextLine();
        System.out.print("Enter new student phone number: ");
        String newStudentPhoneNumber = input.nextLine();
        System.out.print("Enter new student email: ");
        String newStudentEmail = input.nextLine();
        System.out.print("Enter new student parent name: ");
        String newParentName = input.nextLine();
        System.out.print("Enter new student parent number: ");
        String newParentPhoneNumber = input.nextLine();
        System.out.print("Enter new student address: ");
        String newAddress = input.nextLine();
        System.out.print("Enter new student room number: ");
        String newRoomNumber = input.nextLine();

        String query = "UPDATE students SET name = ?, course = ?, phone_number = ?, email = ?, parent_name = ?, parent_number = ?, address = ?, room_number = ? WHERE sid = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newStudentName);
            preparedStatement.setString(2, newCourseName);
            preparedStatement.setString(3, newStudentPhoneNumber);
            preparedStatement.setString(4, newStudentEmail);
            preparedStatement.setString(5, newParentName);
            preparedStatement.setString(6, newParentPhoneNumber);
            preparedStatement.setString(7, newAddress);
            preparedStatement.setString(8, newRoomNumber);
            preparedStatement.setInt(9, studentId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Update Successfully!!");
            }else {
                System.out.println("Student Update Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteStudents() {
        System.out.print("Enter student id(sid): ");
        int studentId = input.nextInt();
        input.nextLine();
        System.out.print("Enter student name: ");
        String studentName = input.nextLine();

        String query = "DELETE FROM students WHERE sid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, studentName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Deleted Successfully!!");
            }else {
                System.out.println("Student Deleted Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getRoom() {
        System.out.print("Enter student id: ");
        int studentId = input.nextInt();
        input.nextLine();
        System.out.print("Enter student name: ");
        String studentName = input.nextLine();

        String query = "SELECT room_number from students WHERE sid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int room_number = resultSet.getInt("room_number");
                System.out.println("Your room number is " + room_number + " where sid: " + studentId + " and name: " + studentName);
            }else {
                System.out.println("No Any students on this student id and name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
    }
}
