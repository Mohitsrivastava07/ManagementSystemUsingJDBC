import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HotelManagementSystemApp {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "";

    private JFrame frame;
    private JTextArea displayArea;

    public HotelManagementSystemApp() {
        frame = new JFrame("Hotel Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel (Buttons)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));

        JButton reserveBtn = new JButton("Reserve Room");
        JButton viewBtn = new JButton("View Reservations");
        JButton getRoomBtn = new JButton("Get Room Number");
        JButton updateBtn = new JButton("Update Reservation");
        JButton deleteBtn = new JButton("Delete Reservation");
        JButton exitBtn = new JButton("Exit");

        panel.add(reserveBtn);
        panel.add(viewBtn);
        panel.add(getRoomBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(exitBtn);

        // Text Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        reserveBtn.addActionListener(e -> reserveRoom());
        viewBtn.addActionListener(e -> viewReservations());
        getRoomBtn.addActionListener(e -> getRoomNumber());
        updateBtn.addActionListener(e -> updateReservation());
        deleteBtn.addActionListener(e -> deleteReservation());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private void reserveRoom() {
        JTextField nameField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField contactField = new JTextField();

        Object[] message = {
                "Guest Name:", nameField,
                "Room Number:", roomField,
                "Contact:", contactField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Reserve Room", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection con = connect()) {
                String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(roomField.getText()));
                ps.setString(3, contactField.getText());

                ps.executeUpdate();
                displayArea.setText("Reservation Added Successfully!");
            } catch (Exception ex) {
                displayArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private void viewReservations() {
        try (Connection con = connect()) {
            String sql = "SELECT * FROM reservations";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            sb.append("Reservations:\n\n");

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("reservation_id"))
                        .append(", Name: ").append(rs.getString("guest_name"))
                        .append(", Room: ").append(rs.getInt("room_number"))
                        .append(", Contact: ").append(rs.getString("contact_number"))
                        .append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    private void getRoomNumber() {
        String id = JOptionPane.showInputDialog("Enter Reservation ID:");
        String name = JOptionPane.showInputDialog("Enter Guest Name:");

        try (Connection con = connect()) {
            String sql = "SELECT room_number FROM reservations WHERE reservation_id=? AND guest_name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                displayArea.setText("Room Number: " + rs.getInt("room_number"));
            } else {
                displayArea.setText("Reservation Not Found!");
            }

        } catch (Exception e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateReservation() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField contactField = new JTextField();

        Object[] message = {
                "Reservation ID:", idField,
                "New Name:", nameField,
                "New Room:", roomField,
                "New Contact:", contactField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Reservation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection con = connect()) {
                String sql = "UPDATE reservations SET guest_name=?, room_number=?, contact_number=? WHERE reservation_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(roomField.getText()));
                ps.setString(3, contactField.getText());
                ps.setInt(4, Integer.parseInt(idField.getText()));

                ps.executeUpdate();
                displayArea.setText("Reservation Updated!");
            } catch (Exception e) {
                displayArea.setText("Error: " + e.getMessage());
            }
        }
    }

    private void deleteReservation() {
        String id = JOptionPane.showInputDialog("Enter Reservation ID:");

        try (Connection con = connect()) {
            String sql = "DELETE FROM reservations WHERE reservation_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));

            ps.executeUpdate();
            displayArea.setText("Reservation Deleted!");
        } catch (Exception e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new HotelManagementSystemApp();
    }
}