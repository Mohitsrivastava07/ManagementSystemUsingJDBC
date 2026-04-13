package HospitalManagementSystem;
import javax.swing.undo.StateEdit;
import java.lang.ClassNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital_db";
    private static final String username = "root";
    private static final String password = "";

   public static void main(String[] args) throws ClassNotFoundException, SQLException {
       Scanner input = new Scanner(System.in);
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       }catch(ClassNotFoundException e) {
           System.out.println(e.getMessage());
       }
       try {
           Connection connection = DriverManager.getConnection(url, username, password);
           Patient patient = new Patient(connection, input);
           Doctors doctor = new Doctors(connection, input);
           while (true) {
               System.out.println("\nHospital Management System");
               System.out.println("1. Add Patient");
               System.out.println("2. View Patients");
               System.out.println("3. View Doctors");
               System.out.println("4. Book Appointments");
               System.out.println("5. View Appointment");
               System.out.println("6. Exit");
               System.out.print("Choose any option(1 to 5): ");
               int choice = input.nextInt();

               switch (choice) {
                   case 1:
                       patient.addPatient();
                       break;
                   case 2:
                       patient.viewPatient();
                       break;
                   case 3:
                       doctor.viewDoctor();
                       break;
                   case 4:
                       bookAppointment(patient, doctor, connection, input);
                       break;
                   case 5:
                       viewAppointment(connection);
                       break;
                   case 6:
                       System.out.println("Thank you for using Hotel Management System");
                       return;
                   default:
                       System.out.println("Enter corrected value choice!!");
               }
           }
       }catch(SQLException e) {
           System.out.println(e.getMessage());
       }
   }
    public static void bookAppointment(Patient patient, Doctors doctors, Connection connection, Scanner input) {
        System.out.print("Enter Patient Id: ");
        int patientsId = input.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorsId = input.nextInt();
        input.nextLine();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = input.nextLine();

        if (patient.getPatientById(patientsId) && doctors.getDoctorById(doctorsId)) {
            if (checkDoctorAvailability(doctorsId, appointmentDate, connection)) {
                String appointmentSql = "INSERT INTO appointments(patient_id, doctor_id, appointment_date)" +
                        "VALUES (" + patientsId + ", " + doctorsId + ", '" + appointmentDate + "')";

                try {
                    Statement statement = connection.createStatement();
                    int affectedRows = statement.executeUpdate(appointmentSql);
                    if (affectedRows > 0) {
                        System.out.println("Appointment Booked!!");
                    }else {
                        System.out.println("Failed to book appointment!!");
                    }
                }catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
       String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = " + doctorId + " AND appointment_date = " + appointmentDate;
       try {
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);
           if (resultSet.next()) {
               int count = resultSet.getInt(1);
               if (count == 0) {
                   return true;
               }else {
                   return false;
               }
           }
       }catch(SQLException e) {
           System.out.println(e.getMessage());
       }
       return false;
    }
    public static void viewAppointment(Connection connection) {
       String sql = "SELECT * FROM appointments";

       try {
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);
           System.out.println("Appointments: ");
           System.out.println("+----------------|--------------|------------|----------------------+");
           System.out.println("| Appointment ID | Patient ID   | Doctor ID  | Appointment Date     |");
           System.out.println("+----------------|--------------|------------|----------------------+");
           while(resultSet.next()) {
               int appointmentId = resultSet.getInt("id");
               int patientAppointId = resultSet.getInt("patient_id");
               int doctorAppointId = resultSet.getInt("doctor_id");
               String appointmentDate = resultSet.getString("appointment_date");

               System.out.printf("| %-14s || %-11s || %-8s || %-19s |\n", appointmentId, patientAppointId, doctorAppointId, appointmentDate);
               System.out.println("|----------------|--------------|------------|----------------------|");
           }
       }catch (SQLException e) {
           System.out.println(e.getMessage());
       }
    }
}
