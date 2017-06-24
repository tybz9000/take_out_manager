package Takeout_manage_system;


import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Customer_GUI extends Thread{
	
	   private JFrame customer_frame;
	   private JFrame use_frame;
	   private JFrame register_frame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JTextField id_input;
	   private JTextField r_id_input;
	   private JTextField r_phone_input;
	   private JTextField r_key_input;
	   private JTextField r_name_input;
	   private JTextField r_add_input;
	   private JTextField key_input;
	   private JButton register_button_l_o_r = new JButton("注册");
	   private JButton login_button_l_o_r = new JButton("登录");
	   private JButton register_button_r = new JButton("注册");
	   private JButton producer_list_button;
	   private JButton menu_button;
	   private JButton cart_button;
	   private JButton order_button;
	   private JButton commit_button;
	   private JButton delete_cart_button;
	   private JButton show_cart_button;
	   private JButton show_evaluation_button;
	   private JTextArea show_area;
	   private JTextField input;
	   private Customer customer = new Customer();
	   

	   public void run()
	   {
		   show_L_o_R();
	   }
	   public  void show_L_o_R(){/*login or  register*/
		  prepareGUI_L_o_R();      
	      showTextFieldDemo_L_o_R();
	      Trigger_Login_L_o_R();
	      Trigger_register_L_o_R();
	   }
	   
	   	public void prepareGUI_L_o_R(){
		   /**/
		    customer_frame = new JFrame("顾客登录");
			customer_frame.setSize(400,600);
			customer_frame.setLayout(null);
			
			JLabel head_name = new JLabel("",JLabel.CENTER );
			JLabel id_label = new JLabel("",JLabel.CENTER); 
		    JLabel key_label = new JLabel("",JLabel.CENTER); 
		    
		    id_input=new JTextField();
		    key_input=new JTextField();
		    
		    JPanel controlPanel = new JPanel();
		    controlPanel.setLayout(new FlowLayout());
		    
		    customer_frame.add(head_name);
		    customer_frame.add(id_label);
		    customer_frame.add(id_input);
		    customer_frame.add(key_label);
		    customer_frame.add(key_input);
		    customer_frame.add(controlPanel);
		    customer_frame.add(register_button_l_o_r);
		    customer_frame.add(login_button_l_o_r);
		      
			customer_frame.addWindowListener(new WindowAdapter() {
		         public void windowClosing(WindowEvent windowEvent){
			        System.exit(0);
		         }        
		    }); 
			head_name.setText("欢迎使用本外卖系统，请登录");
			head_name.setBounds(100, 0, 200, 50);
			id_label.setText("用户名:");
			id_label.setBounds(70, 80, 60, 50);
			key_label.setText("密码:");
			key_label.setBounds(70, 180, 50, 50);
			id_input.setBounds(170,100,200,20);
			key_input.setBounds(170,200,200,20);
			register_button_l_o_r.setBounds(100, 400, 200, 100);
			login_button_l_o_r.setBounds(100, 300, 200, 100);
			login_button_l_o_r.setActionCommand("登录");
			register_button_l_o_r.setActionCommand("注册1");
	   }

	   private void showTextFieldDemo_L_o_R(){
	      customer_frame.setVisible(true);  
	   }
	   public void Trigger_Login_L_o_R()
	   {
		    login_button_l_o_r.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   				String id=id_input.getText();
		   				String key=key_input.getText();
		   				int FeedBack = -1;
		   				FeedBack = customer.login(id,key);
		   				if(FeedBack == 0)
		   				{
		   					JOptionPane.showMessageDialog(customer_frame, "请输入正确的用户名和密码", "",JOptionPane.WARNING_MESSAGE); 
		   				}
		   				if(FeedBack == 1)
		   				{
		   					JOptionPane.showMessageDialog(customer_frame, "登录成功！欢迎进入外卖订餐系统"); 
		   				}
		   				if(FeedBack == 2)
		   				{
		   					JOptionPane.showMessageDialog(customer_frame, "不能重复登录", "",JOptionPane.WARNING_MESSAGE);
		   				}
		   				if(customer.login_state == true)
		   				{
		   					customer_frame.dispose();
		   					show_details();
		   				}
		   		}	
		   });
	   }
	   public void Trigger_register_L_o_R()
	   {
		    register_button_l_o_r.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			customer_frame.dispose();
		   					show_register();
		   		}	
		   });
	   }
	   public void show_register()
	   {
		   prepareGUI_register();
		   showTextFieldDemo_register();  
		   Trigger_register_R();
	   }
	   public void showTextFieldDemo_register()
	   {
		   register_frame.setVisible(true);
	   }
	   public void Trigger_register_R()
	   {
		    register_button_r.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
					String id	 =	r_id_input.getText();
					String phone =	r_phone_input.getText();
					String key	 =	r_key_input.getText();
					String name	 =	r_name_input.getText();
					String add	 =	r_add_input.getText();
		   			if(customer.register(id,phone,key,name,add) == false)
		   			{/*注册失败重返注册界面*/
		   				register_frame.dispose();
		   				show_register();
		   			}
		   			else
		   			{/*注册成功，返回登陆界面*/
		   				register_frame.dispose();
		   				show_details();
		   			}
		   		}	
		   });

	   }
	   public void prepareGUI_register()
	   {
			register_frame = new JFrame("注册界面");
			register_frame.setSize(400,600);
			register_frame.setLayout(null);
			
			JLabel r_id = new JLabel("",JLabel.CENTER );
			r_id.setText("用户名：");
			r_id.setBounds(0, 50, 70, 50);
		    JLabel r_phone = new JLabel("",JLabel.CENTER); 
		    r_phone.setText("手机号：");
		    r_phone.setBounds(0, 150, 70, 50);
		    JLabel r_key = new JLabel("",JLabel.CENTER); 
		    r_key.setText("密码：");
		    r_key.setBounds(0, 250, 70, 50);
		    JLabel r_name = new JLabel("",JLabel.CENTER );
		    r_name.setText("实名：");
		    r_name.setBounds(0, 350, 70, 50);
		    JLabel r_add = new JLabel("",JLabel.CENTER); 
		    r_add.setText("地址：");
		    r_add.setBounds(0, 450, 70, 50);
		    
		    r_id_input=new JTextField();
		    r_id_input.setBounds(150, 50, 200, 30);
		    r_phone_input=new JTextField();
		    r_phone_input.setBounds(150, 150, 200, 30);
		    r_key_input=new JTextField();
		    r_key_input.setBounds(150, 250, 200, 30);
		    r_name_input=new JTextField();
		    r_name_input.setBounds(150, 350, 200, 30);
		    r_add_input=new JTextField();
		    r_add_input.setBounds(150, 450, 200, 30);
		    
		    register_button_r.setBounds(150, 500, 150, 50);
		    register_frame.add(r_id);
		    register_frame.add(r_phone);
		    register_frame.add(r_key);
		    register_frame.add(r_name);
		    register_frame.add(r_add);
		    register_frame.add(r_id_input);
		    register_frame.add(r_phone_input);
		    register_frame.add(r_key_input);
		    register_frame.add(r_name_input);
		    register_frame.add(r_add_input);
		    register_frame.add(register_button_r);
	   }
	   public  void show_details(){/*login or  register*/
			  prepareGUI_details();      
		      showTextFieldDemo_details();
		      Trigger_producer_list_button();
		      Trigger_menu_button();
		      Trigger_cart_button();
		      Trigger_order_button();
		      Trigger_commit_button();
		      Trigger_show_cart_button();
		      Trigger_delete_cart_button();
		      Trigger_show_evaluation_button();
		   }
	   
	public void prepareGUI_details()
	   {
		    use_frame = new JFrame("外卖平台");
			use_frame.setSize(600,700);
			use_frame.setLayout(null);
			
			producer_list_button = new JButton("商家列表");
			producer_list_button.setBounds(0,300,200,50);
			producer_list_button.setActionCommand("商家列表");
			menu_button = new JButton("菜单");
			menu_button.setBounds(400, 300, 200, 50);
			menu_button.setActionCommand("菜单");
			cart_button = new JButton("加入购物车");
			cart_button.setBounds(0, 500, 200, 50);
			cart_button.setActionCommand("加入购物车");
			order_button = new JButton("下单");
			order_button.setBounds(200, 500, 200, 50);
			order_button.setActionCommand("下单");
			commit_button = new JButton("评价");
			commit_button.setBounds(400, 500, 200, 50);
			commit_button.setActionCommand("评价");
			delete_cart_button = new JButton("清空购物车");
			show_cart_button = new JButton("查看购物车");
			delete_cart_button.setBounds(200, 550, 200, 50); 
			show_cart_button.setBounds(0, 550, 200, 50); 
			show_evaluation_button = new JButton("查看评价");
			show_evaluation_button.setBounds(400, 550, 200, 50);
			
			show_area=new JTextArea();
			show_area.setBounds(0, 0, 600, 300);
			
			input=new JTextField();
			input.setBounds(0, 400, 600, 70);
		    
			use_frame.add(producer_list_button);
			use_frame.add(menu_button);
			use_frame.add(cart_button);
			use_frame.add(order_button);
			use_frame.add(commit_button);
			use_frame.add(delete_cart_button);
			use_frame.add(show_cart_button);
			use_frame.add(show_evaluation_button);
			use_frame.add(show_area);
			use_frame.add(input);
		    
	   }
	   public void showTextFieldDemo_details()
	   {
		   use_frame.setVisible(true);
	   }
	   public void Trigger_producer_list_button()
	   {
		   producer_list_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   					/*等待更改GUI界面*/
		   			ResultSet result = null;
		   			result = customer.get_restaurant_menu();
		   			try
		   			{	
		   				show_area.setText("");
		   				Font font=new Font("宋体",Font.BOLD,20);   
		   				show_area.setFont(font);
			   			while(result.next()) 
			   			{
			   				show_area.append("商家号: " + result.getString(1) +"\n" 
			   				+ "商家联系方式: " + result.getString(2) +"\n"
			   				+ "商家名: " +result.getString(3) +"\n"
			   				+ "商家地址: " +result.getString(4) +"\n");
			   			}
		   			}
			   		catch (SQLException e1)
			   		{
			   			// TODO Auto-generated catch block
			   			e1.printStackTrace();
			   		}
		   		}
		   		
		   });

	   }
	   public void Trigger_menu_button()
	   {
		   menu_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			boolean if_has_next = false;
		   			ResultSet result = null;
		   			String inputValue = JOptionPane.showInputDialog(use_frame,"请输入您想要查询的商家号");
		   			if(inputValue == null)
		   				return ;
		   			result = customer.get_menu(inputValue);/*等待添加GUI界面(添加输入商家号的文本框)*/
		   			try
		   			{	
		   				show_area.setText("");
			   			while(result.next()) 
			   			{
			   				show_area.append("餐品号: " + result.getString(1) +"\n"
			   				+ "商家号: " + result.getString(2)+"\n"
			   				+ "菜名: " +result.getString(3)+"\n"
			   				+ "价格: " +result.getString(4)+"\n");
			   				if_has_next = true;
			   			}
			   			if(if_has_next == false)
			   			{
			   				JOptionPane.showMessageDialog(use_frame, "您输入的商家不存在或尚无菜单信息，请重新输入", "",JOptionPane.WARNING_MESSAGE);
			   			}
		   			}
			   		catch (SQLException e1)
			   		{
			   			// TODO Auto-generated catch block
			   			e1.printStackTrace();
			   		}
		   		}	
		   });

	   }
	   public void Trigger_cart_button()
	   {
		   cart_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			String Id =  input.getText();
		   			int Feedback = -1;
	//	   			customer.add_to_shopping_cart();/*等待更改GUI界面*/
		   			String inputValue = JOptionPane.showInputDialog(use_frame,"请输入您想添加至购物车的餐品号");
		   			JOptionPane.showConfirmDialog(use_frame, "确认添加至购物车？", "", JOptionPane.YES_NO_OPTION);
		   			if(inputValue != null)
		   				Feedback = customer.add_to_shopping_cart(inputValue);
		   			if(Feedback == 0)
		   				JOptionPane.showMessageDialog(use_frame, "请您输入正确的餐品号", "餐品查询异常",JOptionPane.WARNING_MESSAGE); 
		   			if(Feedback == 1)
		   				JOptionPane.showMessageDialog(use_frame, "您输入的餐品已经添加至购物车");
		   		}	
		   });

	   }
	   public void Trigger_order_button()
	   {
		   order_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			ResultSet result = null;
		   			result = customer.show_cart();
		   			boolean if_has_next = false;
			   			try
			   			{	
				   			while(result.next()) 
				   			{
				   				if_has_next = true;
					   		}	   			
				   			if(if_has_next == false)
				   			{
				   				JOptionPane.showMessageDialog(use_frame, "您的购物车为空,此时不能下订单", "下订单异常",JOptionPane.WARNING_MESSAGE);
				   			}
							if(if_has_next)
								customer.Order();
			   			}
			   			catch (SQLException e1)
				   		{
				   			// TODO Auto-generated catch block
				   			e1.printStackTrace();
				   		}
		   		}
		   		
		   });

	   }
	   public void Trigger_commit_button()
	   {
		   commit_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			boolean flag;
		   			String Id = JOptionPane.showInputDialog(use_frame,"请输入您想要评价的商家");
		   			if(Id == null)
		   				return ;
		   			String Content = JOptionPane.showInputDialog(use_frame,"请输入您想要评价的内容");
		   			if(Content == null)
		   				return ;
		   			flag = customer.evaluate(Id,Content);/*等待更改GUI界面*/
		   			if(flag)
		   				JOptionPane.showMessageDialog(use_frame, "您已成功添加评论");
		   			else
		   				JOptionPane.showMessageDialog(use_frame, "您输入的商家不存在！","",JOptionPane.WARNING_MESSAGE);
		   			
		   				
		   		}	
		   });
	   }
	   public void Trigger_show_cart_button()
	   {
		   show_cart_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			ResultSet result = null;
		   			String Food_Id;
		   			String Producer_Id;
		   			String Food_Name;
		   			String Price;
		   			result = customer.show_cart();
		   			boolean if_has_next = false;
		   			
			   			try
			   			{	
			   				show_area.setText("");
				   			while(result.next()) 
				   			{
				   				if_has_next = true;
				   				Food_Id = result.getString(1);
				   				Producer_Id = result.getString(2);
			   					Food_Name = result.getString(3);
			   					Price = result.getString(4);
			   					show_area.append("餐品号:  " + Food_Id +"\n"
					   					+"商家号:  " + Producer_Id + "\n"
					   					+"菜名:  " + Food_Name + "\n"
					   					+"价格:  " + Price + "\n"+"\n") ;
					   				}	   			
				   			if(if_has_next == false)
				   			{
				   				JOptionPane.showMessageDialog(use_frame, "您的购物车为空", "购物车查询异常",JOptionPane.WARNING_MESSAGE);
				   			}
			   			}
				   		catch (SQLException e1)
				   		{
				   			// TODO Auto-generated catch block
				   			e1.printStackTrace();
				   		}
		   		}	
		   });
	   }
	   public void Trigger_delete_cart_button()
	   {
		   delete_cart_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   		 customer.delete_cart();
				   JOptionPane.showMessageDialog(use_frame, "您的购物车已经清空", "购物车清空",JOptionPane.WARNING_MESSAGE);
		   		}	
		   });
	   }
	   public void Trigger_show_evaluation_button()
	   {
		   show_evaluation_button.addActionListener(new ActionListener() 
		   	{
		   		public void actionPerformed(ActionEvent e) 
		   		{
		   			ResultSet result = null;
		   			String Id = JOptionPane.showInputDialog(use_frame,"请输入您想要查询评价的商家");
		   			result = customer.show_evaluation(Id);
		   			boolean if_has_next = false;
		   			try
		   			{	
		   				show_area.setText("");
			   			while(result.next()) 
			   			{
			   				show_area.append("商家号: " + result.getString(1)+"\n"
			   						+"用户用户名: " + result.getString(2)+"\n"
			   						+"评价内容: " + result.getString(3)+"\n") ;
			   				if_has_next = true;
			   			}
			   			if(if_has_next == false)
			   			{
			   				JOptionPane.showMessageDialog(use_frame, "此商家的评论为空,您可以来评论一下", "",JOptionPane.WARNING_MESSAGE);
			   			}
		   			}
			   		catch (SQLException e1)
			   		{
			   			// TODO Auto-generated catch block
			   			e1.printStackTrace();
			   		}
		   		}	
		   });
	   }
}
