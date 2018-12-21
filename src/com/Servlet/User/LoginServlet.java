package com.Servlet.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.DAO.LogMessageDAO;
import com.DAO.TokenDAO;
import com.DAO.UserDAO;
import com.Util.TimeUtil;
import net.sf.json.JSONObject;

@WebServlet(name = "LoginServlet", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        UserDAO ud = new UserDAO();

        try {
            PrintWriter out = response.getWriter();
            String id = request.getParameter("id").trim();
            String password = request.getParameter("password").trim();

            String log_id = String.valueOf(System.currentTimeMillis());
            String time = TimeUtil.getTime();
            String info = "Last Login: " + time;
            String verifyResult = ud.verifyLogin(id, password);
            boolean insertLog = false;
            boolean insertToken = false;

            if (verifyResult != null) {
                String[] tokens  = verifyResult.split(" ");
                insertToken = (TokenDAO.createToken(tokens[0], id, tokens[1]) != -1);
            }

            Map<String, Integer> result = new HashMap<>();
            Map<String , String> token = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if (verifyResult != null  && insertToken) {
                //code 1 : success
                result.put("Result", 1);
                token.put("Token", verifyResult.split(" ")[0]);
                insertLog = (LogMessageDAO.createLog(log_id, time, info, id) != -1);
                if(!insertLog){
                    //code 4 : log insertion failed
                    TokenDAO.deleteToken(verifyResult.split(" ")[0]);
                    token.clear();
                    result.clear();
                    result.put("Result", 4);
                }
            }
            else if(verifyResult == null){
                //code 2 : wrong password
                result.put("Result", 2);
            }else if(!insertToken){
                //code 3 : token creation failed
                result.put("Result", 3);
            }

            jsonObject.put("Status", result);
            jsonObject.put("Token", token);

            out.write(jsonObject.toString());
        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
