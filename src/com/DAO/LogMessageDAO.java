package com.DAO;

import com.Entity.LogMessage;
import com.Entity.User;
import com.Util.DBManageUtil;
import com.mysql.cj.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogMessageDAO {
    public static List<LogMessage> queryLogs(String user_id){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM logs WHERE user_id=? order by time desc");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, user_id);

            resultSet = preparedStatement.executeQuery();
            List<LogMessage> logList = new ArrayList<>();
            while(resultSet.next()) {
                LogMessage log = new LogMessage();
                log.setId(resultSet.getString("id"));
                log.setTime(resultSet.getString("time"));
                log.setInfo(resultSet.getString("info"));
                log.setUser_id(user_id);

                logList.add(log);
            }
            return logList;
        } catch (SQLException exception) {
            Logger.getLogger(LogMessageDAO.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static LogMessage queryLatestLog(String user_id){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM logs WHERE user_id=? order by time desc");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, user_id);

            resultSet = preparedStatement.executeQuery();
            LogMessage log = new LogMessage();
            if (resultSet.next()) {
                log.setId(resultSet.getString("id"));
                log.setTime(resultSet.getString("time"));
                log.setInfo(resultSet.getString("info"));
                log.setUser_id(user_id);

                return log;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            Logger.getLogger(LogMessageDAO.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static int createLog(String id, String time, String info, String user_id) {
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO logs VALUES(?,?,?,?)");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, info);
            preparedStatement.setString(4, user_id);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(LogMessageDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }
}
