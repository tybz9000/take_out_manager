package Takeout_manage_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Takeout_manage_system.SQL_INFORMATION;


public class Test {
	public static Statement statement;
	public Test() {
		try
		{
			Class.forName(SQL_INFORMATION.DRIVER_MYSQL);		//加载JDBC驱动
			System.out.println("Driver Load Success.");
			
			Connection connection = DriverManager.getConnection(SQL_INFORMATION.URL);	//创建数据库连接对象
			statement = connection.createStatement();		//创建Statement对象
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		Test datebase=new Test();
		
		 Customer_GUI customer_gui = new Customer_GUI();
    	 customer_gui.show_L_o_R();
    	 Producer_GUI producer_gui = new Producer_GUI();
     	 producer_gui.run();
     	 Delivery_Man_GUI delivery_man_gui = new Delivery_Man_GUI();
     	 delivery_man_gui.run();
	}
}
