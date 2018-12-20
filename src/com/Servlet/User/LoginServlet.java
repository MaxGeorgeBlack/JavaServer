package com.Servlet.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.DAO.LogMessageDAO;
import com.DAO.UserDAO;
import com.Entity.User;
import com.Util.TimeUtil;
import net.sf.json.JSONObject;

@WebServlet(name = "LoginServlet", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        UserDAO ud = new UserDAO();
        LogMessageDAO lmd = new LogMessageDAO();

        try(PrintWriter out = response.getWriter()){
            String id = request.getParameter("id").trim();
            String password = request.getParameter("password").trim();

            String log_id = String.valueOf(System.currentTimeMillis());
            String time = TimeUtil.getTime();
            String info = "Last Login: " + time;
            boolean verifyResult = ud.verifyLogin(id, password);
            boolean insertLog = (lmd.createLog(log_id, time, info, id) != -1);


            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if(verifyResult && insertLog){
                params.put("Result", "Success");
            }else{
                params.put("Result", "Failed");
            }

            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
