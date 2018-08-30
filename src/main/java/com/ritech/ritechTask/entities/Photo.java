package com.ritech.ritechTask.entities;

public class Photo {
    private int photoId;
    private String photoSrc;
    private int serviceBookId;

    public Photo() {
    }

    public Photo(int photoId, String photoSrc, int serviceId) {
        this.photoId = photoId;
        this.photoSrc = photoSrc;
        this.serviceBookId = serviceId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public int getServiceBookId() {
        return serviceBookId;
    }

    public void setServiceBookId(int serviceId) {
        this.serviceBookId = serviceId;
    }
}
