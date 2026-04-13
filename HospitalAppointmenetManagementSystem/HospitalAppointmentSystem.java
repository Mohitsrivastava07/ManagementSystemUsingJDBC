package HospitalAppointmenetManagementSystem;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HospitalAppointmentSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospitalappointment_db";
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
            Patients patients = new Patients(connection, input);
            Doctors doctors = new Doctors(connection, input);
            Appointments appointments = new Appointments(connection, input);

            while (true) {
                System.out.println();
                System.out.println("1. Patients");
                System.out.println("2. Doctors");
                System.out.println("3. Appointments");

                int choice1;
                System.out.print("Choose any option: ");
                if (input.hasNextInt()) {
                    choice1 = input.nextInt();
                    switch(choice1) {
                        case 1:
                            while (true) {
                                System.out.println();
                                System.out.println("\nHospital Appointment Management System");
                                System.out.println("1. Add Patient");
                                System.out.println("2. View Particular Patients");
                                System.out.println("3. View Patients");
                                System.out.println("4. Delete Patients");
                                System.out.println("5. Update Patients");
                                System.out.println("6. Exit");
                                int choice2;
                                try {
                                    System.out.print("Choose any option: ");
                                    choice2 = input.nextInt();
                                    switch(choice2) {
                                        case 1: patients.addPatients(); break;
                                        case 2: patients.viewParticularPatients(); break;
                                        case 3: patients.viewPatients(); break;
                                        case 4: patients.deletePatient(); break;
                                        case 5: patients.updatePatient(); break;
                                        case 6: patients.exit(); break;
                                        default:
                                            System.out.println("Invalid Choice!!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Try again! Only numeric value is input not alphabets");
                                    input.nextLine();
                                }
                            }

                        case 2:
                            while (true) {
                                System.out.println();
                                System.out.println("\nHospital Appointment Management System");
                                System.out.println("1. View Doctors");
                                System.out.println("2. Update Doctors");
                                System.out.println("3. Get Doctor By Id");
                                System.out.println("3. Exit");

                                int choice3;
                                System.out.print("Choose any option: ");
                                if (input.hasNextInt()) {
                                    choice3 = input.nextInt();
                                    switch (choice3) {
                                        case 1: doctors.viewDoctors(); break;
                                        case 2: doctors.updateDoctors(); break;
                                        case 3: doctors.exit(); break;
                                        default:
                                            System.out.println("Invalid Choice! Try again...");
                                    }
                                } else {
                                    System.out.println("Invalid Choice! Try Again...");
                                    input.next();
                                }
                            }
                        case 3:
                            while (true) {
                                System.out.println("1. Book Appointment");
                                System.out.println("2. View Appointment");

                                int choice4;
                                System.out.print("Choose any option: ");
                                if (input.hasNextInt()) {
                                    choice4 = input.nextInt();
                                    switch (choice4) {
                                        case 1: appointments.bookAppointment(); break;
                                        case 2: appointments.viewAppointment(); break;
                                        default:
                                            System.out.println("Invalid Choice! Try again...");
                                    }
                                } else {
                                    System.out.println("Invalid Choice! Try again...");
                                    input.next();
                                }
                            }
                        default:
                            System.out.println("Invalid Choice!! Try again...");
                    }
                } else {
                    System.out.println("Invalid Choice! Try again...");
                    input.next();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}


