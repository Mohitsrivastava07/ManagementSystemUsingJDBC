package HostelManagementSystem;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class HostelManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hostel_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Students std = new Students(connection, input);
            Faculty flt = new Faculty(connection, input);
            StudentsPayments stdPay = new StudentsPayments(connection, input);
            FacultyPayments fltPay = new FacultyPayments(connection, input);
            while (true) {
                System.out.println("\n1. Students Information Checked");
                System.out.println("2. Faculty Information Checked");
                System.out.println("3. Students Payments Details");
                System.out.println("4. Faculty Payments Details");
                System.out.print("Choice any one: ");
                int checkedChoice = input.nextInt();
                switch (checkedChoice) {
                    case 1:
                        System.out.println("\nHOSTEL MANAGEMENT SYSTEM");
                        System.out.println("1. Add Students");
                        System.out.println("2. View All Students");
                        System.out.println("3. View Particular Students");
                        System.out.println("4. Get Room Number");
                        System.out.println("5. Update Students");
                        System.out.println("6. Delete Students");
                        System.out.println("0. Exit");
                        System.out.print("Choice any option(0 to 6): ");
                        int stdChoice = input.nextInt();

                        switch (stdChoice) {
                            case 1: std.addStudents(); break;
                            case 2: std.viewAllstudents(); break;
                            case 3: std.viewParticularStudent(); break;
                            case 4: std.getRoom(); break;
                            case 5: std.updateStudents(); break;
                            case 6: std.deleteStudents(); break;
                            case 0: std.exit(); return;
                            default: System.out.println("Invalid Choice! Try Again");
                        }
                        break;
                    case 2:
                        System.out.println("\nHOSTEL MANAGEMENT SYSTEM");
                        System.out.println("1. Add Faculty");
                        System.out.println("2. View All Faculty");
                        System.out.println("3. View Particular Faculty");
                        System.out.println("4. Get Room Number");
                        System.out.println("5. Update Faculty");
                        System.out.println("6. Delete Faculty");
                        System.out.println("0. Exit");
                        System.out.print("Choice any option(0 to 6): ");
                        int facChoice = input.nextInt();

                        switch (facChoice) {
                            case 1: flt.addFaculty(); break;
                            case 2: flt.viewAllFaculty(); break;
                            case 3: flt.viewParticularFaculty(); break;
                            case 4: flt.getRoom(); break;
                            case 5: flt.updateFaculty(); break;
                            case 6: flt.deleteFaculty(); break;
                            case 0: flt.exit(); return;
                            default: System.out.println("Invalid Choice! Try Again");
                        }
                        break;
                    case 3:
                        System.out.println("\nStudents Payment Details");
                        System.out.println("1. Add Students Payments");
                        System.out.println("2. View Students Payments");
                        System.out.println("3. View Particular Students Payments");
                        System.out.println("0. Exit");
                        System.out.print("Choice any option: ");
                        int stdPaymentChoice = input.nextInt();

                        switch (stdPaymentChoice) {
                            case 1: stdPay.addStudentsPayments(); break;
                            case 2: stdPay.viewStudentsPayments(); break;
                            case 3: stdPay.viewParticularStudentsPayments(); break;
                            case 0: stdPay.paymentExit(); break;
                            default: System.out.println("Invalid Choice! Try Again");
                        }
                        break;
                    case 4:
                        System.out.println("\nFaculty Payment Details");
                        System.out.println("1. Add Faculty Payments");
                        System.out.println("2. View Faculty Payments");
                        System.out.println("3. View Particular Faculty Payments");
                        System.out.println("0. Exit");
                        System.out.print("Choice any option: ");
                        int fltPaymentChoice = input.nextInt();

                        switch (fltPaymentChoice) {
                            case 1: fltPay.addFacultyPayments(); break;
                            case 2: fltPay.viewFacultyPayments(); break;
                            case 3: fltPay.viewParticularFacultyPayments(); break;
                            case 0: fltPay.paymentExit(); break;
                            default: System.out.println("Invalid Choice! Try Again");
                        }
                    default:
                        System.out.println("Invalid Choice!! Try Again");
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
