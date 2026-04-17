package StudentFacultyHostelFees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentFacultyHostelFeesSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/studentfacultyhostelfees_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner input = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Students students = new Students(connection, input);
            Faculty faculty = new Faculty(connection, input);

            while (true) {
                System.out.println();
                System.out.println("1. Student");
                System.out.println("2. Faculty");
                System.out.print("Choose any option: ");
                if (input.hasNextInt()) {
                    int choice1 = input.nextInt();
                    switch (choice1) {
                        case 1:
                            while (true) {
                                System.out.println();
                                System.out.println("1. Add Students");
                                System.out.println("2.View All Students");
                                System.out.println("3. View Particular Students");
                                System.out.println("4. Update Students");
                                System.out.println("5. Delete Students");
                                System.out.println("6. Exit");

                                System.out.print("Choose any option: ");
                                if (input.hasNextInt()) {
                                    int choice2 = input.nextInt();
                                    input.nextLine();
                                    switch (choice2) {
                                        case 1: students.addStudents(); break;
                                        case 2: students.viewAllstudents(); break;
                                        case 3: students.viewParticularStudent(); break;
                                        case 4: students.updateStudents(); break;
                                        case 5: students.deleteStudents(); break;
                                        case 6: students.exit(); return;
                                        default:
                                            System.out.println("Invalid choice! Try Again...");
                                    }
                                } else {
                                    System.out.println("Invalid Input! Must be number...");
                                    input.next();
                                }
                            }

                        case 2:
                            while (true) {
                                System.out.println();
                                System.out.println("1. Add Faculty");
                                System.out.println("2.View All Faculty");
                                System.out.println("3. View Particular Faculty");
                                System.out.println("4. Update Faculty");
                                System.out.println("5. Delete Faculty");
                                System.out.println("6. Exit");

                                System.out.print("Choose any option: ");
                                if (input.hasNextInt()) {
                                    int choice3 = input.nextInt();
                                    input.nextLine();
                                    switch (choice3) {
                                        case 1:
                                            faculty.addFaculty();
                                            break;
                                        case 2:
                                            faculty.viewAllFaculty();
                                            break;
                                        case 3:
                                            faculty.viewParticularFaculty();
                                            break;
                                        case 4:
                                            faculty.updateFaculty();
                                            break;
                                        case 5:
                                            faculty.deleteFaculty();
                                            break;
                                        case 6:
                                            faculty.exit();
                                            break;
                                        default:
                                            System.out.println("Invalid choice! Try Again...");
                                    }
                                } else {
                                    System.out.println("Invalid Input! Must be number...");
                                    input.next();
                                }
                            }
                        default:
                            System.out.println("Invalid Choice! Try Again...");
                    }
                } else {
                    System.out.println("Invalid choice! Must be number...");
                    input.next();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
