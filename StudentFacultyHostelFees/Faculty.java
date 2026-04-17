package StudentFacultyHostelFees;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;

public class Faculty {
    private Connection connection;
    private Scanner input;

    public Faculty(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void addFaculty() {
        String kid;
        String facultyName;
        String emailId;
        String phoneNumber;
        String address;
        String roomNumber;
        String whichMonth;
        String collectedBy;

        while (true) {
            try {
                System.out.print("Enter faculty kid: ");
                kid = input.nextLine();
                if (!kid.matches("F\\d{5}")) {
                    throw new IncludingOtherAlphabetsWithFiveDigitsPlaceException("Not Started faculty kid with F and followed by 5 digits number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter faculty name: ");
                            facultyName = input.nextLine();
                            if (!facultyName.matches("[a-zA-Z ]+")) {
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
                                                    System.out.print("Enter faculty phone number: ");
                                                    phoneNumber = input.nextLine();
                                                    if (!phoneNumber.matches("\\d{10}")) {
                                                        throw new InvalidPhoneNumberException("Phone number must be exactly 10 digits...");
                                                    } else {
                                                        while (true) {
                                                            try {
                                                                System.out.print("Enter faculty address: ");
                                                                address = input.nextLine();
                                                                if (address.matches(".*\\d.*")) {
                                                                    throw new IncludingNumberException("Must be contained alphabets NOT number...");
                                                                } else {
                                                                    while (true) {
                                                                        try {
                                                                            System.out.print("Enter faculty room number: ");
                                                                            roomNumber = input.nextLine();
                                                                            if (!(roomNumber.matches("A\\d{3}") || roomNumber.matches("B\\d{3}"))) {
                                                                                throw new IncludingOtherAlphabetsWithThreeDigitsPlaceException("Not started Faculty roo number with A, B and followed by 3 digits number...");
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

        String sqlQuery = "INSERT INTO facultyhostelfees(kid, faculty_name, email_id, phone_number, address, room_number, which_month, collected_by)VALUES" +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, facultyName);
            preparedStatement.setString(3, emailId);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, roomNumber);
            preparedStatement.setString(7, whichMonth);
            preparedStatement.setString(8, collectedBy);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Data Inserted Successfully!");
            } else {
                System.out.println("Faculty Data Inserted Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAllFaculty() {
        String sqlQuery = "SELECT * FROM facultyhostelfees";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Faculties: ");
            System.out.println("+--------------|---------------------------|---------------------------------|----------------------|----------------------------+-----------------------|-------------------------|------------------------+");
            System.out.println("| Kid          | Faculty Name              | Email ID                        | Phone Number         | Address                    | Which Month           | Collected By            | Date Time              |");
            System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|-----------------------|-------------------------|------------------------|");
            while (resultSet.next()) {
                String kid = resultSet.getString("kid");
                String facultyName = resultSet.getString("faculty_name");
                String emailId = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String whichMonth = resultSet.getString("which_month");
                String collectedBy = resultSet.getString("collected_by");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12s | %-25s | %-31s | %-20s | %-26s | %-21s | %-23s | %-22s |\n", kid, facultyName, emailId, phoneNumber, address, whichMonth, collectedBy, date_time);
                System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|-----------------------|-------------------------|------------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewParticularFaculty() {
        String kid;
        String facultyName;

        while (true) {
            try {
                System.out.print("Enter faculty kid: ");
                kid = input.nextLine();
                if (!kid.matches("F\\d{5}")) {
                    throw new IncludingNumberException("Faculty kid started with 'F' and followed by 5 digits place number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter faculty name: ");
                            facultyName = input.nextLine();
                            if (facultyName.matches(".*\\d.*")) {
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

        String sqlQuery = "SELECT * FROM facultyhostelfees WHERE kid = ? AND faculty_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, facultyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Students: ");
            System.out.println("+--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------+");
            System.out.println("| Kid          | Faculty Name              | Email ID                        | Phone Number         | Address                    | Room Number   | Which Month           | Collected By            | Date Time              |");
            System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------|");
            while (resultSet.next()) {
                String stdKid = resultSet.getString("kid");
                String fltName = resultSet.getString("faculty_name");
                String emailId = resultSet.getString("email_id");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String roomNumber = resultSet.getString("room_number");
                String whichMonth = resultSet.getString("which_month");
                String collectedBy = resultSet.getString("collected_by");
                String date_time = resultSet.getString("date_time");

                System.out.printf("| %-12s | %-25s | %-31s | %-20s | %-26s | %-13s | %-21s | %-23s | %-22s |\n", stdKid, fltName, emailId, phoneNumber, address, roomNumber, whichMonth, collectedBy, date_time);
                System.out.println("|--------------|---------------------------|---------------------------------|----------------------|----------------------------|---------------|-----------------------|-------------------------|------------------------|");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateFaculty() {
        String kid;
        String updateFacultyName;
        String updateEmailId;
        String updatePhoneNumber;
        String updateAddress;
        String updateRoomNumber;
        String updateWhichMonth;
        String updateCollectedBy;

        while (true) {
            try {
                System.out.print("Enter faculty kid: ");
                kid = input.nextLine();
                if (!kid.matches("F\\d{5}")) {
                    throw new IncludingNumberException("Faculty kid started with 'F' and followed by 5 digits place number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter update faculty name: ");
                            updateFacultyName = input.nextLine();
                            if (updateFacultyName.matches(".*\\d.*")) {
                                throw new IncludingNumberException("Invalid faculty name! NOT including number...");
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
                                                                                throw new IncludingOtherAlphabetsWithThreeDigitsPlaceException("Not started faculty room number with A, B and followed by 3 digits number...");
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

        String sqlQuery = "UPDATES facultyhostelfees SET faculty_name = ?, email_id = ?, phone_number = ?, address = ?, room_number = ?, which_month = ?, collected_by = ? WHERE kid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, updateFacultyName);
            preparedStatement.setString(2, updateEmailId);
            preparedStatement.setString(3, updatePhoneNumber);
            preparedStatement.setString(4, updateAddress);
            preparedStatement.setString(5, updateRoomNumber);
            preparedStatement.setString(6, updateWhichMonth);
            preparedStatement.setString(7, updateCollectedBy);
            preparedStatement.setString(8, kid);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Update Successfully!");
            } else {
                System.out.println("Faculty Update Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFaculty() {
        String kid;
        String facultyName;

        while (true) {
            try {
                System.out.print("Enter faculty kid: ");
                kid = input.nextLine();
                if (!kid.matches("F\\d{5}")) {
                    throw new IncludingOtherAlphabetsWithFiveDigitsPlaceException("Faculty kid start with Capital 'F' with followed by 5 digits number...");
                } else {
                    while (true) {
                        try {
                            System.out.print("Enter faculty name: ");
                            facultyName = input.nextLine();
                            if (facultyName.matches(".*\\d.*")) {
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

        String sqlQuery = "DELETE FROM facultyhostelfees WHERE kid = ? AND faculty_name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, kid);
            preparedStatement.setString(2, facultyName);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Faculty Deleted Successfully!");
            } else {
                System.out.println("Faculty Deleted Successfully!");
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
