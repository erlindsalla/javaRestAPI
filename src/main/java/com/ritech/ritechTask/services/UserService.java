package com.ritech.ritechTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ritech.ritechTask.controllers.UserController;
import com.ritech.ritechTask.entities.User;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;

@Path("/user")
public class UserService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public static User getUser(
            @PathParam("userId") int userId
    ){
        User user = UserController.getUser(userId);

        return  user;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public static User createUser(String userJSON)  {
        ObjectMapper mapper = new ObjectMapper();

        User obj = null;
        try {
            obj = mapper.readValue(userJSON, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = UserController.createUser(obj);
        return user;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public static boolean deleteUser(
            @FormParam("userId") int userId
    ){

        boolean user = UserController.deleteUser(userId);


        return user;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public static User updateUser(String userJSON)  {
        ObjectMapper mapper = new ObjectMapper();

        User obj = null;
        try {
            obj = mapper.readValue(userJSON, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = UserController.updateUser(obj);
        return user;
    }

    @Path("/login")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public static User login(String userJSON)  {
        ObjectMapper mapper = new ObjectMapper();

        User obj = null;
        try {
            obj = mapper.readValue(userJSON, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String email= obj.getEmail();
        String password = obj.getPassword();
        User user = UserController.login(email, password);
        return user;
    }

}

