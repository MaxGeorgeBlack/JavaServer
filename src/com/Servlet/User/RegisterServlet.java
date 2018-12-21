package com.Servlet.User;

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

//still not completed, problems about verify code
@WebServlet(name = "RegisterServlet", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        UserDAO ud = new UserDAO();

        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id").trim();
            String password = request.getParameter("password").trim();

            Boolean verifyResult = ud.verifyAccount(id, password);

            Map<String, Integer> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if (verifyResult) {
                boolean codeResult = CodeDAO.sendCode(id);
                if (codeResult) {
                    //code 1 : success
                    params.put("Result", 1);
                }else{
                    //code 2 : code sending failed
                    params.put("Result", 2);
                }
            } else {
                //code 3 : register failed, id already exists
                params.put("Result", 3);
            }

            jsonObject.put("Status", params);
            out.write(jsonObject.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
