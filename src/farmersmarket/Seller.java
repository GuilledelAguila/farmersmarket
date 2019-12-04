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

public class Seller {
	
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	public void run(Connection conn, int id) throws SQLException {
		
		JFrame seller = new JFrame("seller");
		
		seller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		seller.setLayout(new BorderLayout());
		seller.setSize((int)screenWidth / 3, (int)screenHeight - 500);
		
		seller.setLocation((int)screenWidth / 3, 0);
	
        
		seller.setVisible(true);
		
	}
	
}
