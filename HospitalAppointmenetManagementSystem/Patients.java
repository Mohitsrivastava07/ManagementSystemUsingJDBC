package HospitalAppointmenetManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner input;

    public Patients(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addPatients() throws SQLException {
        input.nextLine();
        String patientName;
        int patientAge;
        String patientGender;

        while (true) {
            try {
                System.out.print("Enter patient name: ");
                patientName = input.nextLine();
                if (patientName.matches(".*\\d.*")) {
                    throw new InvalidAlphabetsException("Try again!! In patient name not include any number...");
                }
                else {
                    while (true) {
                       try {
                           System.out.print("Enter patient age: ");
                           if (input.hasNextInt()) {
                               patientAge = input.nextInt();
                               input.nextLine();
                               while (true) {
                                   try {
                                       System.out.print("Enter patient gender: ");
                                       patientGender = input.nextLine();
                                       if (patientGender.matches(".*\\d.*")) {
                                           throw new InvalidAlphabetsException("Try again! Not be include number only character...");
                                       } else {
                                           break;
                                       }
                                   } catch (InvalidAlphabetsException e) {
                                       System.out.println(e.getMessage());
                                       input.next();
                                   }
                               }
                               break;
                           } else {
                               System.out.println("Invalid patient age!! Must be number...");
                               input.next(); // clear wrong input
                           }
                       } catch (Exception e) {
                           System.out.println("Invalid patient age!! Must be number...");
                           input.next();
                       }
                    }
                }
                break;
            } catch (InvalidAlphabetsException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "INSERT INTO patients(patient_name, patient_age, gender)VALUES(?, ?, ?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, patientName);
            ps.setInt(2, patientAge);
            ps.setString(3, patientGender);
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Patient Inserted Successfully!");
            } else {
                System.out.println("Patient Inserted Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewParticularPatients() {
        int patientId;
        String patientName;

        while (true) {
            System.out.print("Enter patient id: ");
            if (input.hasNextInt()) {
                patientId = input.nextInt();
                input.nextLine();
                while (true) {
                    try {
                        System.out.print("Enter patient name: ");
                        patientName = input.nextLine();
                        if (patientName.matches(".*\\d.*")) {
                            throw new InvalidAlphabetsException("Invalid patient name! Not any number included...");
                        }
                        break;
                    } catch (InvalidAlphabetsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            } else {
                System.out.println("Invalid patient id! Must be a number...");
                input.next();
            }
        }

        String sqlQuery = "SELECT * FROM patients WHERE patient_id = ? and patient_name = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, patientId);
            ps.setString(2, patientName);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+--------------|--------------------------|----------------|------------------|----------------------+");
            System.out.println("| Patient ID   | Patient name             | Patient Age    | Gender           | Date Time            |");
            System.out.println("|--------------|--------------------------|----------------|------------------|----------------------|");
            while (resultSet.next()) {
                int p_Id = resultSet.getInt("patient_id");
                String p_Name = resultSet.getString("patient_name");
                int p_Age = resultSet.getInt("patient_age");
                String p_Gender = resultSet.getString("gender");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12d | %-24s | %-14d | %-16s | %-20s |\n", p_Id, p_Name, p_Age, p_Gender, date_time);
                System.out.println("|--------------|--------------------------|----------------|------------------|----------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPatients() {
        String sqlQuery = "SELECT * FROM patients;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Patients: ");
            System.out.println("+--------------|--------------------------|----------------|------------------|----------------------+");
            System.out.println("| Patient ID   | Patient name             | Patient Age    | Gender           | Date Time            |");
            System.out.println("|--------------|--------------------------|----------------|------------------|----------------------|");
            while (resultSet.next()) {
                int p_Id = resultSet.getInt("patient_id");
                String p_Name = resultSet.getString("patient_name");
                int p_Age = resultSet.getInt("patient_age");
                String p_Gender = resultSet.getString("gender");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12d | %-24s | %-14d | %-16s | %-20s |\n", p_Id, p_Name, p_Age, p_Gender, date_time);
                System.out.println("|--------------|--------------------------|----------------|------------------|----------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePatient() {
        int patientId;
        String patientName;

        while (true) {
            System.out.print("Enter patient Id: ");
            if (input.hasNextInt()) {
                patientId = input.nextInt();
                input.nextLine();
                while (true) {
                    try {
                        System.out.print("Enter patient name: ");
                        patientName = input.nextLine();
                        if (patientName.matches(".*\\d.*")) {
                            throw new InvalidAlphabetsException("Invalid patient name! Not any number included...");
                        }
                        break;
                    } catch (InvalidAlphabetsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            } else {
                System.out.println("Try again! Must be only number...");
                input.next();
            }
        }

        String sqlQuery = "DELETE FROM patients WHERE patient_id = ? AND patient_name = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setInt(1, patientId);
        ps.setString(2, patientName);
        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Patient Deleted Successfully!!");
        } else {
            System.out.println("Patient Deleted Failed!!");
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePatient() {
        int patientId;
        String updatePatientName;
        int updatePatientAge;
        String updatePatientGender;

        while (true) {
            System.out.print("Enter patient id: ");
            if (input.hasNextInt()) {
                patientId = input.nextInt();
                input.nextLine();
                break;
            } else {
                System.out.println("Try again! Must be number...");
                input.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter update patient name: ");
                updatePatientName = input.nextLine();
                if (updatePatientName.matches(".*\\d.*")) {
                    throw new IncludingNumberException("Invalid update patient name! Try again...");
                } else {
                    while (true) {
                        System.out.print("Enter update patient age: ");
                        if (input.hasNextInt()) {
                            updatePatientAge = input.nextInt();
                            input.nextLine();
                            while (true) {
                                try {
                                    System.out.print("Enter update patient gender: ");
                                    updatePatientGender = input.nextLine();
                                    if (updatePatientGender.matches(".*\\d.*")) {
                                        throw new IncludingNumberException("Invalid update patient gender! Try again...");
                                    } else {
                                        break;
                                    }
                                } catch (IncludingNumberException e) {
                                    System.out.println(e.getMessage());
                                    input.next();
                                }
                            }
                            break;
                        } else {
                            System.out.println("Invalid update patient age! Try again...");
                            input.next();
                        }
                    }
                }
                break;
            } catch (IncludingNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "UPDATE patients SET patient_name = ?, patient_age = ?, gender = ? WHERE patient_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, updatePatientName);
            ps.setInt(2, updatePatientAge);
            ps.setString(3, updatePatientGender);
            ps.setInt(4, patientId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Updates Successfully!!");
            } else {
                System.out.println("Patient Updates Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

    public boolean getPatientById(int patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = " + patientId;

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
