package HospitalAppointmenetManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Appointments {
    private Connection connection;
    private Scanner input;

    public Appointments(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void bookAppointment() {
        int patientId;
        int doctorId;
        String appointmentDate;

        Patients patients = new Patients(connection, input);
        Doctors doctors = new Doctors(connection, input);

        while (true) {
            System.out.print("Enter patient Id: ");
            if (input.hasNextInt()) {
                patientId = input.nextInt();
                input.nextLine();
                while (true) {
                    System.out.print("Enter doctor Id: ");
                    if (input.hasNextInt()) {
                        doctorId = input.nextInt();
                        input.nextLine();
                        while (true) {
                            System.out.print("Enter appointment date (YYYY-MM-DD): ");
                            appointmentDate = input.nextLine();
                            if (appointmentDate.isEmpty()) {
                                System.out.println("Invalid appointment date! Try Again...");
                            } else {
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Invalid doctor id! Must be number...");
                        input.next();
                    }
                }
                break;
            } else {
                System.out.println("Invalid patient id! Must be number");
                input.next();
            }
        }
        if (patients.getPatientById(patientId) && doctors.getDoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {

                String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setString(3, appointmentDate);
                    int affectedRows = ps.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment Booked!!");
                    } else {
                        System.out.println("Failed to book appointment!!");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Doctor is not available on this date.");
            }
        } else {
            System.out.println("Invalid patient or doctor ID!");
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

    public void viewAppointment() {
        String sql = "SELECT * FROM appointments";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Appointments: ");
            System.out.println("+----------------|--------------|-------------|----------------------|---------------------+");
            System.out.println("| Appointment ID | Patient ID   | Doctor ID   | Appointment Date     | Date Time           |");
            System.out.println("+----------------|--------------|-------------|----------------------+---------------------|");
            while(resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                int patientAppointId = resultSet.getInt("patient_id");
                int doctorAppointId = resultSet.getInt("doctor_id");
                String appointmentDate = resultSet.getString("appointment_date");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-14s | %-11s | %-9s | %-19s | %-19s |\n", appointmentId, patientAppointId, doctorAppointId, appointmentDate, date_time);
                System.out.println("+----------------|--------------|------------|----------------------+---------------------|");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
