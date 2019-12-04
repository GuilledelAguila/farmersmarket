package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Farmer {
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	public void run(Connection conn, int id) throws SQLException {
		
		JFrame farmer = new JFrame("farmer");
		
		farmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		farmer.setLayout(new BorderLayout());
		
		farmer.setSize((int)screenWidth / 3, (int)screenHeight - 500);
		
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
        
		farmer.setVisible(true);
		
	}
}
