package farmersmarket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Buyer {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	GetTable modelTable;
	JTable mainTable = new JTable();
	JFrame buyer_frame = new JFrame("buyer");
	JPanel table_panel = new JPanel();
	JPanel filter_panel = new JPanel();
	JPanel history_panel = new JPanel();
	JPanel make_review_panel = new JPanel();

	
	JComboBox choose_farm = new JComboBox();
	JComboBox choose_seller = new JComboBox();
	JComboBox choose_product = new JComboBox();
	JComboBox choose_lower = new JComboBox();
	JComboBox choose_upper = new JComboBox();
	JComboBox choose_farm_to_review = new JComboBox();
	
	JTextField price_lower = new JTextField(3);
	JTextField price_upper = new JTextField(3);
	
	JButton filter_button = new JButton("Filter");
	JButton history_button = new JButton("View Shopping History");
	JButton postings_button = new JButton("View Available Postings");
	JButton reviews_button = new JButton("View farm reviews");
	JButton make_review_button = new JButton("Leave a Review");

	// get all available postings that can be bought at the current time with or without filters
	public void getPostings(JTable table, GetTable modelTable, String farm, String seller, 
			String product, int price_lower, int price_upper, Connection conn) {
		
		try {
			CallableStatement callGetPostings = conn.prepareCall("{call search_post(?, ? , ?, ?, ?)}");
			if(farm == "--") {
				callGetPostings.setNull(1, java.sql.Types.VARCHAR);
			} else {
				callGetPostings.setString(1, farm);
			}
			
			if(seller == "--") {
				callGetPostings.setNull(2, java.sql.Types.VARCHAR);
			} else {
				callGetPostings.setString(2,  seller);
			}
			
			if(product == "--") {
				callGetPostings.setNull(3, java.sql.Types.VARCHAR);
			} else {
				callGetPostings.setString(3, product);
			}
			
			if(price_lower == -1) {
				callGetPostings.setNull(4, java.sql.Types.INTEGER);
			} else {
				callGetPostings.setInt(4, price_lower);
			}
			
			if(price_upper == -1) {
				callGetPostings.setNull(5,java.sql.Types.INTEGER);
			} else {
				callGetPostings.setInt(5, price_upper);
			}
			
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
	
	// insert bought item into buyer_to_posting table
	public void item_bought(JTable table, GetTable modelTable, int buyer_id, int posting_id, Connection conn) {
		
		try {
			CallableStatement callItemBought = conn.prepareCall("{call item_bought(?, ?)}");
			callItemBought.setInt(1, buyer_id);
			callItemBought.setInt(2, posting_id);
			callItemBought.execute();
			callItemBought.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//get the buyer history
	public void buyer_history(JTable table, GetTable modelTable, int buyer_id, Connection conn) {
		
		try {
			CallableStatement callBuyerHistory = conn.prepareCall("{call buyer_history(?)}");
			callBuyerHistory.setInt(1, buyer_id);
			ResultSet s = callBuyerHistory.executeQuery();
			table.setModel(modelTable.buildTableModel(s));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	//get all farm reviews
	public void get_reviews(JTable table, GetTable modelTable, Connection conn) {
		
		try {
			CallableStatement callBuyerHistory = conn.prepareCall("{call get_farm_reviews}");
			ResultSet s = callBuyerHistory.executeQuery();
			table.setModel(modelTable.buildTableModel(s));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//set all dropdowns with data for the filter area
	public void set_filter_dropdowns(Connection conn) {
		
		
		try {
			String sql = "Select Distinct farm_name from farm";
			PreparedStatement pstmt_get_farms = conn.prepareStatement(sql);
			String sql2 = "SELECT Distinct CONCAT(first_name, \" \", last_name) as seller_name FROM seller";
			PreparedStatement pstmt_get_sellers = conn.prepareStatement(sql2);
			String sql3 = "Select Distinct produce_name from catalog";
			PreparedStatement pstmt_get_products = conn.prepareStatement(sql3);

			try {
				ResultSet s = pstmt_get_farms.executeQuery(sql);
				choose_farm.addItem("--");
				while(s.next()) {
					choose_farm.addItem(s.getString("farm_name"));
				}
				s = pstmt_get_sellers.executeQuery(sql2);
				choose_seller.addItem("--");
				while(s.next()) {
					choose_seller.addItem(s.getString("seller_name"));
				}
				s = pstmt_get_products.executeQuery(sql3);
				choose_product.addItem("--");
				while(s.next()) {
					choose_product.addItem(s.getString("produce_name"));
				}

			} finally {
				pstmt_get_farms.close();
				pstmt_get_sellers.close();
				pstmt_get_products.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		choose_lower.addItem("--");
		for(int i = 0; i < 50; i++) {
			choose_lower.addItem(i);
		}
		choose_upper.addItem("--");
		for(int i = 0; i < 50; i++) {
			choose_upper.addItem(i);
		}
		
		
	}
	


	public void run(Connection conn, int id) throws SQLException {

		//configuring main frame
		buyer_frame.setLayout(new BoxLayout(buyer_frame.getContentPane(), BoxLayout.Y_AXIS));
		buyer_frame.setSize((int)screenWidth / 3, (int)screenHeight -650);
		buyer_frame.setLocation(0, 0);
		
		//configuring panels that will go in the main frame
		table_panel.setLayout(new FlowLayout());
		filter_panel.setLayout(new FlowLayout());
		history_panel.setLayout(new FlowLayout());
		make_review_panel.setLayout(new FlowLayout());
		
		// adding buttons to allow user to see history, postings or reviews
		history_panel.add(history_button);
		history_panel.add(postings_button);
		history_panel.add(reviews_button);
		
		//adding review button to review pannel
		make_review_panel.add(make_review_button);
		modelTable = new GetTable(conn);
		
		//adding all dropdowns to filtering area in frame
		set_filter_dropdowns(conn);
		JLabel label1 = new JLabel("Select Farm:");
		filter_panel.add(label1);
		filter_panel.add(choose_farm);
		label1 = new JLabel("Select Seller:");
		filter_panel.add(label1);
		filter_panel.add(choose_seller);
		label1 = new JLabel("Select Produce:");
		filter_panel.add(label1);
		filter_panel.add(choose_product);
		label1 = new JLabel("Lower Price:");
		filter_panel.add(label1);
		filter_panel.add(choose_lower);
		label1 = new JLabel("Highest Price:");
		filter_panel.add(label1);
		filter_panel.add(choose_upper);

		filter_panel.add(filter_button);
		
		
		// getting data into the available postings table "default view"
		getPostings(mainTable, modelTable,  "--", "--", "--", -1, -1, conn);

		//configuring main table in frame
		JScrollPane mainTableContainer = new JScrollPane(mainTable);
		mainTableContainer.setPreferredSize(new Dimension(622, 200));
		filter_panel.setPreferredSize(new Dimension(622, 50));
		mainTable.setFillsViewportHeight(true);
		
		JLabel tittle = new JLabel("Available To Buy: Click on an item to buy");
		
		table_panel.add(tittle,BorderLayout.PAGE_START);
		table_panel.add(mainTableContainer);
		
		//adding all panel to the main frame
		buyer_frame.getContentPane().add(history_panel);
		buyer_frame.getContentPane().add(table_panel);
		buyer_frame.getContentPane().add(filter_panel);

		buyer_frame.getContentPane().add(make_review_panel);
		
		buyer_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Listen for row selection (item bought) in the postings table
		mainTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {

				if (event.getValueIsAdjusting()){
					if (mainTable.getSelectedRow() != -1 && tittle.getText() == "Available To Buy: Click on an item to buy") {
						int selected_product_id = (int) mainTable.getValueAt(mainTable.getSelectedRow(), 0);
						//update buyer_to_postings table
						item_bought(mainTable, modelTable, id, selected_product_id, conn);
						//refresh postings table
						getPostings(mainTable, modelTable,  "--", "--", "--", -1, -1, conn);
					}
				}
			}
		});
		
		//listen for buyer wants to filter the postings table
        filter_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//get all filtered areas the user selected
            	String chosen_farm = (String)choose_farm.getSelectedItem();
            	String chosen_seller = (String)choose_seller.getSelectedItem();
            	String chosen_produce = (String)choose_product.getSelectedItem();
            	String chosen_price_upper;
            	String chosen_price_lower;
            	
            	try {
            		chosen_price_upper  = (String)choose_upper.getSelectedItem();
            		chosen_price_upper = "-1";
            	} catch(ClassCastException caster) {
            		chosen_price_upper  = Integer.toString((int)choose_upper.getSelectedItem());
            	}
            	
            	try {
            		chosen_price_lower  = (String)choose_lower.getSelectedItem();
            		chosen_price_lower = "-1";
            	} catch(ClassCastException caster) {
            		 chosen_price_lower  = Integer.toString((int)choose_lower.getSelectedItem());
            	}
            	
            	//refresh table with new filtered data
            	getPostings(mainTable, modelTable,  chosen_farm, chosen_seller, chosen_produce,
            			(int) Integer.parseInt(chosen_price_lower), (int) Integer.parseInt(chosen_price_upper), conn);
                tittle.setText("Farm Reviews");
            }
        });
        
        //listen for when buyer wants to see shopping history
        history_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            buyer_history(mainTable, modelTable, id, conn);
            tittle.setText("Shopping History");
            }
        });
        
        //listen for when thu buyer wants to look at postings (no filters)
        postings_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            getPostings(mainTable, modelTable,  "--", "--", "--", -1, -1, conn);
            tittle.setText("Available To Buy: Click on an item to buy");

            }
        });
        
        //listen for when the user wants to see all reviews
        reviews_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            get_reviews(mainTable, modelTable, conn);
            tittle.setText("Farm Reviews");

            }
        });
        
        //listen for when the user wants to make a review
        make_review_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//show reviews in main table
                get_reviews(mainTable, modelTable, conn);
                tittle.setText("Farm Reviews");
            	
                //setup new window
            	JFrame review_frame = new JFrame("Review");
            	JPanel review_panel = new JPanel();
            	JTextArea review_area = new JTextArea(10, 50);
        		JLabel choose_farm_label = new JLabel("Choose Farm to Review:");
        		JButton submit_review_button = new JButton("Submit Review OR Delete Review (If Empty)");
            	
        		review_frame.setLayout(new BoxLayout(review_frame.getContentPane(), BoxLayout.Y_AXIS));
        		review_panel.setLayout(new FlowLayout());
        		
        		review_frame.setSize((int)screenWidth / 3, (int)screenHeight -800);
        		review_frame.setLocation(400,400);
        		
        		//get all farms from which the buyer has bought before
        		try {
					CallableStatement callGetFarms = conn.prepareCall("{CALL get_buyer_farms(?)}");
					callGetFarms.setInt(1, id);
					ResultSet s = callGetFarms.executeQuery();
					choose_farm_to_review.removeAllItems();
					while(s.next()) {
						choose_farm_to_review.addItem(s.getString("farm"));
					}
					callGetFarms.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		//add all ui elements to the frame
        		review_panel.add(choose_farm_label);
        		review_panel.add(choose_farm_to_review);
        		review_panel.add(review_area);
        		review_panel.add(submit_review_button);
        		
        		review_frame.add(review_panel);
        		review_frame.setVisible(true);
        		buyer_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		
        		//listen for when the user submits the review
                submit_review_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                    	String chosen_farm = (String)choose_farm_to_review.getSelectedItem();
                    	int farmid = 0;
                    	String review = review_area.getText();

                    	//get the farm id from the farm name the buyer selects
                		try {
                			String sql = "Select Distinct fid from farm where farm_name = ?;";
                			PreparedStatement pstmt_get_id = conn.prepareStatement(sql);
                			pstmt_get_id.setString(1, chosen_farm);

            				ResultSet s = pstmt_get_id.executeQuery();
            				while(s.next()) {
            					 farmid = s.getInt("fid");
            				}
                			pstmt_get_id.close();
                			
                		} catch (SQLException err) {
                			// TODO Auto-generated catch block
                			err.printStackTrace();
                		}
                		
                		//insert/delete/update buyer review
                		try {

                			CallableStatement callSubmitReview = conn.prepareCall("{CALL insert_update_review(?,?,?)}");
                			callSubmitReview.setInt(1, id);
                			callSubmitReview.setInt(2, farmid);
                			if (review.length() != 0) {
                				callSubmitReview.setString(3, review);
                			} else {
                				callSubmitReview.setNull(3, java.sql.Types.VARCHAR);
                			}
                			
                			callSubmitReview.execute();
                            get_reviews(mainTable, modelTable, conn);
                            tittle.setText("Farm Reviews");
                			callSubmitReview.close();
                        	

                		} catch (SQLException err) {
                			// TODO Auto-generated catch block
                			err.printStackTrace();
                			
                		}
                		

                    

                    }
                });
        		


            }
        });

		buyer_frame.setVisible(true);

	}
}
