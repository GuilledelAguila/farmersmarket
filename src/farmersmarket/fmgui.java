package farmersmarket;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

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
	public int[] runStartWindow(Connection conn) throws SQLException {

		Buyer b = new Buyer();
		Seller s = new Seller();
		Farmer f = new Farmer();

		String buyerQuery = "SELECT bid FROM buyer";
		String sellerQuery = "SELECT sid FROM seller";
		String farmerQuery = "SELECT fid FROM farm";

		int result[] = { 0, 0 };

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

		// Dropdown for ID
		Vector<Integer> ids = new Vector<Integer>();


		// Waits for role input to display possible ID's as a dropdown
		// Locks the combobox to select a role after first selection
		// Opens corresponding window on second selection
		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String role = cb.getSelectedItem().toString();

				switch(role) {
				case "BUYER": 
					try(PreparedStatement ps = conn.prepareStatement(buyerQuery);
							ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							ids.add(rs.getInt("bid"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}

					// make the combobox
					JComboBox<Integer> cb1 = new JComboBox<Integer>(ids);
					cb1.setMaximumSize(cb1.getPreferredSize());
					cb1.setAlignmentX(Component.CENTER_ALIGNMENT);
					panel.add(cb1, 4);

					cb.setEnabled(false);

					cb1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							String userString = cb.getSelectedItem().toString();

							switch(userString) {
							case "BUYER": result[0] = 1; break;
							case "SELLER": result[0] = 2; break;
							case "FARMER": result[0] = 3; break;
							default: break;
							}

							result[1] = (int)cb1.getSelectedItem();

							cb1.setEnabled(false);

							// switch for each window based on role: BUYER, SELLER, or FARMER
							switch(result[0]) {
							case 1: try {
								login.dispose();
								b.run(conn, result[1]);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} break;
							case 2: try {
								s.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} break;
							case 3: try {
								f.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} break;
							default: break;
							}
						}
					});

					login.add(panel);

					panel.revalidate();
					panel.repaint();


					break;
				case "SELLER": 
					try(PreparedStatement ps1 = conn.prepareStatement(sellerQuery);
							ResultSet rs1 = ps1.executeQuery()) {
						while(rs1.next()) {
							ids.add(rs1.getInt("sid"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}

					// make the combobox
					cb1 = new JComboBox<Integer>(ids);
					cb1.setMaximumSize(cb1.getPreferredSize());
					cb1.setAlignmentX(Component.CENTER_ALIGNMENT);
					panel.add(cb1, 4);

					cb.setEnabled(false);

					cb1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							String userString = cb.getSelectedItem().toString();

							switch(userString) {
							case "BUYER": result[0] = 1; break;
							case "SELLER": result[0] = 2; break;
							case "FARMER": result[0] = 3; break;
							default: break;
							}

							result[1] = (int)cb1.getSelectedItem();

							cb1.setEnabled(false);

							// switch for each window based on role: BUYER, SELLER, or FARMER
							switch(result[0]) {
							case 1: try {
								b.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} break;
							case 2: try {
								s.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} break;
							case 3: try {
								f.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} break;
							default: break;
							}
						}
					});

					login.add(panel);

					panel.revalidate();
					panel.repaint();

					break;
				case "FARMER": 
					try(PreparedStatement ps2 = conn.prepareStatement(farmerQuery);
							ResultSet rs2 = ps2.executeQuery()) {
						while(rs2.next()) {
							ids.add(rs2.getInt("fid"));
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}

					// make the combobox
					cb1 = new JComboBox<Integer>(ids);
					cb1.setMaximumSize(cb1.getPreferredSize());
					cb1.setAlignmentX(Component.CENTER_ALIGNMENT);
					panel.add(cb1, 4);

					cb.setEnabled(false);

					cb1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							String userString = cb.getSelectedItem().toString();

							switch(userString) {
							case "BUYER": result[0] = 1; break;
							case "SELLER": result[0] = 2; break;
							case "FARMER": result[0] = 3; break;
							default: break;
							}

							result[1] = (int)cb1.getSelectedItem();

							cb1.setEnabled(false);

							// switch for each window based on role: BUYER, SELLER, or FARMER
							switch(result[0]) {
							case 1: try {
								b.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} break;
							case 2: try {
								s.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} break;
							case 3: try {
								f.run(conn, result[1]);
								login.dispose();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} break;
							default: break;
							}
						}
					});

					panel.revalidate();
					panel.repaint();


					break;
				default: break;
				}
			}
		});

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

		// run login
		//display.runStartWindow(conn);
		
		// for testing farmer
		Farmer f = new Farmer();
		f.run(conn, 4);

	}
}