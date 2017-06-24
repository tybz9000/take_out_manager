package Takeout_manage_system;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Delivery_Man{

	private int	Delivery_Man_Id;
	private String	PhoneNumber_Delivery_Man;
	//private int [] 	Salary_Delivery_Man;
	public boolean Delivery_situation;
	//private int Money ;
	private Statement statement;
	//private boolean If_Delivery_Done;		/*是否派送完毕*/
	//private boolean If_Get_Food;			/*是否取得货物*/
	/*派送员取餐过程*/
	public Delivery_Man()
	{
		//Money = 0;
		this.Delivery_situation=false;
		//If_Delivery_Done = false;
		//If_Get_Food = false;
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
	private String random_phone(){//生成随机电话号码函数,这个不要也可以 
		String base="0123456789";
		Random ran=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<11;i++){
			int num=ran.nextInt(10);
			sb.append(base.charAt(num));
		}
		return sb.toString();
	}
	public void register(){
		this.PhoneNumber_Delivery_Man=random_phone();
		String sql="INSERT INTO 派送员 VALUES (0,'"+this.PhoneNumber_Delivery_Man+"',3000,'000000')";
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
	public void get_Number(){
		String sql="SELECT 派送员号 FROM 派送员 WHERE 派送员联系方式='"+this.PhoneNumber_Delivery_Man+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next())
			this.Delivery_Man_Id=rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String show_order(int order){
		String sql="SELECT * FROM 订单 WHERE 订单号='"+order+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){
				String user_id=rs.getString(2);
				String producer_id=rs.getString(3);
				String ORDER="订单号："+order+"  客户号："+user_id+"  商家号："+producer_id;
				return ORDER;
			}
			else{
				return "暂无使用信息推送";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public String show_information(int order){
		String sql="SELECT * FROM 订单 WHERE 订单号='"+order+"'";
		try {
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()){
				int state=rs.getInt(5);
				if(state==2){
					String sql2="SELECT * FROM 商家 WHERE 商家号='"+rs.getInt(3)+"'";
					try{
						ResultSet rs2=statement.executeQuery(sql2);
						if(rs2.next()){
							String information="商家名:"+rs2.getString(3)+"  商家地址:  "+rs2.getString(4)+"  商家联系方式:"+rs2.getString(2);
							
							rs.close();
							rs2.close();
							return information;
						}
						else{
							return null;
						}
					}
					catch(SQLException e){
						e.printStackTrace();
						return null;
					}
				}
				if(state==3){
					String sql3="SELECT * FROM 消费者 WHERE consumer_id='"+rs.getString(2)+"'";
					try{
						ResultSet rs2=statement.executeQuery(sql3);
						if(rs2.next()){
							String information="用户实名:"+rs2.getString(4)+"    用户地址:"+rs2.getString(5)+"    用户联系方式:"+rs2.getString(2);
							rs.close();
							rs2.close();
							return information;
						}
						else{
							return null;
						}
					}
					catch(SQLException e){
						e.printStackTrace();
						return null;
					}
				}
				else{
					return "暂无信息推送";
				}
			}
			else{
				return "订单信息有误";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public boolean login(String id,String key){//登录方法
		
			int rcount=0;
			String sql="SELECT COUNT(*) FROM 派送员 WHERE 派送员号='"+id+"' AND 派送员密码='"+key+"'";
			try {
				ResultSet rs=statement.executeQuery(sql);
				
				if(rs.next())rcount=rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rcount==1){
				String sql2="SELECT 派送员联系方式 FROM 派送员 WHERE 派送员号='"+id+"'";
				try {
					ResultSet rs=statement.executeQuery(sql2);
					if(rs.next()){
						this.PhoneNumber_Delivery_Man=rs.getString(1);
						this.Delivery_Man_Id=Integer.parseInt(id);
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			else{
				System.out.println("用户名或密码有误");
				return false;
			}
		
	}
	public int waitingfororders(){//抢单函数，扫描订单表，如果发现可取订单 取之
		int order_id=0;
		if(this.Delivery_situation)return 0;
		else{
			ResultSet result = null;
			String sql="SELECT * FROM 订单 WHERE 派送员号=0";
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
				if(result.next()) {
					order_id=result.getInt(1);
					String sql2="UPDATE 订单 set 派送员号 ='"+Delivery_Man_Id+"' WHERE 订单号='"+order_id+"'";
					try
					{
						statement.execute(sql2);
						System.out.println("派送员"+this.Delivery_Man_Id+"已接单 正赶往商家");
						this.Delivery_situation=true;
						
					} catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String sql4 = "UPDATE 订单 set 订单处理情况 = 2 WHERE 订单号='"+order_id+"'";
					try
					{
						statement.execute(sql4);
					} catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("订单处理情况已经被置为2(派送员正在取餐)");
					return order_id;
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return order_id;	
		}
	}
	public int read_user_id(){
		int User_Id = 0;
		ResultSet result = null;
		String sql1 = "SELECT 用户用户名 FROM 订单 WHERE 派送员号 = '"+this.Delivery_Man_Id+"'";
		try
		{
			result = statement.executeQuery(sql1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			if(result.next()) {
				User_Id = result.getInt(1) ;
				
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//找到消费者的具体信息
		System.out.println("派送员 "+Delivery_Man_Id+"已经获取消费者信息"+User_Id);		
		return User_Id;
	}
	public int read_producer_id(){
		int Producer_Id = 0;
		ResultSet result = null;
		String sql2 = "SELECT 商家号 FROM 订单 WHERE 派送员号 = '"+this.Delivery_Man_Id+"'";
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
				Producer_Id = result.getInt(1) ;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//找到商家信息
		System.out.println("派送员 "+Delivery_Man_Id+"已经获取商家信息"+Producer_Id);		
		return Producer_Id;
	}
	public void Get_Food()
	{
		//ResultSet result = null;
		//System.out.println("派送员 "+Delivery_Man_Id+"  已经拿到餐品。");
		
		String sql5 = "UPDATE 订单 set 订单处理情况 = 3 WHERE 派送员号 = '"+this.Delivery_Man_Id+"'";
		try
		{
			statement.execute(sql5);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("订单处理情况已经被置为3(派送员正在送餐)");
		
	}
	
	/*派送员配送过程*/
	public void Delivery()
	{
		//System.out.println("派送员: "+Delivery_Man_Id+"已经完成递送。");
		
		String sql5 = "DELETE FROM 订单 WHERE 派送员号='"+this.Delivery_Man_Id+"'";
		try
		{
			statement.execute(sql5);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("订单处理情况已经被置为4(用户已经取到餐品)");
		this.Delivery_situation=false;
	}

	   
}
