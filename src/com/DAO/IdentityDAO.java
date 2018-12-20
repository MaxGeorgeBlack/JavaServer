package com.DAO;

import com.Entity.Identity;
import com.Entity.User;
import com.Util.DBManageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdentityDAO {
    public static List<Identity> queryIdentityByUser(String user_id){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM identity WHERE user_id=?");

        try{
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, user_id);

            resultSet = preparedStatement.executeQuery();
            List<Identity> identityList = new ArrayList<>();
            while(resultSet.next()) {
                Identity identity = new Identity();
                identity.setName(resultSet.getString("name"));
                identity.setUser_id(resultSet.getString("user_id"));
                identityList.add(identity);
            }

            return identityList;
        }catch(SQLException exception){
            Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null,exception);
            return null;
        }finally{
            DBManageUtil.closeAll(connection, preparedStatement,resultSet);
        }
    }

    public static Identity queryIdentityByName(String name){
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM identity WHERE name=?");

        try{
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            Identity identity = new Identity();
            if(resultSet.next()){
                identity.setName(resultSet.getString("name"));
                identity.setUser_id(resultSet.getString("user_id"));
                return identity;
            }else{
                return null;
            }
        }catch(SQLException exception){
            Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null,exception);
            return null;
        }finally{
            DBManageUtil.closeAll(connection, preparedStatement,resultSet);
        }
    }

    public static int createIdentity(String name, String user_id) {
        Connection connection = DBManageUtil.getConnection();
        PreparedStatement preparedStatement = null;

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO identity VALUES(?,?)");

        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, user_id);

            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } finally {
            DBManageUtil.closeAll(connection, preparedStatement, null);
        }
    }

    public boolean verifyCreate(String name, String user_id){
        Identity existIdentity = IdentityDAO.queryIdentityByName(name);
        if (existIdentity != null) {
            return false;
        }

        return IdentityDAO.createIdentity(name, user_id) != -1;
    }
}
