package com.Util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DBManageUtil extends HttpServlet{
    ServletConfig config;
    private static String username;
    private static String password;
    private static String url;
    private static Connection connection;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        this.config = config;
        username = config.getInitParameter("DBUsername");
        password = config.getInitParameter("DBPassword");
        url = config.getInitParameter("ConnectionURL");
    }

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, username, password);
        }catch(Exception exception){
            Logger.getLogger(DBManageUtil.class.getName()).log(Level.SEVERE, null, exception);
        }
        return connection;
    }

    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }catch(SQLException exception){
            Logger.getLogger(DBManageUtil.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
