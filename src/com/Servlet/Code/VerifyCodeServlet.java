package com.Servlet.Code;

import com.DAO.CodeDAO;
import com.DAO.UserDAO;
import com.Entity.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VerifyCodeServlet", urlPatterns = "/VerifyCode")
public class VerifyCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        CodeDAO cd = new CodeDAO();

        try(PrintWriter out = response.getWriter()){
            String address = request.getParameter("address").trim();
            String code = request.getParameter("code").trim();
            String time = String.valueOf(System.currentTimeMillis());

            Boolean verifyResult = cd.verifyCode(address, code, time);

            Map<String, Integer> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if(verifyResult){
                //code 1 : success
                params.put("Result", 1);
            }else{
                //code 2 : wrong code
                UserDAO.deleteUser(address);
                params.put("Result", 2);
            }

            jsonObject.put("Status", params);
            out.write(jsonObject.toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
