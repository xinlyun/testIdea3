package test.Dao;


import test.Dao.InfoItem.ParentInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import java.sql.DriverManager;
//import java.sql.SQLException;

/**
 * Created by xinlyun on 16-4-12.
 */
public class DataProvider {
    static DataProvider dataProvider;
    Connection con;
    private DataProvider(){
        initDatabase();
    }
    private   void initDatabase() {
        //声明Connection对象
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

    public static DataProvider getInstence(){
        if(dataProvider==null)dataProvider = new DataProvider();
        return dataProvider;
    }

    public ParentInfo getParentInfo(int id){
        ParentInfo parentInfo = new ParentInfo(id,con);
        return parentInfo;
    }
    public ParentInfo getParentInfo(String token){
        ParentInfo parentInfo = new ParentInfo(token,con);
        return parentInfo;
    }
    public ParentInfo getParentInfo(int num,String password){
        ParentInfo parentInfo = new ParentInfo(num,password,con);
        return parentInfo;
    }

}
