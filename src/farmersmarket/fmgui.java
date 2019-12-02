package farmersmarket;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class fmgui extends JFrame {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "farmersmarket";
	
	private int id_num = 1;
	
	
	public void run(Connection conn) {
		JFrame buyer = new JFrame("buyer");
		JFrame seller = new JFrame("seller");
		JFrame farmer = new JFrame("farmer");
		
		buyer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		seller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buyer.setLayout(new BorderLayout());
		seller.setLayout(new BorderLayout());
		farmer.setLayout(new BorderLayout());
		
		buyer.setSize((int)screenWidth / 3, (int)screenHeight -500);
		seller.setSize((int)screenWidth / 3, (int)screenHeight - 500);
		farmer.setSize((int)screenWidth / 3, (int)screenHeight - 500);
		
		buyer.setLocation(0, 0);
		seller.setLocation((int)screenWidth / 3, 0);
		farmer.setLocation((int)screenWidth / 3 + (int)screenWidth / 3, 0);

		
		GetTable farms = new GetTable(conn);
		JTable farmTable = farms.runTable("select * from farm");
		
        JScrollPane scrollPane = new JScrollPane(farmTable);
        farmTable.setFillsViewportHeight(true);
 
        JLabel lblHeading = new JLabel("Farms");
 
        farmer.getContentPane().setLayout(new BorderLayout());
 
        farmer.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        farmer.getContentPane().add(scrollPane,BorderLayout.CENTER);
 
        farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
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
                
                  // need to add something that will trigger updates either here or SQL
                
                try {
                  System.out.println("double trouble");
                 CallableStatement callItemBought = conn.prepareCall("{call item_bought(?, ?)}");
                 int dpid = (int) postingTable.getValueAt(postingTable.getSelectedRow(), 0);
                 callItemBought.setInt(1, id_num);
                 callItemBought.setInt(2, dpid);
                 callItemBought.execute();
                 
                 //String sql = "DELETE FROM posting WHERE pid = " + dpid;
                // JavaMySql jms = new JavaMySql();
                // jms.executeUpdate(conn, sql);
                // CallableStatement callDelete = conn.prepareCall("DELETE FROM buyer WHERE)
                 
                }
                catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                } 
            
                
                // System.out.println(postingTable.getValueAt(postingTable.getSelectedRow(), 0).toString());
              }
          });
/*
            // create a label to display text 
            JLabel l = new JLabel("nothing entered"); 
      
            // create a new button 
            JButton b = new JButton("submit"); 
      
            // addActionListener to button 
            b.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
                System.out.println("nice");
              }
            }
      
            // create a object of JTextField with 16 columns 
            JTextField t = new JTextField(16); 
     
      
            // add buttons and textfield to panel 
            buyer.add(t); 
            buyer.add(b); 
            buyer.add(l); 
      
        } 
      
        // if the vutton is pressed 
        public void actionPerformed(ActionEvent e) 
        { 
            String s = e.getActionCommand(); 
            if (s.equals("submit")) { 
                // set the text of the label to the text of the field 
                l.setText(t.getText()); 
      
                // set the text of field to blank 
                t.setText("  "); 
            } 
        }
*/             
      
 
//        ////////////////
//		GetTable sellerToFarm = new GetTable();
//		JTable sellerToFarmTable = sellerToFarm.runTable("select * from seller_to_farm");
//		
//        JScrollPane scrollPane1 = new JScrollPane(sellerToFarmTable);
//        farmTable.setFillsViewportHeight(true);
// 
//        JLabel lblHeading1 = new JLabel("Sellers For Farms");
// 
//        farmer.getContentPane().setLayout(new BorderLayout());
// 
//        farmer.getContentPane().add(lblHeading1,BorderLayout.PAGE_START);
//        farmer.getContentPane().add(scrollPane1,BorderLayout.CENTER);
// 
//        farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        
		buyer.setVisible(true);
		seller.setVisible(true);
		farmer.setVisible(true);
	}
	
	

	public static void main(String[] args)  throws Exception {
		fmgui display = new fmgui();
		JavaMySql javasql = new JavaMySql();
		Connection conn = javasql.getConnection();
		
		
		display.run(conn);
	}
}