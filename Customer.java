package Takeout_manage_system;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends Thread{//消费者类
	private Statement statement;
	public boolean login_state;//登录状态
	private String user_id;
	public Customer(){
		login_state=false;
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
	
	public boolean register(String id, String phone, String key, String name, String add){
		login_state=false;
		String sql="INSERT INTO 消费者 VALUES ('"+id+"','"+phone+"','"+key+"','"+name+"','"+add+"')";
		try
		{
			statement.execute(sql);
			//System.out.println("信息注册成功，请登录");
			this.user_id=id;
			login_state=true;
			return true;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public int login(String id,String key){//登录方法
		Scanner sc=new Scanner(System.in);
		int rcount=0;
		if(login_state==true){
			System.out.println("您已登录，请勿重复登录");
			return 2;
		}
			String sql="SELECT COUNT(*) FROM 消费者 WHERE consumer_id='"+id+"' AND consumer_key='"+key+"'";
			try {
				ResultSet rs=statement.executeQuery(sql);
				
				if(rs.next())rcount=rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rcount==1){
				login_state=true;
			//	System.out.println("登录成功");
				this.user_id=id;
				return 1;

			}
			else{
			//	System.out.println("用户名或密码有误");
				return 0;
			}
		
	}
	public ResultSet get_restaurant_menu(){
		
		ResultSet result = null;
		String sql="SELECT * FROM 商家";
		try
			{
				result = statement.executeQuery(sql);
				return result;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
/*		try
		{
			while(result.next()) {
				System.out.println("商家号: " + result.getString(1) 
						+ ", 商家联系方式: " + result.getString(2)
						+ ", 商家名: " +result.getString(3)
						+ ", 商家地址: " +result.getString(4));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
public ResultSet get_menu(String Id){
		
		String res_id = Id;
		ResultSet result = null;
		String sql="SELECT * FROM 餐品 where 商家号='"+Id+"'";
		try
			{
				result = statement.executeQuery(sql);
				return result;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
/*
		try
		{
			while(result.next()) {
				System.out.println("餐品号: " + result.getString(1) 
						+ ", 商家号: " + result.getString(2)
						+ ", 菜名: " +result.getString(3)
						+ ", 价格: " +result.getString(4));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
public int add_to_shopping_cart(String Id){
	int shopping_id=-1;
	int shop=-1;
	
	ResultSet result = null;
		System.out.println("请输入您看中的餐品号");
		String food_id = Id;
		String sql="SELECT 商家号 FROM 餐品 WHERE 餐品号='"+food_id+"'";
		try
		{
			result = statement.executeQuery(sql);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}
		try
		{
			if(result.next()){
				shop =result.getInt(1);
			}
			else{
				System.out.println("餐品不存在，请输入正确的餐品号");
				return 0;
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(shopping_id==-1  ){//如果购物车还没填过菜品
			shopping_id=shop;
			String sql2="INSERT INTO 购物车 VALUES ('"+this.user_id+"','"+food_id+"')";
			try
			{
				statement.execute(sql2);
				System.out.println("已加至购物车;");
				return 1;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(shopping_id==shop){
			String sql2="INSERT INTO 购物车 VALUES ('"+this.user_id+"','"+food_id+"')";
			try
			{
				statement.execute(sql2);
				System.out.println("已加至购物车;");
				return 2;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("购物车只能容纳一个商家的商品，您可以放弃本次操作(y) 或 选择已选择商家的菜品(n)");
				String sql3="DELETE * FROM 购物车 WHERE 用户用户名='"+this.user_id+"'";
				try
				{
					statement.execute(sql3);
					System.out.println("操作已结束");
					return 3;
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		
		return 4;
		
	}

	public ResultSet show_cart()
	{
		String sql="SELECT 餐品.餐品号,商家号,菜名,价格  FROM 购物车,餐品 WHERE 购物车.用户用户名='"+user_id+"'and "
				+ "购物车.餐品号 = 餐品.餐品号 ";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sql);
			return result;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}
	public boolean evaluate(String Id , String Content){
	String res_id = Id;
	String evaluation = Content;
	String sql="INSERT INTO 评价 VALUES ('"+ res_id +"','"+this.user_id+"','"+evaluation+"')";
	try
	{
		statement.execute(sql);
		return true;
	} catch (SQLException e)
	{
		// TODO Auto-generated catch bloc
		return false;
	}
	}
	public ResultSet show_evaluation(String Id){
	String res_id = Id;
	String sql="SELECT * FROM 评价 WHERE 商家号 ='"+res_id+"'";;
	
	ResultSet result = null;
	try
	{
		result = statement.executeQuery(sql);
		return result;
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		//e.printStackTrace();
		return null;
	}
	}
	public void delete_cart()
	{
		String sql="delete from 购物车 where 用户用户名 = '"+this.user_id+"'";
		try
		{
			statement.execute(sql);
			System.out.println("成功删除用户购物车;");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public int Order(){//下单函数，产生订单选定商家和派送员
	String Food = null;
	String Producer_Id = null;
	int Order_Id=0;
	
	ResultSet result = null;//找到购物车中的食物属于哪个商家
	String sql="SELECT 餐品号 FROM 购物车 WHERE 用户用户名='"+this.user_id+"'";
	try
	{
		result = statement.executeQuery(sql);
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	try
	{
		if(result.next()){
			Food =result.getString(1);
		}
		
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	String sql2 = "SELECT 商家号 FROM 餐品 WHERE 餐品号 = '"+Food+"' ";
	try
	{
		result = statement.executeQuery(sql2);
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try
	{
		if(result.next()) {
			Producer_Id = result.getString(1) ;
		}
		
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//商家号寻找完毕
	String sql3 = "INSERT INTO 订单 VALUES(0,'"+this.user_id+"','"+Producer_Id+"',0,1)";
	try
	{
		statement.execute(sql3);
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("已经添加"+user_id+"的订单");
	String sql4 = "SELECT 订单号 FROM 订单 WHERE 用户用户名 = '"+this.user_id+"'AND 餐品号 = '"+Food+"'";
	try
	{
		result = statement.executeQuery(sql);
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try
	{
		while(result.next()) {
			Order_Id = result.getInt(1) ;
		}
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return Order_Id;
	
}
	public ResultSet Get_Food_Name(String Food_Id)
	{
		String sql = "SELECT * FROM 餐品  WHERE 餐品号 = '"+Food_Id+"'";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sql);
			return result;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		
	public void run(){
		String input;
		System.out.println("用户请输入以下字符串调取服务：\nlogin:  登录\nregister:  注册\nmenu:  获取菜单"
				+ "\nadd:  添加至购物车\norder:  下订单\nevalute:  评论");
	}
	
	
}
