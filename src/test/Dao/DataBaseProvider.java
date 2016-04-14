package test.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by xinlyun on 16-3-31.
 */
public class DataBaseProvider {
    private DataBaseProvider dataBaseProvider;
    private Connection connection;
    private DataBaseProvider(){
        initDatabase();
    }
    private DataBaseProvider getInstence(){
        if(dataBaseProvider==null)
            dataBaseProvider = new DataBaseProvider();
        return dataBaseProvider;
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
            this.connection = con;
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
}
