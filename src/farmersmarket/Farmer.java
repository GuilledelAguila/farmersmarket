package farmersmarket;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

		// See Old Produce Button. All produce from farmer that has been put in a posting
		JButton seeProduceButton = new JButton("Product History"); 
		farmer.add(seeProduceButton);

		// See Your Reviews
		JButton reveiwsButton = new JButton("Your Reviews");
		farmer.add(reveiwsButton);

		// ActionListeners for buttons

		// reviews button
		reveiwsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// remove anything in the main panel for the reviews to be displayed in
				panel.removeAll();
				panel.revalidate();
				panel.repaint();

				// show reviews


				ArrayList<String> reviewsText = new ArrayList<String>();
				ArrayList<String> whoSaid = new ArrayList<String>();

				try {
					PreparedStatement ps = conn.prepareStatement("{call get_reviews(?)}");
					ps.setInt(1, id);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						reviewsText.add(rs.getString("review"));
						whoSaid.add(rs.getString("reviewer"));
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}

				String allReviews = "";

				for (int i = 0; i < reviewsText.size(); ++ i) {
					allReviews += reviewsText.get(i) + "- " + whoSaid.get(i) + "\n";
				}


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

		// see produce button
		seeProduceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// remove anything in the main panel for the postings to be displayed in
				panel.removeAll();
				panel.revalidate();
				panel.repaint();

				GetTable farms = new GetTable(conn);
				JTable farmTable = new JTable();

				try {
					CallableStatement callFarmsPosting = conn.prepareCall("{call farms_postings(?)}");
					callFarmsPosting.setInt(1, id);

					ResultSet s = callFarmsPosting.executeQuery();
					farmTable.setModel(farms.buildTableModel(s));

				} catch (SQLException err) {
					// TODO Auto-generated catch block
					err.printStackTrace();
				}

				farmTable.setFillsViewportHeight(true);

				panel.add(farmTable);
				farmer.add(panel);
				panel.revalidate();
				panel.repaint();
			}

		});


		// add produce button
		addProduceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// remove anything in the main panel for the postings to be displayed in
				panel.removeAll();
				panel.revalidate();
				panel.repaint();

				// Text Entries for Produce
				JLabel quantityPrompt = new JLabel("Enter Quantity of Produce:");
				quantityPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
				JTextField quantityTF = new JTextField();
				quantityTF.setSize((int)screenWidth / 3 - 200, (int)screenHeight / 3 - 200);

				JLabel cidPrompt = new JLabel("Enter Catalog ID of Produce:");
				cidPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
				JTextField cidTF = new JTextField(); 
				cidTF.setSize((int)screenWidth / 3 - 200, (int)screenHeight / 3 - 200);

				// Add Enter Button
				JButton enter = new JButton("Enter");

				enter.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

						try {
							int quantity = Integer.parseInt(quantityTF.getText());
							int cid = Integer.parseInt(cidTF.getText());

							CallableStatement callAddProduce = conn.prepareCall("{call add_produce(?, ?, ?)}");

							// procedure to add the produce 
							try {
								callAddProduce.setInt(1, quantity);
								callAddProduce.setInt(2, cid);
								callAddProduce.setInt(3, id);
								callAddProduce.execute();
							} catch (SQLException e1) {
								JLabel error = new JLabel("Invalid Data Entered Try Again");
								quantityTF.setText(""); 
								cidTF.setText(""); 

								panel.add(error);
								farmer.add(panel);
								panel.revalidate();
								panel.repaint();
							}

							quantityTF.setText(""); 
							cidTF.setText(""); 
						} catch (NumberFormatException | SQLException n) {
							JLabel error = new JLabel("Invalid Data Entered Try Again");
							quantityTF.setText(""); 
							cidTF.setText(""); 

							panel.add(error);
							farmer.add(panel);
							panel.revalidate();
							panel.repaint();
						}



					}

				});



				panel.add(quantityPrompt);
				panel.add(quantityTF);
				panel.add(cidPrompt);
				panel.add(cidTF);
				panel.add(enter);
				farmer.add(panel);
				panel.revalidate();
				panel.repaint();
			}

		});

		farmer.setVisible(true);

	}
}
