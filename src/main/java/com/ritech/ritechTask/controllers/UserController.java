package com.ritech.ritechTask.controllers;

import com.ritech.ritechTask.db.DBConnection;
import com.ritech.ritechTask.entities.User;
import sun.security.pkcs11.wrapper.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    public static User getUser(int userId){
        String getUserQuery = "SELECT * FROM User WHERE idUser = ?";
        User user = null;
        try {
            DBConnection.setUpConnection();
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getUserQuery);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId((userId));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public static User createUser(User user){

        try {
            DBConnection.setUpConnection();
            String createUserQuery = "INSERT INTO User (idUser, firstName, lastName, email, password) VALUES (NULL, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(createUserQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setUserId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User updateUser(User user){

        try {
            DBConnection.setUpConnection();
            String updateUserQuery = "UPDATE User SET userId= NULL , firstName = ?, lastName = ?, email = ?, password = ? WHERE idUser = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateUserQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean deleteUser(int userId){
        try {
            DBConnection.setUpConnection();
            String deleteUserQuery = "DELETE User WHERE idUser = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteUserQuery);
            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static User login (String email, String password){
        User user = new User();
        try {
            DBConnection.setUpConnection();
            String loginQuery = "SELECT * FROM User WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(loginQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                user.setUserId(resultSet.getInt("idUser"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
