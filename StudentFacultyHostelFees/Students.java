package StudentFacultyHostelFees;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;

public class Students {
    private Connection connection;
    private Scanner input;

    public Students(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addStudents() {
        String kid;
        String studentName;
        String emailId;
        String phoneNumber;
        String address;
        String roomNumber;
        String whichMonth;
        String collectedBy;

        while (true) {
            try {
                System.out.print("Enter student kid: ");
                kid = input.nextLine();
                if (!kid.matches("K\\d{5}")) {
                    throw new IncludingOtherAlphabetsWithFiveDigitsPlaceException("Not Started student kid with K and followed by 5 digits number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter student name: ");
                            studentName = input.nextLine();
                            if (!studentName.matches("[a-zA-Z ]+")) {
                                throw new IncludingNumberException("Must be contain only alphabets NOT number and special Character...");
                            } else {
                                while (true) {
                                    try {
                                        System.out.print("Enter email id: ");
                                        emailId = input.nextLine();
                                        if (!emailId.endsWith("@gmail.com")) {
                                            throw new InvalidEmailIdException("Email id must be ends-with '@gmail.com' like (abc123@gmail.com)...");
                                        } else {
                                            while (true) {
                                                try {
                                                    System.out.print("Enter student phone number: ");
                                                    phoneNumber = input.nextLine();
                                                    if (!phoneNumber.matches("\\d{10}")) {
                                                        throw new InvalidPhoneNumberException("Phone number must be exactly 10 digits...");
                                                    } else {
                                                        while (true) {
                                                            try {
                                                                System.out.print("Enter student address: ");
                                                                address = input.nextLine();
                                                                if (address.matches(".*\\d.*")) {
                                                                    throw new IncludingNumberException("Must be contained alphabets NOT number...");
                                                                } else {
                                                                    while (true) {
                                                                       try {
                                                                           System.out.print("Enter student room number: ");
                                                                           roomNumber = input.nextLine();
                                                                           if (!(roomNumber.matches("A\\d{3}") || roomNumber.matches("B\\d{3}"))) {
                                                                               throw new IncludingOtherAlphabetsWithThreeDigitsPlaceException("Not started student roo number with A, B and followed by 3 digits number...");
                                                                           } else {
                                                                               while (true) {
                                                                                   try {
                                                                                       System.out.print("Enter month name of fees deposit: ");
                                                                                       whichMonth = input.nextLine();
                                                                                       if (whichMonth.matches(".*\\d.*")) {
                                                                                           throw new IncludingNumberException("Must be month name NOT number...");
                                                                                       } else {
                                                                                           while (true) {
                                                                                               try {
                                                                                                   System.out.print("Enter name of collected person of fees: ");
                                                                                                   collectedBy = input.nextLine();
                                                                                                   if (collectedBy.matches(".*\\d.*")) {
                                                                                                       throw new IncludingNumberException("Must be name of collected person NOT including any number...");
                                                                                                   } else {
                                                                                                       break;
                                                                                                   }
                                                                                               } catch (IncludingNumberException e) {
                                                                                                   System.out.println(e.getMessage());
                                                                                               }
                                                                                           }
                                                                                           break;
                                                                                       }
                                                                                   } catch (IncludingNumberException e) {
                                                                                       System.out.println(e.getMessage());
                                                                                   }
                                                                               }
                                                                               break;
                                                                           }
                                                                       } catch (IncludingOtherAlphabetsWithThreeDigitsPlaceException e) {
                                                                           System.out.println(e.getMessage());
                                                                       }
                                                                    }
                                                                    break;
                                                                }
                                                            } catch (IncludingNumberException e) {
                                                                System.out.println(e.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    }
                                                } catch (InvalidPhoneNumberException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                            break;
                                        }
                                    } catch (InvalidEmailIdException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                        } catch (IncludingNumberException e) {
                        System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
            } catch (IncludingOtherAlphabetsWithFiveDigitsPlaceException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "INSERT INTO studentshostelfees(kid, student_name, email_id, phone_number, address, room_number, which_month, collected_by)VALUES" +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, studentName);
            preparedStatement.setString(3, emailId);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, roomNumber);
            preparedStatement.setString(7, whichMonth);
            preparedStatement.setString(8, collectedBy);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Data Inserted Successfully!");
            } else {
                System.out.println("Student Data Inserted Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAllstudents() {
        String sqlQuery = "SELECT * FROM studentshostelfees";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Students: ");
            System.out.println("+--------------|---------------------------|---------------------------------|----------------------|----------------------------+-----------------------|-------------------------|------------------------+");
            System.out.println("| Kid          | Student Name              | Email ID                        | Phone Number         | Address                    | Which Month           | Collected By            | Date Time              |");
            System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|-----------------------|-------------------------|------------------------|");
            while (resultSet.next()) {
                String kid = resultSet.getString("kid");
                String studentName = resultSet.getString("student_name");
                String emailId = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String whichMonth = resultSet.getString("which_month");
                String collectedBy = resultSet.getString("collected_by");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12s | %-25s | %-31s | %-20s | %-26s | %-21s | %-23s | %-22s |\n", kid, studentName, emailId, phoneNumber, address, whichMonth, collectedBy, date_time);
                System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewParticularStudent() {
        String kid;
        String studentName;

        while (true) {
            try {
                System.out.print("Enter student kid: ");
                kid = input.nextLine();
                if (!kid.matches("K\\d{5}")) {
                    throw new IncludingNumberException("Student kid started with 'K' and followed by 5 digits place number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter student name: ");
                            studentName = input.nextLine();
                            if (studentName.matches(".*\\d.*")) {
                                throw new IncludingNumberException("Must be character NOT number...");
                            } else {
                                break;
                            }
                        } catch (IncludingNumberException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
            } catch (IncludingNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "SELECT * FROM studentshostelfees WHERE kid = ? AND student_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Students: ");
            System.out.println("+--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------+");
            System.out.println("| Kid          | Student Name              | Email ID                        | Phone Number         | Address                    | Room Number   | Which Month           | Collected By            | Date Time              |");
            System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------|");
            while (resultSet.next()) {
                String stdKid = resultSet.getString("kid");
                String stdName = resultSet.getString("student_name");
                String emailId = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String whichMonth = resultSet.getString("which_month");
                String collectedBy = resultSet.getString("collected_by");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12s | %-25s | %-31s | %-20s | %-26s | %-13s | %-21s | %-23s | %-22s |\n", stdKid, stdName, emailId, phoneNumber, address, roomNumber, whichMonth, collectedBy, date_time);
                System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateStudents() {
        String kid;
        String updateStudentName;
        String updateEmailId;
        String updatePhoneNumber;
        String updateAddress;
        String updateRoomNumber;
        String updateWhichMonth;
        String updateCollectedBy;

        while (true) {
            try {
                System.out.print("Enter student kid: ");
                kid = input.nextLine();
                if (!kid.matches("K\\d{5}")) {
                    throw new IncludingNumberException("Student kid started with 'K' and followed by 5 digits place number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter update student name: ");
                            updateStudentName = input.nextLine();
                            if (updateStudentName.matches(".*\\d.*")) {
                                throw new IncludingNumberException("Invalid student name! NOT including number...");
                            } else {
                                while (true) {
                                    try {
                                        System.out.print("Enter update email id: ");
                                        updateEmailId = input.nextLine();
                                        if (!updateEmailId.endsWith("@gmail.com")) {
                                            throw new InvalidEmailIdException("Invalid Email Id! Email id input like 'abc123@gmail.com' in which '@gmail.com' is include...");
                                        } else {
                                            while (true) {
                                                try {
                                                    System.out.print("Enter update phone number: ");
                                                    updatePhoneNumber = input.nextLine();
                                                    if (!updatePhoneNumber.matches("\\d{10}")) {
                                                        throw new InvalidPhoneNumberException("Invalid phone number! Must be only 10 digits phone number...");
                                                    } else {
                                                        while (true) {
                                                            try {
                                                                System.out.print("Enter update address: ");
                                                                updateAddress = input.nextLine();
                                                                if (updateAddress.matches(".*\\d.*")) {
                                                                    throw new IncludingNumberException("Invalid update address! Address without number will be include...");
                                                                } else {
                                                                    while (true) {
                                                                        try {
                                                                            System.out.print("Enter update room number: ");
                                                                            updateRoomNumber = input.nextLine();
                                                                            if (!(updateRoomNumber.matches("A\\d{3}") || updateRoomNumber.matches("B\\d{3}"))) {
                                                                                throw new IncludingOtherAlphabetsWithThreeDigitsPlaceException("Not started student room number with A, B and followed by 3 digits number...");
                                                                            } else {
                                                                                while (true) {
                                                                                    try {
                                                                                        System.out.print("Enter month name of fees deposit: ");
                                                                                        updateWhichMonth = input.nextLine();
                                                                                        if (updateWhichMonth.matches(".*\\d.*")) {
                                                                                            throw new IncludingNumberException("Must be month name NOT number...");
                                                                                        } else {
                                                                                            while (true) {
                                                                                                try {
                                                                                                    System.out.print("Enter name of collected person of fees: ");
                                                                                                    updateCollectedBy = input.nextLine();
                                                                                                    if (updateCollectedBy.matches(".*\\d.*")) {
                                                                                                        throw new IncludingNumberException("Must be name of collected person NOT including any number...");
                                                                                                    } else {
                                                                                                        break;
                                                                                                    }
                                                                                                } catch (IncludingNumberException e) {
                                                                                                    System.out.println(e.getMessage());
                                                                                                }
                                                                                            }
                                                                                            break;
                                                                                        }
                                                                                    } catch (IncludingNumberException e) {
                                                                                        System.out.println(e.getMessage());
                                                                                    }
                                                                                }
                                                                                break;
                                                                            }
                                                                        } catch (IncludingOtherAlphabetsWithThreeDigitsPlaceException e) {
                                                                            System.out.println();
                                                                        }
                                                                    }
                                                                    break;
                                                                }
                                                            } catch (IncludingNumberException e) {
                                                                System.out.println(e.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    }
                                                } catch (InvalidPhoneNumberException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                            break;
                                        }
                                    } catch (InvalidEmailIdException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                        } catch (IncludingNumberException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
            } catch (IncludingNumberException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "UPDATES studentshostelfees SET student_name = ?, email_id = ?, phone_number = ?, address = ?, room_number = ?, which_month = ?, collected_by = ? WHERE kid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, updateStudentName);
            preparedStatement.setString(2, updateEmailId);
            preparedStatement.setString(3, updatePhoneNumber);
            preparedStatement.setString(4, updateAddress);
            preparedStatement.setString(5, updateRoomNumber);
            preparedStatement.setString(6, updateWhichMonth);
            preparedStatement.setString(7, updateCollectedBy);
            preparedStatement.setString(8, kid);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Update Successfully!");
            } else {
                System.out.println("Student Update Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStudents() {
        String kid;
        String studentName;

        while (true) {
            try {
                System.out.print("Enter student kid: ");
                kid = input.nextLine();
                if (!kid.matches("K\\d{5}")) {
                    throw new IncludingOtherAlphabetsWithFiveDigitsPlaceException("Student kid start with Capital 'K' with followed by 5 digits number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter student name: ");
                            studentName = input.nextLine();
                            if (studentName.matches(".*\\d.*")) {
                                throw new IncludingNumberException("Must be only include charter NOT number...");
                            } else {
                                break;
                            }
                        } catch (IncludingNumberException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
            } catch (IncludingOtherAlphabetsWithFiveDigitsPlaceException e) {
                System.out.println(e.getMessage());
            }
        }

        String sqlQuery = "DELETE FROM studentshostelfees WHERE kid = ? AND student_name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, studentName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student Deleted Successfully!");
            } else {
                System.out.println("Student Deleted Successfully!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exit() {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            try {
                Thread.sleep(400);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
            i--;
        }
        System.out.println("Thank You for using Student Faculty Hostel Management System...");
    }
}
