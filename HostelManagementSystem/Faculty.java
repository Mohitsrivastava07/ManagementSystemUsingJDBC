package HostelManagementSystem;
import java.nio.channels.IllegalChannelGroupException;
import java.sql.*;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.UnknownFormatConversionException;

public class Faculty {
    private Scanner input;
    private Connection connection;

    public Faculty(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }
    public void addFaculty() {
        input.nextLine();
        System.out.print("Enter faculty name: ");
        String facultyName = input.nextLine();
        System.out.print("Enter faculty mobile number: ");
        String facultyPhoneNumber = input.nextLine();
        System.out.print("Enter faculty email: ");
        String facultyEmail = input.nextLine();
        System.out.print("Enter faculty address: ");
        String address = input.nextLine();
        System.out.print("Enter faculty room number: ");
        int roomNumber = input.nextInt();

        String query = "INSERT INTO faculty(name, phone_number, email, address, room_number)" +
                "VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, facultyName);
            preparedStatement.setString(2, facultyPhoneNumber);
            preparedStatement.setString(3, facultyEmail);
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, roomNumber);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Inserted Successfully!!!");
            }else {
                System.out.println("Faculty Inserted Failed!!!");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewAllFaculty() {
        String query = "SELECT * FROM faculty";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("All Faculty Details...");
            System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            System.out.println("| FID   | Faculty Name          | Phone number      | Email                     | Address               | Room number     | Joining Date        |");
            System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            while (resultSet.next()) {
                int facultyId = resultSet.getInt("fid");
                String facultyName = resultSet.getString("name");
                String facultyPhoneNumber = resultSet.getString("phone_number");
                String facultyEmail = resultSet.getString("email");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String joiningDate = resultSet.getString("joining_date").toString();

                System.out.printf("| %-6d | %-22s | %-18s | %-26s | %-22s | %-16s | %-20s |\n", facultyId, facultyName, facultyPhoneNumber, facultyEmail, address, roomNumber, joiningDate);
                System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewParticularFaculty() {
        System.out.print("Enter faculty id(fid): ");
        int facultyId = input.nextInt();
        input.nextLine();
        System.out.print("Enter faculty name: ");
        String facultyName = input.nextLine();

        String query = "SELECT * from faculty WHERE fid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, facultyId);
            preparedStatement.setString(2, facultyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("All Faculty Details...");
            System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            System.out.println("| FID   | Faculty Name          | Phone number      | Email                     | Address               | Room number     | Joining Date        |");
            System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            while (resultSet.next()) {
                int fltId = resultSet.getInt("fid");
                String fltName = resultSet.getString("name");
                String facultyPhoneNumber = resultSet.getString("phone_number");
                String facultyEmail = resultSet.getString("email");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String joiningDate = resultSet.getString("joining_date").toString();

                System.out.printf("| %-6d | %-22s | %-18s | %-26s | %-22s | %-16s | %-20s |\n", fltId, fltName, facultyPhoneNumber, facultyEmail, address, roomNumber, joiningDate);
                System.out.println("*.......|.......................|...................|...........................|.......................|.................|.....................|");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateFaculty() {
        System.out.print("Enter faculty id(fid) to updates: ");
        int facultyId = input.nextInt();
        input.nextLine();

        System.out.print("Enter new faculty name: ");
        String newFacultyName = input.nextLine();
        System.out.print("Enter new faculty phone number: ");
        String newFacultyPhoneNumber = input.nextLine();
        System.out.print("Enter new faculty email: ");
        String newFacultyEmail = input.nextLine();
        System.out.print("Enter new faculty address: ");
        String newAddress = input.nextLine();
        System.out.print("Enter new faculty room number: ");
        String newRoomNumber = input.nextLine();

        String query = "UPDATE faculty SET name = ?, phone_number = ?, email = ?, address = ?, room_number = ? WHERE fid = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newFacultyName);
            preparedStatement.setString(2, newFacultyPhoneNumber);
            preparedStatement.setString(3, newFacultyEmail);
            preparedStatement.setString(4, newAddress);
            preparedStatement.setString(5, newRoomNumber);
            preparedStatement.setInt(6, facultyId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Update Successfully!!");
            }else {
                System.out.println("Faculty Update Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteFaculty() {
        System.out.print("Enter faculty id(fid): ");
        int facultyId = input.nextInt();
        input.nextLine();
        System.out.print("Enter faculty name: ");
        String facultyName = input.nextLine();

        String query = "DELETE FROM faculty WHERE fid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, facultyId);
            preparedStatement.setString(2, facultyName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Deleted Successfully!!");
            }else {
                System.out.println("Faculty Deleted Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getRoom() {
        System.out.print("Enter faculty id(fid): ");
        int facultyId = input.nextInt();
        input.nextLine();
        System.out.print("Enter faculty name: ");
        String facultyName = input.nextLine();

        String query = "SELECT room_number from faculty WHERE fid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, facultyId);
            preparedStatement.setString(2, facultyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int room_number = resultSet.getInt("room_number");
                System.out.println("Your room number is " + room_number + " where fid: " + facultyId + " and name: " + facultyName);
            }else {
                System.out.println("No Any faculties on this faculty id and name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int j = 5;
        while (j != 0) {
            System.out.print(".");
            Thread.sleep(450);
            j--;
        }
    }
}
