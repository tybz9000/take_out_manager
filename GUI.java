package Takeout_manage_system;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI {
	
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;

	   public void SwingControlDemo(){
	      prepareGUI();
	   }

	   public  void login(){
	      SwingControlDemo();      
	      showTextFieldDemo();
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("外卖登录系统");
	      mainFrame.setSize(1000,600);
	      mainFrame.setLayout(new GridLayout(4, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	      headerLabel = new JLabel("", JLabel.CENTER);        
	      statusLabel = new JLabel("",JLabel.CENTER);    

	      statusLabel.setSize(350,100);

	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	   }

	   private void showTextFieldDemo(){
	      headerLabel.setText("外卖登录");  

	      JButton Customer_Button = new JButton("我是顾客");
	      Customer_Button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	 closeThis();
	        	 Customer_GUI customer_gui = new Customer_GUI();
	        	 customer_gui.show_L_o_R();
	         }
	      }); 
	      JButton Producer_Button = new JButton("我是商家");
	      Producer_Button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) { 
	        	 closeThis();
	        	Producer_GUI producer_gui = new Producer_GUI();
	        	producer_gui.run();
	         }	 
	      });
	      JButton Delivery_Man_Button = new JButton("我是派送员");
	      Delivery_Man_Button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {  
	        	 closeThis();
	        	 Delivery_Man_GUI delivery_man_gui = new Delivery_Man_GUI();
	        	 delivery_man_gui.run();
	        	 
	         }
	      });

	      controlPanel.add(Customer_Button);
	      controlPanel.add(Producer_Button);
	      controlPanel.add(Delivery_Man_Button);
	      mainFrame.setVisible(true);  
	   }
	
	public void closeThis(){
		mainFrame.dispose();
	}
	
}