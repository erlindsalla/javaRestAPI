package com.ritech.ritechTask.controllers;

import com.ritech.ritechTask.db.DBConnection;
import com.ritech.ritechTask.entities.Photo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoController {

    public static Photo getPhoto(int photoId){
        String getPhotoQuery = "SELECT * FROM Photos WHERE idPhoto = ?";
        Photo photo = null;
        try {
            DBConnection.setUpConnection();
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getPhotoQuery);
            preparedStatement.setLong(1, photoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                photo = new Photo();
                photo.setPhotoId(resultSet.getInt("idPhoto"));
                photo.setPhotoSrc(resultSet.getString("src"));
                photo.setServiceBookId(resultSet.getInt("idSherbime"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return photo;
    }

    public static Photo createPhoto(Photo photo){

        try {
            DBConnection.setUpConnection();
        String createPhotoQuery = "INSERT INTO Photos (src, idSherbime) VALUES ( ?, ?)";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(createPhotoQuery);
        preparedStatement.setString(1, photo.getPhotoSrc().split("public")[1]);
        preparedStatement.setInt(2, photo.getServiceBookId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next()){
            photo.setPhotoId(resultSet.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return photo;
    }

    public static Photo updatePhoto(Photo photo){
        try {
            DBConnection.setUpConnection();
            String updatePhotoQuery = "UPDATE  Photos SET idPhoto= null, src=?, idSherbime=? ";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updatePhotoQuery);
            preparedStatement.setString(1, photo.getPhotoSrc());
            preparedStatement.setInt(2, photo.getServiceBookId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                photo.setPhotoId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photo;

    }
    public static boolean deletePhotos (int idSherbime){
        try {
            DBConnection.setUpConnection();
            String deletePhotoQuery = "DELETE FROM Photos WHERE idSherbime = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deletePhotoQuery);
            preparedStatement.setInt(1, idSherbime);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    public static List<Photo> getAllPhotos(long idSherbime){
        List<Photo> photoList = new ArrayList<>();
        try{
            DBConnection.setUpConnection();
            String getAllPhotosQuery = "SELECT * FROM Photos WHERE idSherbime = ?  ";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getAllPhotosQuery);
            preparedStatement.setLong(1, idSherbime);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Photo photo = new Photo();
                photo.setPhotoSrc(resultSet.getString("src"));
                photo.setServiceBookId(resultSet.getInt("idSherbime"));
                photo.setPhotoId(resultSet.getInt("idPhoto"));
                photoList.add(photo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  photoList;
    }
}
