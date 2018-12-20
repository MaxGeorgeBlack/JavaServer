package com.DAO;

import com.Entity.Code;
import com.Entity.User;
import com.Util.DBManageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeDAO {
    public static Code queryLatestCode(String user_id){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM verifycode WHERE user_id=? order by dead_time desc");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, user_id);

            resultSet = preparedStatement.executeQuery();
            Code code = new Code();
            if (resultSet.next()) {
                code.setCode(resultSet.getString("code"));
                code.setId(resultSet.getString("id"));
                code.setUser_id(user_id);
                code.setSend_time(resultSet.getString("send_time"));
                code.setDead_time(resultSet.getString("dead_time"));

                return code;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            Logger.getLogger(CodeDAO.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static int createCode(String id, String user_id, String code, String send_time, String dead_time){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO verifycode VALUES(?,?,?,?,?)");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, code);
            preparedStatement.setString(4, send_time);
            preparedStatement.setString(5, dead_time);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(CodeDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }

    public boolean verifyCode(String user_id, String inputCode, String time){
        Code code = CodeDAO.queryLatestCode(user_id);
        return code != null &&
                time.compareTo(code.getDead_time()) < 0 &&
                inputCode.equals(code.getCode()) ;
    }
}
