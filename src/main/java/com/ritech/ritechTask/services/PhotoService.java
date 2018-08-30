package com.ritech.ritechTask.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ritech.ritechTask.controllers.PhotoController;
import com.ritech.ritechTask.controllers.ServiceBookController;
import com.ritech.ritechTask.controllers.UserController;
import com.ritech.ritechTask.entities.Photo;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

@Path("/photo")
public class PhotoService {
    private static final String UPLOAD_FOLDER = "/home/erlind/liber-sherbimesh/public/images";

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idPhoto}")
    public static Photo getPhoto(
            @PathParam("idPhoto") int idPhoto
    ){
        Photo photo = PhotoController.getPhoto(idPhoto);

        return  photo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allPhotos/{idSherbime}")
    public static List<Photo> getAllPhotos(@PathParam("idSherbime") long idSherbime){
        List<Photo> allPhotos = PhotoController.getAllPhotos(idSherbime);

        return  allPhotos;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idPhoto}")
    public static boolean deletePhotos(@PathParam("idPhoto") int idPhoto){

        boolean photo = PhotoController.deletePhotos(idPhoto);


        return photo;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public static Photo updatePhoto(@FormParam("photo") String photoJSON)  {
        ObjectMapper mapper = new ObjectMapper();

        Photo obj = null;
        try {
            obj = mapper.readValue(photoJSON, Photo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Photo photo = PhotoController.updatePhoto(obj);
        return photo;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPhoto(
            @Context HttpHeaders httpHeaders,
            @FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("image") FormDataContentDisposition fileDetail,
            @FormDataParam("email") String email,
            @FormDataParam("sherbimeId") int sherbimeId

    )  {
        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("Invalid form data").build();


        // create our destination folder, if it not exists
        try {
            createFolderIfNotExists(UPLOAD_FOLDER+"/"+email);
            createFolderIfNotExists(UPLOAD_FOLDER+"/"+email+"/"+sherbimeId);
        } catch (SecurityException se) {
            return Response.status(500)
                    .entity("Can not create destination folder on server")
                    .build();
        }
        String uploadedFileLocation = UPLOAD_FOLDER+"/"+email +"/"+sherbimeId+"/"+ fileDetail.getFileName();
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);

        } catch (IOException e) {
            return Response.status(500).entity("Can not save file").build();
        }
        Photo photo=new Photo();
        photo.setServiceBookId(sherbimeId);
        photo.setPhotoSrc(uploadedFileLocation);
        PhotoController photoService=new PhotoController();
        photoService.createPhoto(photo);

        return Response.status(200)
                .entity("File saved to " + uploadedFileLocation).build();

    }

    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

    private static void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }


}
