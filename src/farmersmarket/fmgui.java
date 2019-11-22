package farmersmarket;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class fmgui extends JFrame {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	
	public void run() {
		JFrame buyer = new JFrame("buyer");
		JFrame seller = new JFrame("seller");
		JFrame farmer = new JFrame("farmer");
		
		buyer.setSize((int)screenWidth / 3, (int)screenHeight);
		seller.setSize((int)screenWidth / 3, (int)screenHeight);
		farmer.setSize((int)screenWidth / 3, (int)screenHeight);
		
		buyer.setLocation(0, 0);
		seller.setLocation((int)screenWidth / 3, 0);
		farmer.setLocation((int)screenWidth / 3 + (int)screenWidth / 3, 0);
		
		buyer.setVisible(true);
		seller.setVisible(true);
		farmer.setVisible(true);
	}
	
	
	

	public static void main(String[] args)  throws Exception {
		fmgui display = new fmgui();

		display.run();
	}
}