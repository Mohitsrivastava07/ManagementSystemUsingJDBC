package StudentHostelFeesSystem;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.*;

public class Students {
    private Connection connection;
    private Scanner input;

    public Students(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }
    public void addStudents() throws AlphabetWithNumberException, EmailException, PhoneNumberException {
        String studentName;
        String studentKid;
        String studentEmail;
        String studentPhoneNumber;
        String which_month;
        String withdrawName;

        input.nextLine();
        while (true) {
            try {
                System.out.print("Enter student kid: ");
                studentKid = input.nextLine();
                if (!studentKid.matches("K\\d{5}$")) {
                    throw new AlphabetWithNumberException("Invalid KID! Must start with 'K' and then followed by 5 digits.");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter student name: ");
                studentName = input.nextLine();
                if (studentName.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter student email: ");
                studentEmail = input.nextLine();
                if (!studentEmail.endsWith("@gmail.com")) {
                    throw new EmailException("Invalid email! Email must be end with '@gmail.com' NOT another");
                }
                break;
            } catch (EmailException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
           try {
               System.out.print("Enter student phone number: ");
               studentPhoneNumber = input.nextLine();
               if (!(studentPhoneNumber.matches(".*[^0-9].*")) || (!(studentPhoneNumber.length() == 10)))  {
                   throw new PhoneNumberException("Invalid Phone Number! Only 10 digits number be contained NOT any character and special character");
               }
               break;
           } catch (PhoneNumberException e) {
               System.out.println(e.getMessage());
           }
        }
        while (true) {
            try {
                System.out.print("Enter student deposit fees of which month: ");
                which_month = input.nextLine();
                if (which_month.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter name of withdraw by: ");
                withdrawName = input.nextLine();
                if (withdrawName.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String query = "INSERT INTO studentfees (kid, name, email_id, phone_number, month, withdraw_by)" +
                "VALUES(?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentKid);
            preparedStatement.setString(2, studentName);
            preparedStatement.setString(3, studentEmail);
            preparedStatement.setString(4, studentPhoneNumber);
            preparedStatement.setString(5, which_month);
            preparedStatement.setString(6, withdrawName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Inserted Successfully!");
            } else {
                System.out.println("Student Inserted Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewAllStudents() {
        String query = "SELECT * FROM studentfees;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Students Details...");
            System.out.println("*........ ..|..........................|..............................|..................|................|........................|....................*");
            System.out.println("| KID       | Student Name             | Email ID                     | Phone Number     | Month          | Withdraw By            | Date Time          |");
            System.out.println("*...........|..........................|..............................|..................|................|........................|....................*");
            while (resultSet.next()) {
                String studentKid = resultSet.getString("kid");
                String studentName = resultSet.getString("name");
                String studentEmail = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String whichMonth = resultSet.getString("month");
                String withdrawName = resultSet.getString("withdraw_by");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-10s | %-24s | %-28s | %-16s | %-14s | %-22s | %-18s |\n", studentKid, studentName, studentEmail, phoneNumber, whichMonth, withdrawName, dateTime);
                System.out.println("*...........|..........................|..............................|..................|................|........................|....................*");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void viewParticularStudent() throws AlphabetWithNumberException {
        String studentKid;
        String studentName;

        input.nextLine();
        while (true) {
            try {
                System.out.print("Enter student kid: ");
                studentKid = input.nextLine();
                if (!studentKid.matches("K\\d{5}$")) {
                    throw new AlphabetWithNumberException("Invalid KID! Must start with 'K' and then followed by 5 digits.");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter student name: ");
                studentName = input.nextLine();
                if (studentName.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String query = "SELECT * FROM studentfees WHERE kid = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentKid);
            preparedStatement.setString(2, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Students Details...");
            System.out.println("*........ ..|..........................|..............................|..................|................|........................|....................*");
            System.out.println("| KID       | Student Name             | Email ID                     | Phone Number     | Month          | Withdraw By            | Date Time          |");
            System.out.println("*...........|..........................|..............................|..................|................|........................|....................*");
            while (resultSet.next()) {
                String stdKid = resultSet.getString("kid");
                String stdName = resultSet.getString("name");
                String studentEmail = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String whichMonth = resultSet.getString("month");
                String withdrawName = resultSet.getString("withdraw_by");
                String dateTime = resultSet.getString("date_time");

                System.out.printf("| %-10s | %-24s | %-28s | %-16s | %-14s | %-22s | %-18s |\n", stdKid, stdName, studentEmail, phoneNumber, whichMonth, withdrawName, dateTime);
                System.out.println("*...........|..........................|..............................|..................|................|........................|....................*");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateStudents() throws AlphabetWithNumberException, EmailException, PhoneNumberException {
        String studentKid;
        String newStudentName;
        String newStudentEmail;
        String newStudentPhoneNumber;
        String newWhich_month;
        String newWithdrawName;

        input.nextLine();
        while (true) {
            try {
                System.out.print("Enter student kid to update: ");
                studentKid = input.nextLine();
                if (!studentKid.matches("K\\d{5}$")) {
                    throw new AlphabetWithNumberException("Invalid KID! Must start with 'K' and then followed by 5 digits.");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter new student name: ");
                newStudentName = input.nextLine();
                if (newStudentName.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter new student email: ");
                newStudentEmail = input.nextLine();
                if (!newStudentEmail.endsWith("@gmail.com")) {
                    throw new EmailException("Invalid email! Email must be end with '@gmail.com' NOT another");
                }
                break;
            } catch (EmailException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter new student phone number: ");
                newStudentPhoneNumber = input.nextLine();
                if (!(newStudentPhoneNumber.matches(".*[^0-9].*")) || (!(newStudentPhoneNumber.length() == 10)))  {
                    throw new PhoneNumberException("Invalid Phone Number! Only 10 digits number be contained NOT any character and special character");
                }
                break;
            } catch (PhoneNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter new student deposit fees of which month: ");
                newWhich_month = input.nextLine();
                if (newWhich_month.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter new name of withdraw by: ");
                newWithdrawName = input.nextLine();
                if (newWithdrawName.matches(".*\\d.*")) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String query = "UPDATE studentfees SET name = ?, email_id = ?, phone_number = ?, month = ?, withdraw_by = ? WHERE kid = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newStudentName);
            preparedStatement.setString(2, newStudentEmail);
            preparedStatement.setString(3, newStudentPhoneNumber);
            preparedStatement.setString(4, newWhich_month);
            preparedStatement.setString(5, newWithdrawName);
            preparedStatement.setString(6, studentKid);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Updated Successfully!!");
            } else {
                System.out.println("Student Updated Failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteStudents() throws AlphabetWithNumberException {
        String studentKid;
        String studentName;

        input.nextLine();
        while (true) {
            try {
                System.out.print("Enter student kid: ");
                studentKid = input.nextLine();
                if (!studentKid.matches("K\\d{5}$")) {
                    throw new AlphabetWithNumberException("Invalid KID! Must start with 'K' and then followed by 5 digits.");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Enter student name: ");
                studentName = input.nextLine();
                if (!(studentName.matches(".*[^a-zA-Z].*"))) {
                    throw new AlphabetWithNumberException("Invalid Name! Must be in only String NOT contains any numeric and special character");
                }
                break;
            } catch (AlphabetWithNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String query = "DELETE FROM studentfees WHERE kid = ? AND name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,studentKid);
            preparedStatement.setString(2, studentName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Deleted Successfully!!");
            }else {
                System.out.println("Student Deleted Failed!!");
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
            Thread.sleep(450);
            i--;
        }
    }
}
