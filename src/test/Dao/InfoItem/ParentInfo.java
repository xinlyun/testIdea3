package test.Dao.InfoItem;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by xinlyun on 16-4-12.
 */
public class ParentInfo {
    int idParent;
    int phonenum;
    String password;
    String name ;
    String email;
    String token;
    Connection connection;
    int style;
    public void setConnnetSql(Connection connnetSql){
        this.connection = connnetSql;
    }

    public ParentInfo(int phonenum,String password,String name,String email,String token){

    }
    public ParentInfo(int idParent,Connection connection){
        style = 1;
        this.connection = connection;
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from parent where idparent = "+idParent;
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            init(idParent,rs.getInt("phone number"),rs.getString("password")
                    ,rs.getString("name"),rs.getString("email"),"aliljfo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ParentInfo(int num,String password,Connection connection){
        style = 2;
        this.connection = connection;
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from parent where phone number = "+phonenum+",password="+password;
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            init(rs.getInt("idparent"),num,password
                    ,rs.getString("name"),rs.getString("email"),"aliljfo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ParentInfo(String token,Connection connection){
        style = 2;
        this.connection = connection;
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from parent where role = "+token;
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            init(rs.getInt("idparent"),rs.getInt("phone number"),rs.getString("password")
                    ,rs.getString("name"),rs.getString("email"),token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void save(Connection connection){
        try {
            String sql = "";
            Statement statement = connection.createStatement();
            if(style == 0){
                sql = "insert into parent set idparent = "+idParent+",phone number="+phonenum+",password="+password+",name="+name+",email="+email+",role="+token;
            }else if(style ==1){
                sql = "update parent set phone number="+phonenum+",password="+password+",name="+name+",email="+email+",role="+token+" where idparent = "+idParent;
            }else if(style == 2){
                sql = "update parent set idparent = "+idParent+",phone number="+phonenum+",password="+password+",name="+name+",email="+email+" where role = "+token;
            }
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    public ParentInfo(int idParent,int phonenum,String password,String name,String email,String token){
        style = 0;
        init(idParent,phonenum,password,name,email,token);
    }

    private void init(int idParent,int phonenum,String password,String name,String email,String token){
        this.idParent = idParent;
        this.phonenum = phonenum;
        this.password = password;
        this.name       = name;
        this.email  =   email;
        this.token = token;
    }


    public int getIdParent() {
        return idParent;
    }

    public int getPhonenum() {
        return phonenum;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenum(int phonenum) {
        this.phonenum = phonenum;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idparent",getIdParent());
        jsonObject.put("phone_number",getPhonenum());
        jsonObject.put("name",getName());
        jsonObject.put("email",getEmail());
        jsonObject.put("role",getToken());
        return jsonObject.toJSONString();
    }

}
