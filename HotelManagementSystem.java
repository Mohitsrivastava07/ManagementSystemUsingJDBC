import java.util.IllegalFormatConversionException;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class HotelManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner input = new Scanner(System.in);
            while(true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exist");

                System.out.print("Enter any option (0 to 5): ");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, input);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, input);
                        break;
                    case 4:
                        updateReservation(connection, input);
                        break;
                    case 5:
                        deleteReservation(connection, input);
                        break;
                    case 0:
                        exit();
                        input.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again!");
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void reserveRoom(Connection connection, Scanner input) {
        input.nextLine();
        System.out.print("Enter guest name: ");
        String guestName = input.nextLine();
        System.out.print("Enter room number: ");
        int roomNumber = input.nextInt();
        input.nextLine();
        System.out.print("Enter Contact number: ");
        String contactNumber = input.nextLine();

        String sql = "INSERT INTO reservations(guest_name, room_number, contact_number) " +
                "VALUES('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation Successfully!!");
            }else {
                System.out.println("Reservation Failed!!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void viewReservation(Connection connection) throws IllegalFormatConversionException {
        String sql = "SELECT * from reservations;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Current Reservation");
            System.out.println("+----------------|-----------------------|----------------|--------------------|-------------------------+");
            System.out.println("| Reservation ID | Guest Name            | Room Number    | Contact Number     | Reservation date-time    |");
            System.out.println("|----------------|-----------------------|----------------|--------------------|-------------------------|");
            while(resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getString("reservation_dates").toString();

                System.out.printf("| %-14d | %-21s | %-14d | %-18s | %-21s  |\n", reservationId, guestName, roomNumber, contactNumber, reservationDate);
                System.out.println("|----------------|-----------------------|----------------|--------------------|-------------------------|");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void getRoomNumber(Connection connection, Scanner input) {
        System.out.print("Enter your reservation Id: ");
        int reservationId = input.nextInt();
        input.nextLine();
        System.out.print("Enter your guest name: ");
        String guestName = input.nextLine();

        String sql = "SELECT room_number FROM reservations WHERE reservation_id = "
                + reservationId + " AND guest_name = '" + guestName + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room Number for reservation Id " + reservationId + " and guest name " + guestName + " is " + roomNumber);
            }else {
                System.out.println("Reservation Not Found! for given Id and name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateReservation(Connection connection, Scanner input) {
        System.out.print("Enter your reservation Id to updates: ");
        int reservationId = input.nextInt();
        input.nextLine();

        System.out.print("Enter new guest name: ");
        String newGuestName = input.nextLine();
        System.out.print("Enter new room number: ");
        int newRoomNumber = input.nextInt();
        input.nextLine();
        System.out.print("Enter new contact number: ");
        String newcontactNumber = input.nextLine();

        String sql = "UPDATE reservations SET guest_name = '" + newGuestName +
                "', room_number = " + newRoomNumber +
                ", contact_number = '" + newcontactNumber +
                "' WHERE reservation_id = " + reservationId;

        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation updated Successfully!!");
            }else {
                System.out.println("Reservation Updated Failed");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void deleteReservation(Connection connection, Scanner input) {
        System.out.print("Enter your reservation Id: ");
        int reservationId = input.nextInt();

        String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation Deleted Successfully!!");
            }else {
                System.out.println("Reservation Deleted Failed!!");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean reservationExit(Connection connection, int reservationId) {
        String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void exit() throws InterruptedException {
        System.out.print("Existing System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.print("Thank You for using HOTEL MANAGEMENT SYSTEM");
        int j = 5;
        while (j != 0) {
            System.out.print("!");
            Thread.sleep(450);
            j--;
        }
    }
}
