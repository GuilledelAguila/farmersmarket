package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class Farmer {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	public void run(Connection conn, int id) throws SQLException {

		// set dimnesions of Farmer frame
		JFrame farmer = new JFrame("Farmer");
		farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		farmer.setSize((int)screenWidth / 3, (int)screenHeight / 3);
		farmer.setLocation((int)screenWidth / 2 - ((int)screenWidth / 3) / 2, 0);

		// Farmer Panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Flowlout of Options
		FlowLayout buttons = new FlowLayout();
		farmer.setLayout(buttons);

		// Add Produce Button
		JButton addProduceButton = new JButton("Add Produce"); 
		farmer.add(addProduceButton);

		// See Old Produce Button. Denoted by bold if sold
		JButton seeProduceButton = new JButton("Product History"); 
		farmer.add(seeProduceButton);

		// See Your Reviews
		JButton reveiwsButton = new JButton("Your Reviews");
		farmer.add(reveiwsButton);

		// ActionListeners for buttons
		reveiwsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String reviewQuery = "SELECT review, bid FROM review WHERE fid = " + id + "";
				
				ArrayList<String> reviewsText = new ArrayList<String>();
				// ArrayList<Integer> whoSaidIds = new ArrayList<Integer>();
				
				try(PreparedStatement ps = conn.prepareStatement(reviewQuery);
						ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						reviewsText.add(rs.getString("review"));
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
				
				String allReviews = "";
				
				for (String s : reviewsText) {
					allReviews += s + "\n";
				}
				
				// String whoSaidReview = "SELECT first_name, last_name FROM review WHERE " + whoSaid + " = bid";
				

				JTextArea reviews = new JTextArea(allReviews);
				reviews.setFont(new Font("Serif", Font.ITALIC, 16));
				reviews.setLineWrap(true);
				reviews.setWrapStyleWord(true);
				reviews.setEditable(false);
				reviews.setAlignmentX(Component.CENTER_ALIGNMENT);
				reviews.setSize((int)screenWidth / 3 - 200, (int)screenHeight / 3 - 200);

				panel.add(reviews);
				
				farmer.add(panel);

				panel.revalidate();
				panel.repaint();
			}

		});




		// All farm data
		/*
		GetTable farms = new GetTable(conn);
		JTable farmTable = farms.runTable("select * from farm");

        JScrollPane scrollPane = new JScrollPane(farmTable);
        farmTable.setFillsViewportHeight(true);

        JLabel lblHeading = new JLabel("Farms");

        farmer.getContentPane().setLayout(new BorderLayout());

        farmer.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        farmer.getContentPane().add(scrollPane,BorderLayout.CENTER);
		 */



		farmer.setVisible(true);

	}
}
