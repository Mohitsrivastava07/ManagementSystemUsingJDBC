package HospitalManagementSystem;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.SQLException;

public class Patient {
    private Connection connection;
    private Scanner input;

    public Patient(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addPatient() {
        input.nextLine();
        System.out.print("Enter patient name: ");
        String patientName = input.nextLine();
        System.out.print("Enter patient age: ");
        int patientAge = input.nextInt();
        input.nextLine();
        System.out.print("Enter patient gender: ");
        String patientGender = input.nextLine();

        String sql = "INSERT INTO patients(patient_name, patient_age, gender)" +
                "VALUES ('" + patientName + "', " + patientAge + ", '" + patientGender + "')";

        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Patient Data Insertion Successfully!!");
            } else {
                System.out.println("Patient Data Insertion Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewPatient() {
        String sql = "SELECT * FROM patients";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Patient: ");
            System.out.println("+------------|---------------------|--------------|------------------+");
            System.out.println("| Patient ID | Guest Name          | Patient Age  | Gender           |");
            System.out.println("|------------|---------------------|--------------|------------------|");
            while (resultSet.next()) {
                int patientId = resultSet.getInt("id");
                String patientName = resultSet.getString("patient_name");
                int patientAge = resultSet.getInt("patient_age");
                String gender = resultSet.getString("gender");

                System.out.printf("| %-10s || %-18s || %-10s || %-16s |\n", patientId, patientName, patientAge, gender);
                System.out.println("|------------|---------------------|--------------|------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean getPatientById(int patientId) {
        String sql = "SELECT * FROM patients WHERE id = " + patientId;

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
}
