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
		
		// creates table displaying all produce available in the catalog
		GetTable catalog = new GetTable(conn);
    JTable catalogTable = catalog.runTable("select * from catalog");
    
        JScrollPane scrollPaneCatalog = new JScrollPane(catalogTable);
        catalogTable.setFillsViewportHeight(true);
 
        JLabel lblHeadingCatalog = new JLabel("Produce Available to Sell");
      
        seller.getContentPane().setLayout(new BorderLayout());
        
        seller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     // creates table displaying all farmers available to partner with
        GetTable farmers = new GetTable(conn);
        JTable farmersTable = farmers.runTable("select * from farm");
        
            JScrollPane scrollPaneFarmers = new JScrollPane(farmersTable);
            farmersTable.setFillsViewportHeight(true);
     
            JLabel lblHeadingFarmers = new JLabel("Farms Available to Partner With");
     
            seller.getContentPane().setLayout(new BorderLayout());
            /*
            seller.add(lblHeadingFarmers,BorderLayout.PAGE_START);
            seller.add(scrollPaneCatalog, BorderLayout.CENTER);
            seller.add(lblHeadingCatalog,BorderLayout.SOUTH);
            seller.add(scrollPaneFarmers, BorderLayout.SOUTH);
            seller.validate();
            */
            seller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        
		seller.setVisible(true);
		
	}
	
}
