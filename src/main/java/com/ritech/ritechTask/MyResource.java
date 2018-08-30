package com.ritech.ritechTask;

import com.ritech.ritechTask.db.DBConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        DBConnection.setUpConnection();

        String query = "SELECT * FROM Sherbime";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return String.valueOf(resultSet);
    }



}
