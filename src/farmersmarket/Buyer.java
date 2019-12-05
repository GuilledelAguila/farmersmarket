package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Buyer {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	GetTable modelTable;
	JTable mainTable = new JTable();
	JFrame buyer = new JFrame("buyer");
	JPanel buyer_panel = new JPanel();
	JPanel buyer_panel_2 = new JPanel();
	String choices[] = {
			"hallo",
			"Bonjour",
			"holaa"
			
	};
	
	JComboBox cb = new JComboBox(choices);
	
	public void getPostings(JTable table, GetTable modelTable, String farm, String seller, 
			String product, int price_lower, int price_upper, Connection conn) {
		
		try {
			CallableStatement callGetPostings = conn.prepareCall("{call search_post(NULL, NULL ,NULL,NULL, NULL)}");
			try {
				ResultSet s = callGetPostings.executeQuery();
				table.setModel(modelTable.buildTableModel(s));
			} finally {
				callGetPostings.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void item_bought(JTable table, GetTable modelTable, int buyer_id, int posting_id, Connection conn) {
		
		try {
			CallableStatement callItemBought = conn.prepareCall("{call item_bought(?, ?)}");
			callItemBought.setInt(1, buyer_id);
			callItemBought.setInt(2, posting_id);
			callItemBought.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void run(Connection conn, int id) throws SQLException {

		
		buyer.setLayout(new BoxLayout(buyer.getContentPane(), BoxLayout.Y_AXIS));
		buyer.setSize((int)screenWidth / 3, (int)screenHeight -500);
		buyer.setLocation(0, 0);
		
		buyer_panel.setLayout(new BoxLayout(buyer.getContentPane(), BoxLayout.Y_AXIS));
		buyer_panel_2.setLayout(new BoxLayout(buyer.getContentPane(), BoxLayout.Y_AXIS));
		modelTable = new GetTable(conn);

		// Makes posting table in the buyer window 


		
		getPostings(mainTable, modelTable,  "", "", "", -1, -1, conn);

		JScrollPane mainTableContainer = new JScrollPane(mainTable);
		mainTable.setFillsViewportHeight(true);

		buyer_panel.add(new JLabel("Postings"),BorderLayout.PAGE_START);
		
		buyer_panel.add(mainTableContainer,BorderLayout.CENTER);
		buyer.getContentPane().add(buyer_panel, BorderLayout.CENTER);
		
		buyer_panel_2.add(cb);
		buyer.getContentPane().add(buyer_panel_2);
		


		buyer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// allows for selection of row from a table
		mainTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {

				if (event.getValueIsAdjusting()){
					if (mainTable.getSelectedRow() != -1) {
						int selected_product_id = (int) mainTable.getValueAt(mainTable.getSelectedRow(), 0);
						item_bought(mainTable, modelTable, id, selected_product_id, conn);
						getPostings(mainTable, modelTable,  "", "", "", -1, -1, conn);
					}
				}
			}
		});

		buyer.setVisible(true);

	}
}
