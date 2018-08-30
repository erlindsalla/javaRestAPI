package com.ritech.ritechTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ritech.ritechTask.controllers.ServiceBookController;
import com.ritech.ritechTask.entities.ServiceBook;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.ritech.ritechTask.controllers.ServiceBookController.deleteServiceBook;

@Path("/service")
public class ServiceBookService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{serviceBookId}")
    public static ServiceBook getServiceBook(@PathParam("serviceBookId") int serviceBookId){
        ServiceBook serviceBook = ServiceBookController.getServiceBook(serviceBookId);

        return serviceBook;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/serviceBookList/{userId}")
    public static List <ServiceBook>     getAllServiceBooks(@PathParam("userId") long serviceBookId){
       List <ServiceBook> serviceBook = ServiceBookController.getAllServiceBooks(serviceBookId);

        return serviceBook;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
        public static ServiceBook createServiceBook( String serviceBookJson){
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        ServiceBook obj = null;
        try {
            obj = mapper.readValue(serviceBookJson, ServiceBook.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServiceBook serviceBook = ServiceBookController.createServiceBook(obj);
        return serviceBook;

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{serviceBookId}")

    public static boolean deleteServiceBook(@PathParam("serviceBookId") int serviceBookId){
        Boolean serviceBook = ServiceBookController.deleteServiceBook(serviceBookId);

        return true;

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public static ServiceBook updateServiceBook(String serviceBookJson){
        ObjectMapper mapper = new ObjectMapper();

        ServiceBook obj = null;
        try {
            obj = mapper.readValue(serviceBookJson, ServiceBook.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServiceBook serviceBook = ServiceBookController.updateServiceBook(obj);
        return serviceBook;
    }
}
