package HostelManagementSystem;
import com.mysql.cj.protocol.Resultset;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentsPayments {
    private Connection connection;
    private Scanner input;

    StudentsPayments(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }
    public void addStudentsPayments() {
        input.nextLine();
        System.out.print("Enter student id(sid): ");
        int stdId = input.nextInt();
        System.out.print("Enter total amount: ");
        double totalAmount = input.nextDouble();
        input.nextLine();
        System.out.print("Enter your payment mode: ");
        String paymentMode = input.nextLine();
        System.out.print("Enter your deposit amount in which month: ");
        String paymentMonth = input.nextLine();

        String query = "INSERT INTO studentspayments(student_id, amount, payment_mode, which_month)" +
                "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, stdId);
            preparedStatement.setDouble(2, totalAmount);
            preparedStatement.setString(3, paymentMode);
            preparedStatement.setString(4, paymentMonth);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Student's Payment Inserted Successfully");
            }else {
                System.out.println("Student's Payment Inserted Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewStudentsPayments() {
        String query = "SELECT * FROM studentspayments";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Student Payments Details...");
            System.out.println("*............|............|...............|................|.................|......................*");
            System.out.println("| Payment ID | Student ID | Amount        | Payment Mode   | Which Month     | Date Time            |");
            System.out.println("*............|............|...............|................|.................|......................*");
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int studentId = resultSet.getInt("student_id");
                double totalAmount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String paymentMonth = resultSet.getString("which_month");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-11d | %-11d | %-14.2f | %-15s | %-16s | %-21s |\n", paymentId, studentId, totalAmount, paymentMode, paymentMonth, dateTime);
                System.out.println("*............|............|...............|................|.................|......................*");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewParticularStudentsPayments() {
        System.out.print("Enter Payment Id: ");
        int paymentId = input.nextInt();
        System.out.print("Enter Student Id: ");
        int studentId = input.nextInt();

        String query = "SELECT * FROM studentspayments WHERE payment_id = ? AND student_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, paymentId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Particular Student Payments Details...");
            System.out.println("*............|............|...............|..................|..................|.....................*");
            System.out.println("| Payment ID | Student ID | Amount        | Payment Mode     | Which Month      | Date Time           |");
            System.out.println("*............|............|...............|..................|..................|.....................*");
            while (resultSet.next()) {
                int payId = resultSet.getInt("payment_id");
                int stdId = resultSet.getInt("student_id");
                double totalAmount = resultSet.getDouble("amount");
                String paymentMode = resultSet.getString("payment_mode");
                String whichMonth = resultSet.getString("which_month");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-11d | %-11d | %-16.2f | %-17s | %-17s | %-20s |\n", payId, stdId, totalAmount, paymentMode, whichMonth, dateTime);
                System.out.println("*............|............|...............|..................|..................|.....................*");
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
