package com.Servlet.Identity;

import com.DAO.IdentityDAO;
import com.DAO.TokenDAO;
import com.DAO.UserDAO;
import com.Entity.Identity;
import com.Entity.User;
import com.Util.TimeUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SearchIdentityServlet", urlPatterns = "/SearchIdentityByUser")
public class SearchIdentityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        try (PrintWriter out = response.getWriter()) {
            Map<String, Integer> params = new HashMap<>();
            Map<String, String> identities = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            String token = request.getParameter("token").trim();

            try {
                String user_id = TokenDAO.queryToken(token).getUser_id();
                long expiration = Long.parseLong(TokenDAO.queryToken(token).getExpiration());
                if (TimeUtil.getCurrentTime() < expiration) {
                    List<Identity> allIdentities = IdentityDAO.queryIdentityByUser(user_id);

                    if (allIdentities != null) {
                        //code 1 : success
                        params.put("Result", 1);

                        for (Identity identity : allIdentities) {
                            identities.put("name", identity.getName());
                        }
                    } else {
                        //code 2 : identity query failed
                        params.put("Result", 2);
                    }
                } else {
                    //code 3 : token expired
                    params.put("Result", 3);
                }
            }catch (NullPointerException exception){
                //code 4 : token not exists
                params.put("Result", 4);
            }

            jsonObject.put("Status", params);
            jsonObject.put("identities", identities);

            out.write(jsonObject.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
