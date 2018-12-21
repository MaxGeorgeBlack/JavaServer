package com.DAO;

import com.Entity.Token;
import com.Entity.User;
import com.Util.DBManageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO {
    public static Token queryToken(String token){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM token WHERE token=?");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, token);

            resultSet = preparedStatement.executeQuery();
            Token currentToken = new Token();
            if (resultSet.next()) {
                currentToken.setToken(resultSet.getString("token"));
                currentToken.setUser_id(resultSet.getString("user_id"));
                currentToken.setExpiration(resultSet.getString("expiration"));
                return currentToken;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            Logger.getLogger(TokenDAO.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static int createToken(String token, String user_id, String expiration){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();

        try {

            sqlStatement.append("DELETE FROM token WHERE user_id=?");
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, user_id);

            if(preparedStatement.executeUpdate() == -1){
                return -1;
            }

            sqlStatement.setLength(0);
            sqlStatement.append("INSERT INTO token VALUES(?,?,?)");
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, expiration);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(TokenDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }

    public static int deleteToken(String token){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("DELETE FROM token WHERE token=?");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, token);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(TokenDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }
}
