package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.Dao.AndroidPushMsgToSingleDevice;

public class Test extends HttpServlet {
	private Connection con;
	TestServer testServer;
	
	/**
	 * Constructor of the object.
	 */
	public Test() {
		super();
	}
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		try {
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here

	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		System.out.print(cmd);
		if(cmd!=null)
		{
			if(testServer!=null){
				try {
					testServer.send(cmd);
				}catch (Exception e){

				}
			}
		}



		String json = getInfo();
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("ok");

		try {
			AndroidPushMsgToSingleDevice.main2();
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			e.printStackTrace();
		}


//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the GET method"+"\n");
//		if(!json.equals("")){
//			try {
//				JSONArray jsonArray = new JSONArray(json);
//				for(int i = 0 ;i<jsonArray.length();i++){
//						JSONObject mjs = (JSONObject) jsonArray.get(i);
//						out.println("  index:"+i);
//						out.println("  id:"+mjs.getInt("id"));
//						out.println("  name:"+mjs.getString("name"));
//						out.println("  sex:"+mjs.getInt("sex"));
//						out.println("  degree:"+mjs.getString("degree")+"         ");
//				}
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		out.println("  </BODY>");
//		out.println("</HTML>");

		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		System.out.print(cmd);
		if(cmd!=null)
		{
			if(testServer!=null){
				testServer.send(cmd);
			}
		}
//		JSONObject jsonObject = new JSONObject();
//		try {
//			jsonObject.put("myown", "first");
//			jsonObject.put("youown", "two");
//			jsonObject.put("site", "http://www.atool.org/hash.php");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		String string = jsonObject.toString();


		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
//		out.print(this.getClass());
		out.print("ok");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		initDatabase();
		initTcpServer();
	}

	private void  initTcpServer() {
		testServer = new TestServer();
			testServer.start();
	}
	
	public  void initDatabase() {
		//声明Connection对象
		Connection con;
		//驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		//URL指向要访问的数据库名mydata
		String url = "jdbc:mysql://localhost:3306/TraData";
		//MySQL配置时的用户名
		String user = "root";
		//MySQL配置时的密码
		String password = "4uwdb72jgd";
		//遍历查询结果集
		try {
			//加载驱动程序
			Class.forName(driver);
			//1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url,user,password);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			//2.创建statement类对象，用来执行SQL语句！！
			this.con = con;

		} catch(ClassNotFoundException e) {   
			//数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			System.out.println("数据库数据成功获取！！");
		}
	}
	
	private String   getInfo()  {
		
		
		if(con==null)
			return "";
		else {
//			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			try {
				Statement statement = con.createStatement();
				String sql = "select * from Student";
				            //3.ResultSet类，用来存放获取的结果集！！
				            ResultSet rs = statement.executeQuery(sql);			             
				            int id = 0;
				            String name = null;
				            int sex = 0;
				            float degree = 0f;
				            int index = 0;
				            while(rs.next()){
				                //获取stuname这列数据
				                id = rs.getInt("id");
				                //获取stuid这列数据
				                name = rs.getString("name");
				                sex = rs.getInt("sex");
				                degree = rs.getFloat("degree");
				                JSONObject js0 = new JSONObject();
				                js0.put("id", id);
				                js0.put("name", name);
				                js0.put("sex", sex);
				                js0.put("degree", ""+degree);
//				                jsonArray.add(js0);
				                jsonArray.put(index, js0);
				                index++;
				                //首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
				                //然后使用GB2312字符集解码指定的字节数组。      
				                //输出结果
				                System.out.println(id + "\t" + name);
				            }
				            rs.close();            
			} catch (SQLException e) {
				e.printStackTrace();
				return "";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonArray.toString();
		}
	}
	
		
	
	
	
}
