package com.Servlet.User;

import com.DAO.CodeDAO;
import com.DAO.UserDAO;
import com.Util.PostUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
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
        Map<String, Integer> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id").trim();
            String password = request.getParameter("password").trim();

            String passwordStrengthString = PostUtil.sendPost("http://localhost:5000/pwmx", "password=" + password);
            JSONObject passwordStrengthJson = JSONObject.fromObject(passwordStrengthString);
            int strength = Integer.parseInt((String) passwordStrengthJson.get("strength"));
            if (strength < 3) {
                // code 5 : weak password
                params.put("Result", 5);
            } else {
                Boolean verifyResult = ud.verifyAccount(id, password);

                if (verifyResult) {
                    boolean codeResult = CodeDAO.sendCode(id);
                    if (codeResult) {
                        request.getServletContext().setAttribute("UserPassword", password);

                        //code 1 : success
                        params.put("Result", 1);

                    } else {
                        //code 2 : code sending failed
                        params.put("Result", 2);
                    }
                } else {
                    //code 3 : register failed, id already exists
                    params.put("Result", 3);
                }
            }

            jsonObject.put("Status", params);
            jsonObject.put("Strength", strength);
            out.write(jsonObject.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
