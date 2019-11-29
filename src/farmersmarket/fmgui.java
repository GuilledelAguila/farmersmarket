package farmersmarket;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
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
	
	
	public void run() {
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

				
		GetTable farms = new GetTable();
		JTable farmTable = farms.runTable("select * from farm");
		
        JScrollPane scrollPane = new JScrollPane(farmTable);
        farmTable.setFillsViewportHeight(true);
 
        JLabel lblHeading = new JLabel("Farms");
 
        farmer.getContentPane().setLayout(new BorderLayout());
 
        farmer.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        farmer.getContentPane().add(scrollPane,BorderLayout.CENTER);
 
        farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
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

		display.run();
	}
}