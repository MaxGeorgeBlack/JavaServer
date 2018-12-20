package com.Servlet.Log;

import com.DAO.LogMessageDAO;
import com.DAO.UserDAO;
import com.Entity.LogMessage;
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
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetAllLogsServlet", urlPatterns = "/GetAllLogs")
public class GetAllLogsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");

        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");

        try (PrintWriter out = response.getWriter()) {
            String user_id = request.getParameter("user_id").trim();
            List<LogMessage> logs = LogMessageDAO.queryLogs(user_id);

            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if (logs != null) {
                params.put("Result", "Success");
            } else {
                params.put("Result", "Failed");
            }

            jsonObject.put("params", params);
            params.clear();
            for (LogMessage log : logs) {
                params.put(log.getTime(), log.getInfo());
            }
            jsonObject.put("logs", params);

            out.write(jsonObject.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
