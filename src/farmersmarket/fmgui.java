package farmersmarket;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class fmgui extends JFrame {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	// set in the start window
	// 1 - buyer
	// 2 - seller
	// 3 - farmer
	int userType = 0;

	//prompt user for ID
	// must be a pre-existing user
	public int runStartWindow(Connection conn) throws SQLException {
		
		String buyerQuery = "SELECT bid FROM buyer";
		String sellerQuery = "SELECT sid FROM buyer";
		String farmerQuery = "SELECT fid FROM buyer";

		int result = 0;

		// Login Frame
		JFrame login = new JFrame("Login");
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize((int)screenWidth / 3, (int)screenHeight / 3);
		login.setLocation((int)screenWidth / 2 - ((int)screenWidth / 3) / 2, 0);

		// Login Panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Set Title
		JLabel title = new JLabel("FARMER'S MARKET");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("Serif", Font.BOLD, (int)screenWidth / 60));
		panel.add(title);

		// Ask for role
		JLabel prompt = new JLabel("Select your role: ");
		prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(prompt);

		// Dropdown for roles
		String[] roles = { "BUYER", "SELLER", "FARMER" };
		final JComboBox<String> cb = new JComboBox<String>(roles);
		cb.setMaximumSize(cb.getPreferredSize());
		cb.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(cb);

		// Ask for ID
		JLabel enterID = new JLabel("Enter your ID: ");
		enterID.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(enterID);
		
		
		String role = cb.getSelectedItem().toString();
		System.out.println(role);
		
		// Dropdown for ID
		int ids[];
		
		switch(role) {
		case "BUYER": break;
		case "SELLER": break;
		case "FARMER": break;
		default: break;
		}
		

		// Enter button
		JButton btn = new JButton("Enter");
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btn);

		// Add the panel
		login.add(panel);
		login.setVisible(true);

		return result;
	}



	public static void main(String[] args)  throws Exception {
		fmgui display = new fmgui();
		JavaMySql javasql = new JavaMySql();
		// set the connection to Database
		Connection conn = javasql.getConnection();
		Buyer b = new Buyer();
		Seller s = new Seller();
		Farmer f = new Farmer();

		// gets the id
		int id  = display.runStartWindow(conn);


		// switch for each window based on role: BUYER, SELLER, or FARMER
		switch(id) {
		case 1: b.run(conn, id); break;
		case 2: s.run(conn, id); break;
		case 3: f.run(conn, id); break;
		default: break;
		}

	}
}