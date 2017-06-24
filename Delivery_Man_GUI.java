package Takeout_manage_system;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Delivery_Man_GUI extends Thread{
	 private JFrame mainFrame;
	 private JFrame useFrame;
	 private int concern_order;
	 
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JLabel useheaderLabel;
	   private JLabel usestatusLabel;
	   private JLabel useinformationLabel;
	   private JPanel controlPanel;
	   private Delivery_Man deliveryman;
	   
	   private JButton getfood;
	   private JButton sendfood;
	   public Delivery_Man_GUI(){
		   this.concern_order=0;
		   this.deliveryman=new Delivery_Man();
	   }

	   public void SwingControlDemo(){
	      prepareGUI();
	   }

	   public  void show(){
	      SwingControlDemo();      
	      showTextFieldDemo();
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("派送员登录");
	      mainFrame.setSize(400,400);
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
	      //////////////////////////////////////////////////////////////////////////////////////////////
	      useFrame=new JFrame("派送员操作窗口");
	      useFrame.setSize(400,400);
	      useFrame.setLayout(new GridLayout(5, 1));
	      useFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });  
	      this.useheaderLabel=new JLabel("", JLabel.CENTER);    
	      this.usestatusLabel=new JLabel("", JLabel.CENTER);   
	      this.useinformationLabel=new JLabel("", JLabel.CENTER);  
	      
	      this.getfood=new JButton("取货完成");
	      this.sendfood=new JButton("送货完成");
	      
	      useFrame.add(this.useheaderLabel);
	      useFrame.add(this.usestatusLabel);
	      useFrame.add(this.useinformationLabel);
	      useFrame.add(this.getfood);
	      useFrame.add(this.sendfood);
	      
	      mainFrame.setVisible(true);  
	      useFrame.setVisible(false);
	   }

	   private void showTextFieldDemo(){
	      headerLabel.setText("派送员登录"); 

	      JLabel  namelabel= new JLabel("User ID: ", JLabel.RIGHT);
	      JLabel  passwordLabel = new JLabel("Password: ", JLabel.CENTER);
	      final JTextField userText = new JTextField(6);
	      final JPasswordField passwordText = new JPasswordField(6);      

	      JButton loginButton = new JButton("Login");
	      loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) { 
	        	 if(deliveryman.login(userText.getText(), new String(passwordText.getPassword()))){
	        		mainFrame.setVisible(false);
	 	        	useFrame.setVisible(true);
	 	        	new Thread(new Runnable(){
	 		            @Override
	 		            public void run() {
	 		                while(true){
	 		                	scanorders();
	 		                	try {
	 								Thread.sleep(1000);
	 							} catch (InterruptedException e) {
	 								// TODO Auto-generated catch block
	 								e.printStackTrace();
	 							}
	 		                }
	 		            }
	 		        }).start();
	        	 }
	        	 else{
	        		 statusLabel.setText("用户名或密码有误");
	        	 }
	        	}
	      }); 

	      controlPanel.add(namelabel);
	      controlPanel.add(userText);
	      controlPanel.add(passwordLabel);       
	      controlPanel.add(passwordText);
	      controlPanel.add(loginButton);
	      mainFrame.setVisible(true);  
	      
	      /////////////////////////////////////////////////////////////////////////////
	      this.useheaderLabel.setText("派送员使用界面");
	      this.useinformationLabel.setText("暂无使用信息推送");
	      this.usestatusLabel.setText("暂无订单");
	      getfood.setEnabled(false);
	      sendfood.setEnabled(false);
	      getfood.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) { 
		        	sendfood.setEnabled(true);
		        	getfood.setEnabled(false);
		        	deliveryman.Get_Food();
		        }
		  }); 
	      sendfood.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) { 
		        	sendfood.setEnabled(false);
		        	deliveryman.Delivery();
		        	concern_order=0;
		        }
		  }); 
	      
	   }
	   private void scanorders(){
		   int order=deliveryman.waitingfororders();
		   if(order==0){
			   if(deliveryman.Delivery_situation==false)
				   this.usestatusLabel.setText("暂无订单");
			   else;
		   }
		   else{
			   this.usestatusLabel.setText(deliveryman.show_order(order));
			   getfood.setEnabled(true);
			   concern_order=order;
			   
		   }
		   if(concern_order!=0){
			   this.useinformationLabel.setText(deliveryman.show_information(concern_order));
		   }
		   else{
			   this.useinformationLabel.setText("暂无使用信息推送");
		   }
	   }
	   public void run(){
		   this.show();
		   
	   }

}
