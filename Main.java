import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                int choice = 0;

                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM...");
                System.out.println("1. Reserve A Room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Exist");
                System.out.print("Choose any option(1 to 6): ");
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                }else {
                    System.out.println("Invalid Input!! Try Again.");
                }

                switch(choice) {
                    case 1: reserveRoom(connection, input); break;
                    case 2: viewReservation(connection); break;
                    case 3: getRoomNumber(connection, input); break;
                    case 4: updateReservation(connection, input); break;
                    case 5: deleteReservation(connection, input); break;
                    default: System.out.println("Invalid Choice!! Try Again.");
                }
            }
        }catch(SQLException e) {
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
        System.out.print("Enter contact number: ");
        String contactNumber = input.nextLine();

        String sql = "INSERT INTO reservations(guest_name, room_number, contact_number)" +
                "VALUES('"+ guestName +"', " + roomNumber +", '"+ contactNumber +"')";
        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation Insertion Successfully!!");
            }else {
                System.out.println("Reservation Insertion Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void viewReservation(Connection connection) {
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
            System.out.println(e.getMessage());
        }
    }
    private static void getRoomNumber(Connection connection, Scanner input) {
        System.out.print("Enter reservation Id: ");
        int reservationId = input.nextInt();
        input.nextLine();
        System.out.print("Enter guest name: ");
        String guestName = input.nextLine();

        String sql = "SELECT room_number from reservations WHERE reservation_id = " + reservationId +
                " AND guest_name = '" + guestName + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room Number for reservation Id: " + roomNumber + " and guest name is: " + guestName);
            }else {
                System.out.println("Reservation NOT found in given reservation Id and guest name");
                System.out.println("So Thank You!!");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void updateReservation(Connection connection, Scanner input) {
        System.out.print("Enter reservation Id to update: ");
        int reservationId = input.nextInt();
        input.nextLine();

        System.out.print("Enter new guest name: ");
        String newGuestName = input.nextLine();
        System.out.print("Enter new room number: ");
        int newRoomNumber = input.nextInt();
        input.nextLine();
        System.out.print("Enter new contact number: ");
        String newContactNumber = input.nextLine();

        String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', room_number = " + newRoomNumber +
                ", contact_number = '" + newContactNumber + "' WHERE reservation_id = " + reservationId;
        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation Updated Successfully!!");
            }else {
                System.out.println("Reservation Updated Failed!!");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void deleteReservation(Connection connection, Scanner input) {
        int reservationId = 0;

        System.out.print("Enter reservation Id: ");
        if (input.hasNextInt()) {
            reservationId = input.nextInt();
        }else {
            System.out.println("Reservation Id Invalid!!");
        }
        String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;
        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation Deletion Successfully!!");
            }else {
                System.out.println("Reservation Deletion Failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}