package com.DAO;

import com.Util.DBManageUtil;
import com.Entity.User;
import com.Util.EmailSendUtil;
import com.Util.TokenUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    public static List<User> searchAllUsers(String idPart){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE id LIKE ?");

        try{
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, idPart + "%");

            resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
            }

            return userList;
        }catch(SQLException exception){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null,exception);
            return null;
        }finally{
            DBManageUtil.closeAll(connection, preparedStatement,resultSet);
        }
    }

    public static User queryUser(String id) {
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE id=?");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, id);

            resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static int createUser(String id, String password) {
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO USER VALUES(?,?)");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }

    public static int deleteUser(String id) {
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("DELETE FROM user WHERE id=?");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, id);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }

    public String verifyLogin(String id, String password){
        User user = UserDAO.queryUser(id);
        String rightPassword = user.getPassword();

        if(user != null && password.equals(rightPassword)){
            String token = TokenUtil.sign(id);
            if(token != null){
                return token;
            }
        }

        return null;
    }

    public boolean verifyAccount(String id, String password) {
        User existUser = UserDAO.queryUser(id);
        if (existUser != null) {
            return false;
        }

        int result = UserDAO.createUser(id, password);
        return result != -1;
    }
}
