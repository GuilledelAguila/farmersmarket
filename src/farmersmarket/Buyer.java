package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Buyer {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();


	public void run(Connection conn, int id) throws SQLException {

		JFrame buyer = new JFrame("buyer");

		buyer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buyer.setLayout(new BorderLayout());

		buyer.setSize((int)screenWidth / 3, (int)screenHeight -500);

		buyer.setLocation(0, 0);

		// Makes posting table in the buyer window 

		GetTable posting = new GetTable(conn);

		JTable postingTable = posting.runTable("SELECT * FROM (SELECT postingid, CONCAT(seller.first_name, \" \", seller.last_name) AS seller_name, \n" + 
				"A.produce_name, courier.courier_type, cost, date_posted FROM posting\n" + 
				"            JOIN seller ON posting.sid = seller.sid\n" + 
				"            JOIN (SELECT pid, produce_name FROM produce \n" + 
				"            JOIN catalog ON produce.cid = catalog.cid) AS A\n" + 
				"      ON posting.pid = A.pid\n" + 
				"            JOIN courier ON posting.courid = courier.courid \n" + 
				"            ORDER BY date_posted) AS B\n" + 
				"            WHERE postingid NOT IN (SELECT DISTINCT postingid FROM buyer_to_posting);");

		JScrollPane pScrollPane = new JScrollPane(postingTable);
		postingTable.setFillsViewportHeight(true);

		JLabel pLblHeading = new JLabel("Postings");

		buyer.getContentPane().setLayout(new BorderLayout());

		buyer.getContentPane().add(pLblHeading,BorderLayout.PAGE_START);
		buyer.getContentPane().add(pScrollPane,BorderLayout.CENTER);

		buyer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// allows for selection of row from a table
		postingTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {

				if  (event.getValueIsAdjusting() ){

					// need to add something that will trigger updates either here or SQL

					try {
						System.out.println("double trouble");
						CallableStatement callItemBought = conn.prepareCall("{call item_bought(?, ?)}");
						if (postingTable.getSelectedRow() != -1) {
							int dpid = (int) postingTable.getValueAt(postingTable.getSelectedRow(), 0);
							callItemBought.setInt(1, id);
							callItemBought.setInt(2, dpid);
							callItemBought.execute();

							String q = "SELECT * FROM (SELECT postingid, CONCAT(seller.first_name, \" \", seller.last_name) AS seller_name, \n" + 
									"A.produce_name, courier.courier_type, cost, date_posted FROM posting\n" + 
									"            JOIN seller ON posting.sid = seller.sid\n" + 
									"            JOIN (SELECT pid, produce_name FROM produce \n" + 
									"            JOIN catalog ON produce.cid = catalog.cid) AS A\n" + 
									"      ON posting.pid = A.pid\n" + 
									"            JOIN courier ON posting.courid = courier.courid \n" + 
									"            ORDER BY date_posted) AS B\n" + 
									"            WHERE postingid NOT IN (SELECT DISTINCT postingid FROM buyer_to_posting);";

							PreparedStatement pst = conn.prepareStatement(q);
							try {
								ResultSet s = pst.executeQuery(q);
								postingTable.setModel(posting.buildTableModel(s));
							} finally {
								pst.close();
							}
						}



					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

				}
			}
		});

		buyer.setVisible(true);

	}
}
