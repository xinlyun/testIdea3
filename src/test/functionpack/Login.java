package test.functionpack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import test.ConnectionManager;
import test.Dao.DataProvider;
import test.Dao.InfoItem.ParentInfo;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by xinlyun on 16-4-6.
 */
public class Login extends HttpServlet {
    private DataProvider dataProvider;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String acceptjson = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        acceptjson = sb.toString();
        JSONObject msg = JSON.parseObject(acceptjson);
        int phonenumber = msg .getInteger("phonenumber");
        String password = msg.getString("password");
        ParentInfo parentInfo = DataProvider.getInstence().getParentInfo(phonenumber,password);
        JSONObject jsonObject = new JSONObject();
        if(parentInfo==null)
            jsonObject.put("code",401);
        else {
            jsonObject.put("code",200);
            jsonObject.put("data",parentInfo.toJson());
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonObject.toJSONString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

    }
    public Login(){
        super();
    }
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here

    }
    @Override
    public void init() throws ServletException {
        super.init();
        dataProvider = DataProvider.getInstence();

    }
}
