package Takeout_manage_system;


import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Producer {
	
	private int 	Producer_Number		;
	private String 	Producer_PhoneNumber;
	private String 	Producer_Address	;
	private  String	Producer_Name		;
	private int 	Producer_Money		;					/*表示历史一共收到的金额。*/
	private Statement statement;
	public Producer()
	{
		Producer_Money = 0;
		try
		{
			Class.forName(SQL_INFORMATION.DRIVER_MYSQL);		//加载JDBC驱动
			//System.out.println("Driver Load Success.");
			
			Connection connection = DriverManager.getConnection(SQL_INFORMATION.URL);	//创建数据库连接对象
			statement = connection.createStatement();		//创建Statement对象
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void register(String producer_name,String producer_address,String phone){
		this.Producer_Name=producer_name;
		this.Producer_Address=producer_address;
		this.Producer_PhoneNumber=phone;
		String sql="INSERT INTO 商家 VALUES (0,'"+phone+"','"+producer_name+"','"+producer_address+"')";
		try
		{
			statement.execute(sql);
			System.out.println("信息注册成功。");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add_menu(String id,String value){
		String sql="INSERT INTO 餐品 VALUES (0,'"+this.Producer_Number+"','"+id+"','"+value+"')";
		try
		{
			statement.execute(sql);
			//System.out.println("信息注册成功。");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void remove_menu(String id){
		String sql5 = "DELETE FROM 餐品 WHERE 餐品号='"+id+"'";
		try
		{
			statement.execute(sql5);
			//System.out.println("信息注册成功。");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void get_Number(int id){
		this.Producer_Number=id;
	}
	
	public boolean login(String id,String key){//登录方法
		
		int rcount=0;
		String sql="SELECT COUNT(*) FROM 商家 WHERE 商家号='"+id+"' AND 商家密码='"+key+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			
			if(rs.next())rcount=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rcount==1){
			this.Producer_Number=Integer.parseInt(id);
			return true;
		}
		else{
			System.out.println("用户名或密码有误");
			return false;
		}
	
}
}
	
