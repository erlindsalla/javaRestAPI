package com.ritech.ritechTask.controllers;

import com.ritech.ritechTask.db.DBConnection;
import com.ritech.ritechTask.entities.ServiceBook;

import java.sql.Date;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBookController {

    public static ServiceBook getServiceBook(int serviceId) {

        String getServiceQuery = "SELECT * FROM Sherbime WHERE idSherbime = ?";
        ServiceBook serviceBook = null;
        try {
            DBConnection.setUpConnection();
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getServiceQuery);
            preparedStatement.setInt(1, serviceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                serviceBook = new ServiceBook();
                serviceBook.setUserId(resultSet.getInt("idUser"));
                serviceBook.setName(resultSet.getString("nameSherbim"));
                serviceBook.setKm(resultSet.getLong("km"));
                serviceBook.setPrice(resultSet.getLong("price"));
                serviceBook.setDate((resultSet.getString("date")));
                serviceBook.setDescription(resultSet.getString("description"));
                serviceBook.setReminder(resultSet.getString("reminder"));
                serviceBook.setIdService(serviceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceBook;
    }

    public static List<ServiceBook> getAllServiceBooks(long userId) {
        List<ServiceBook> serviceBookList = new ArrayList<>();

        try {
            DBConnection.setUpConnection();
            String getAllServiceBooksQuery = "SELECT * FROM Sherbime WHERE idUser = ?  ";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getAllServiceBooksQuery);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ServiceBook serviceBook = new ServiceBook();
                serviceBook.setUserId(resultSet.getInt("idUser"));
                serviceBook.setName(resultSet.getString("nameSherbim"));
                serviceBook.setKm(resultSet.getLong("km"));
                serviceBook.setIdService(resultSet.getInt("idSherbime"));
                serviceBook.setPrice(resultSet.getLong("price"));
                serviceBook.setDate((resultSet.getString("date")));
                serviceBook.setDescription(resultSet.getString("description"));
                serviceBook.setReminder(resultSet.getString("reminder"));
                serviceBookList.add(serviceBook);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceBookList;
    }


    public static ServiceBook createServiceBook(ServiceBook serviceBook) {
        long idServiceBook = 0;
        try {
            DBConnection.setUpConnection();
            String createServiceBookQuery = "INSERT INTO Sherbime ( idUser, nameSherbim, km, price, date, description, reminder) VALUES (?, ?, ?,?,?,?,?)";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(createServiceBookQuery);
            preparedStatement.setLong(1, serviceBook.getUserId());
            preparedStatement.setString(2, serviceBook.getName());
            preparedStatement.setLong(3, serviceBook.getKm());
            preparedStatement.setLong(4, serviceBook.getPrice());
            preparedStatement.setString(5,  serviceBook.getDate());
            preparedStatement.setString(6, serviceBook.getDescription());
            preparedStatement.setString(7,  serviceBook.getReminder());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                serviceBook.setIdService((int)resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceBook;
    }

    public static ServiceBook updateServiceBook(ServiceBook serviceBook){


        try {
            DBConnection.setUpConnection();
            String updateServiceBookQuery = "UPDATE Sherbime SET idUser = ?, nameSherbim = ?, km = ?, price = ? , date = ?, description = ?, reminder = ? WHERE idSherbime= ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateServiceBookQuery);
            preparedStatement.setLong(1, serviceBook.getUserId());
            preparedStatement.setString(2, serviceBook.getName());
            preparedStatement.setLong(3, serviceBook.getKm());
            preparedStatement.setLong(4, serviceBook.getPrice());
            preparedStatement.setDate(5,   Date.valueOf(serviceBook.getDate()));
            preparedStatement.setString(6, serviceBook.getDescription());
            preparedStatement.setDate(7, Date.valueOf(serviceBook.getReminder()));
            preparedStatement.setLong(8,serviceBook.getIdService());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceBook;
    }

    public static boolean deleteServiceBook(int serviceBookId){
        try{
            DBConnection.setUpConnection();
            String deleteServiceBookQuery = "DELETE FROM Sherbime WHERE idSherbime = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteServiceBookQuery);
            preparedStatement.setLong(1, serviceBookId);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return  true;
    }
    public static  int getLastServiceBookId(){
        int lastServiceId = 0;
        try{
            DBConnection.setUpConnection();
            String getLastServiceBookId = "SELECT idUser FROM User ORDER BY idUser DESC LIMIT 1";
            PreparedStatement preparedStatement= DBConnection.getConnection().prepareStatement(getLastServiceBookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            lastServiceId= resultSet.getInt("idUser");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  lastServiceId;
    }

}
