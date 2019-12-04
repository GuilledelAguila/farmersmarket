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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class fmgui extends JFrame {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	//prompt user for ID
	public void runStartWindow(Connection conn) throws SQLException {

		
	}
	
	

	public static void main(String[] args)  throws Exception {
		fmgui display = new fmgui();
		JavaMySql javasql = new JavaMySql();
		Connection conn = javasql.getConnection();
		Buyer b = new Buyer();
		Seller s = new Seller();
		Farmer f = new Farmer();
		
		// gets the id
		display.runStartWindow(conn);
		int id = 0;
		
		// if for each window
		b.run(conn, id);
		s.run(conn, id);
		f.run(conn, id);
		
	}
}