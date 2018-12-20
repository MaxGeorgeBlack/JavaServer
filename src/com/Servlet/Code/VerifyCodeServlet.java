package com.Servlet.Code;

import com.DAO.CodeDAO;
import com.DAO.UserDAO;
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
            String user_id = request.getParameter("user_id").trim();
            String code = request.getParameter("code").trim();
            String time = String.valueOf(System.currentTimeMillis());

            Boolean verifyResult = cd.verifyCode(user_id, code, time);

            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if(verifyResult){
                params.put("Result", "Success");
            }else{
                params.put("Result", "Failed");
            }

            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
