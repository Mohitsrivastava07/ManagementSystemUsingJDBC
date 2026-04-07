package StudentHostelFeesSystem;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class StudentHostelFeesSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/studenthostelfees_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        Scanner input = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Students std = new Students(connection, input);
            while (true) {
                System.out.println("\nStudent Hostel Fees Management System");
                System.out.println("1. Add Students");
                System.out.println("2. View All Students");
                System.out.println("3. View Particular Student");
                System.out.println("4. Update Students");
                System.out.println("5. Delete Students");
                System.out.println("0. Exit");
                System.out.print("Choice any option(0 to 5): ");
                int choice = input.nextInt();

                switch (choice) {
                    case 1: std.addStudents(); break;
                    case 2:  std.viewAllStudents(); break;
                    case 3: std.viewParticularStudent(); break;
                    case 4: std.updateStudents(); break;
                    case 5: std.deleteStudents(); break;
                    case 0: std.exit(); break;
                    default:
                        System.out.println("Choice only number from 0 to 5 NOT other");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (AlphabetWithNumberException f) {
            System.out.println(f.getMessage());
        } catch (EmailException g) {
            System.out.println(g.getMessage());
        } catch (PhoneNumberException h) {
            System.out.println(h.getMessage());
        }
    }
}
