package HostelManagementSystem;
import com.mysql.cj.protocol.Resultset;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FacultyPayments {
    private Connection connection;
    private Scanner input;

    FacultyPayments(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }
    public void addFacultyPayments() {
        input.nextLine();
        System.out.print("Enter faculty id(fid): ");
        int stdId = input.nextInt();
        System.out.print("Enter total amount: ");
        double totalAmount = input.nextDouble();
        input.nextLine();
        System.out.print("Enter your payment mode: ");
        String paymentMode = input.nextLine();
        System.out.print("Enter your deposit amount in which month: ");
        String paymentMonth = input.nextLine();

        String query = "INSERT INTO facultypayments(faculty_id, amount, payment_mode, which_month)" +
                "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, stdId);
            preparedStatement.setDouble(2, totalAmount);
            preparedStatement.setString(3, paymentMode);
            preparedStatement.setString(4, paymentMonth);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Faculty's Payment Inserted Successfully");
            }else {
                System.out.println("Faculty's Payment Inserted Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewFacultyPayments() {
        String query = "SELECT * FROM facultypayments";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Faculty Payments Details...");
            System.out.println("*............|............|...............|...................|..................|....................*");
            System.out.println("| Payment ID | Faculty ID | Amount        | Payment Mode      | Which Month      | Date Time          |");
            System.out.println("*............|............|...............|...................|..................|....................*");
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int facultyId = resultSet.getInt("faculty_id");
                double totalAmount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String paymentMonth = resultSet.getString("which_month");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-11d | %-11d | %-14.2f | %-18s | %-17s | %-19s |\n", paymentId, facultyId, totalAmount, paymentMode, paymentMonth, dateTime);
                System.out.println("*............|............|...............|...................|..................|....................*");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewParticularFacultyPayments() {
        System.out.print("Enter Payment Id: ");
        int paymentId = input.nextInt();
        System.out.print("Enter Faculty Id: ");
        int facultyId = input.nextInt();

        String query = "SELECT * FROM facultypayments WHERE payment_id = ? AND faculty_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, paymentId);
            preparedStatement.setInt(2, facultyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Particular Student Payments Details...");
            System.out.println("*............|............|...............|....................|..................|......................*");
            System.out.println("| Payment ID | Student ID | Amount        | Payment Mode       | Which Month      | Date Time            |");
            System.out.println("*............|............|...............|....................|..................|......................*");
            while (resultSet.next()) {
                int payId = resultSet.getInt("payment_id");
                int stdId = resultSet.getInt("student_id");
                double totalAmount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String whichMonth = resultSet.getString("which_month");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-11d | %-11d | %-15.2f | %-19s | %-17s | %-21s |\n", payId, stdId, totalAmount, paymentMode, whichMonth, dateTime);
                System.out.println("*............|............|...............|....................|..................|......................*");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void paymentExit() throws InterruptedException {
        System.out.print("Payment Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
    }
}
