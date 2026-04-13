package HospitalManagementSystem;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.SQLException;

public class Doctors {
    private Connection connection;

    public Doctors(Connection connection, Scanner input) {
        this.connection = connection;
    }
    public void viewDoctor() {
        String sql = "SELECT * FROM doctors";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Doctors: ");
            System.out.println("+-----------|----------------------|-----------------------------+");
            System.out.println("| Doctor ID | Doctor Name          | Specialization              |");
            System.out.println("|-----------|----------------------|-----------------------------|");
            while (resultSet.next()) {
                int doctorId = resultSet.getInt("id");
                String doctorName = resultSet.getString("doctor_name");
                String specialization = resultSet.getString("specialisation");

                System.out.printf("|%-11s||%-21s||%-28s|\n", doctorId, doctorName, specialization);
                System.out.println("|-----------|----------------------|-----------------------------|");
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
}
