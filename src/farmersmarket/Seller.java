package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		
		JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
    // Ask for role
    JLabel prompt = new JLabel("Choose an action: ");
    prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(prompt);

    // Dropdown for actions
    String[] action = { "Catalog", "Partner with Farm", "Make Posting", "History"};
    final JComboBox<String> cb = new JComboBox<String>(action);
    cb.setMaximumSize(cb.getPreferredSize());
    cb.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(cb);
    
    // Enter button
    JButton btn = new JButton("Enter");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(btn);
    
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String actions = cb.getSelectedItem().toString();
        switch(actions) {
        case "Catalog": 
          try {
            new Seller().runCatalog(conn, id);
          }
          catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          break;
        case "Partner with Farm": 
          try {
            new Seller().runFarmPartner(conn, id);
          }
          catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          break;
        case "Make Posting": 
          try {
            new Seller().runPosting(conn, id);
          }
          catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          break;
        case "History":
          try {
            new Seller().runHistory(conn, id);
          }
          catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          break;
        default: break;
        }
      }
    });
	
    seller.add(panel);  
    seller.setVisible(true);
	}
	
	// runs window with catalog
	public void runCatalog(Connection conn, int id) throws SQLException {
	  JFrame catalogFrame = new JFrame("catalog");
    
    catalogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    catalogFrame.setLayout(new BorderLayout());
    catalogFrame.setSize((int)screenWidth / 3, (int)screenHeight - 500);
    
    catalogFrame.setLocation((int)screenWidth / 3, 0);
    
    String cquery = "select * from catalog";
    GetTable catalog = new GetTable(conn);
    JTable catalogTable = catalog.runTable(cquery);
    
        JScrollPane scrollPaneCatalog = new JScrollPane(catalogTable);
        catalogTable.setFillsViewportHeight(true);
 
        JLabel lblHeadingCatalog = new JLabel("Produce Available to Sell");
      
        catalogFrame.getContentPane().setLayout(new BorderLayout());
        
        catalogFrame.add(lblHeadingCatalog,BorderLayout.CENTER);
        catalogFrame.add(scrollPaneCatalog, BorderLayout.CENTER);
        
        catalogFrame.getContentPane().setLayout(new BorderLayout());

        catalogFrame.getContentPane().add(lblHeadingCatalog,BorderLayout.PAGE_START);
        catalogFrame.getContentPane().add(scrollPaneCatalog,BorderLayout.CENTER); 
        
        catalogFrame.setVisible(true);
	}
	
	// runs window with available farmer partnerships
	public void runFarmPartner(Connection conn, int id) throws SQLException {
    JFrame catalogFrame = new JFrame("catalog");
    
    catalogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    catalogFrame.setLayout(new BorderLayout());
    catalogFrame.setSize((int)screenWidth / 3, (int)screenHeight - 500);
    
    catalogFrame.setLocation((int)screenWidth / 3, 0);
    
    String fquery = "SELECT * FROM farm WHERE fid NOT IN (SELECT fid FROM seller_to_farm WHERE "
        + "sid = " + String.valueOf(id) + ");";
    GetTable farmers = new GetTable(conn);
    JTable farmersTable = farmers.runTable(fquery);
    
        JScrollPane scrollPaneFarmers = new JScrollPane(farmersTable);
        farmersTable.setFillsViewportHeight(true);
 
        JLabel lblHeadingFarmers = new JLabel("Farms Available to Partner With");
      
        catalogFrame.getContentPane().setLayout(new BorderLayout());
        
        catalogFrame.add(lblHeadingFarmers,BorderLayout.CENTER);
        catalogFrame.add(scrollPaneFarmers, BorderLayout.CENTER);
        
        catalogFrame.getContentPane().setLayout(new BorderLayout());

        catalogFrame.getContentPane().add(lblHeadingFarmers,BorderLayout.PAGE_START);
        catalogFrame.getContentPane().add(scrollPaneFarmers,BorderLayout.CENTER); 
        
     // allows for selection of row from a table
        farmersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
          public void valueChanged(ListSelectionEvent event) {

            if  (event.getValueIsAdjusting() ){

              // need to add something that will trigger updates either here or SQL

              try {
                CallableStatement callItemBought = conn.prepareCall("{call farmer_partner(?, ?)}");
                if (farmersTable.getSelectedRow() != -1) {
                  int fid = (int) farmersTable.getValueAt(farmersTable.getSelectedRow(), 0);
                  callItemBought.setInt(1, id);
                  callItemBought.setInt(2, fid);
                  callItemBought.execute();

                  PreparedStatement pst = conn.prepareStatement(fquery);
                  try {
                    ResultSet s = pst.executeQuery(fquery);
                    farmersTable.setModel(farmers.buildTableModel(s));
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
        catalogFrame.setVisible(true);
  }
	
//runs window with available farmer partnerships
 public void runPosting(Connection conn, int id) throws SQLException {
   JFrame catalogFrame = new JFrame("Make Posting");
   
   catalogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   
   catalogFrame.setLayout(new BorderLayout());
   catalogFrame.setSize((int)screenWidth / 3, (int)screenHeight - 500);
   
   catalogFrame.setLocation((int)screenWidth / 3, 0);
   
   String query1 = "SELECT * FROM posting \r\n" + 
       "WHERE postingid NOT IN (SELECT postingid FROM buyer_to_posting)";
   String query = "select * from (" + query1 + ") as a where sid = " + String.valueOf(id);
   GetTable farmers = new GetTable(conn);
   JTable farmersTable = farmers.runTable(query);
   
       JScrollPane scrollPaneFarmers = new JScrollPane(farmersTable);
       farmersTable.setFillsViewportHeight(true);

       JLabel lblHeadingFarmers = new JLabel("Current Postings (click to delete)");
     
       catalogFrame.getContentPane().setLayout(new BorderLayout());
       
       catalogFrame.add(lblHeadingFarmers,BorderLayout.CENTER);
       catalogFrame.add(scrollPaneFarmers, BorderLayout.CENTER);
       
       catalogFrame.getContentPane().setLayout(new BorderLayout());

       catalogFrame.getContentPane().add(lblHeadingFarmers,BorderLayout.PAGE_START);
       catalogFrame.getContentPane().add(scrollPaneFarmers,BorderLayout.CENTER);
       
       
       // allows for selection of row from a table
       farmersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
         public void valueChanged(ListSelectionEvent event) {

           if  (event.getValueIsAdjusting() ){

             // need to add something that will trigger updates either here or SQL

             try {
               CallableStatement callItemBought = conn.prepareCall("{delete_posting(?)}");
               if (farmersTable.getSelectedRow() != -1) {
                 int postid = (int) farmersTable.getValueAt(farmersTable.getSelectedRow(), 0);
                 callItemBought.setInt(1, postid);
                 callItemBought.execute();

                 PreparedStatement pst = conn.prepareStatement(query);
                 try {
                   ResultSet s = pst.executeQuery(query);
                   farmersTable.setModel(farmers.buildTableModel(s));
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
       
   catalogFrame.setVisible(true);
 }
 
 // runs window with available farmer partnerships
 public void runHistory(Connection conn, int id) throws SQLException {
   JFrame catalogFrame = new JFrame("Selling History");
   
   catalogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   
   catalogFrame.setLayout(new BorderLayout());
   catalogFrame.setSize((int)screenWidth / 3, (int)screenHeight - 500);
   
   catalogFrame.setLocation((int)screenWidth / 3, 0);
   
   String query1 = "SELECT * FROM posting \r\n" + 
       "WHERE postingid IN (SELECT postingid FROM buyer_to_posting)";
   String query = "select * from (" + query1 + ") as a where sid = " + String.valueOf(id);
   GetTable farmers = new GetTable(conn);
   JTable farmersTable = farmers.runTable(query);
   
       JScrollPane scrollPaneFarmers = new JScrollPane(farmersTable);
       farmersTable.setFillsViewportHeight(true);

       JLabel lblHeadingFarmers = new JLabel("Selling History");
     
       catalogFrame.getContentPane().setLayout(new BorderLayout());
       
       catalogFrame.add(lblHeadingFarmers,BorderLayout.CENTER);
       catalogFrame.add(scrollPaneFarmers, BorderLayout.CENTER);
       
       catalogFrame.getContentPane().setLayout(new BorderLayout());

       catalogFrame.getContentPane().add(lblHeadingFarmers,BorderLayout.PAGE_START);
       catalogFrame.getContentPane().add(scrollPaneFarmers,BorderLayout.CENTER); 
       catalogFrame.setVisible(true);
 }
	
}
