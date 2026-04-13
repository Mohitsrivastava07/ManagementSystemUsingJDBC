package HospitalAppointmenetManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Doctors {
    private Connection connection;
    private Scanner input;

    public Doctors(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void viewDoctors() {
        String sqlQuery = "SELECT * FROM doctors;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Doctors: ");
            System.out.println("+--------------|--------------------------|----------------------------------|----------------------+");
            System.out.println("| Doctors ID   | Doctors name             | Doctors Specialization           | Date Time            |");
            System.out.println("|--------------|--------------------------|----------------------------------|----------------------|");
            while (resultSet.next()) {
                int d_Id = resultSet.getInt("doctor_id");
                String d_Name = resultSet.getString("doctor_name");
                String d_Specialization = resultSet.getString("doctor_specialization");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12d | %-24s | %-32s | %-20s |\n", d_Id, d_Name, d_Specialization, date_time);
                System.out.println("|--------------|--------------------------|----------------------------------|----------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDoctors() {
        int doctorId;
        String updateDoctorName;
        String updateDoctorSpecialization;

        while (true) {
            System.out.print("Enter doctor id: ");
            if (input.hasNextInt()) {
                doctorId = input.nextInt();
                input.nextLine();
                break;
            } else {
                System.out.println("Try again! Must be number...");
                input.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter update doctor name: ");
                updateDoctorName = input.nextLine();
                if (updateDoctorName.matches(".*\\d.*")) {
                    throw new IncludingNumberException("Invalid update patient name! Try again...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter update doctor specialization: ");
                            updateDoctorSpecialization = input.nextLine();
                            if (updateDoctorSpecialization.matches(".*\\d.*")) {
                                throw new IncludingNumberException("Must be contain only string NOT number");
                            }
                            break;
                        } catch (IncludingNumberException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                break;
            } catch (IncludingNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "UPDATE patients SET doctor_name = ?, doctor_specialization = ? WHERE doctor_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, updateDoctorName);
            ps.setString(2, updateDoctorSpecialization);
            ps.setInt(3, doctorId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Updates Successfully!!");
            } else {
                System.out.println("Doctor Updates Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean getDoctorById(int doctorId) {
        String sql = "SELECT * FROM doctors WHERE id = " + doctorId;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }else {
                return false;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(400);
            i--;
        }
    }
}
