package com.ingsw.dietiDeals24.model;

import java.io.Serializable;

public class Image implements Serializable {

    private Integer idImage;
    private String base64Data;

    public Image(Integer idImage, String base64Data) {
        this.idImage = idImage;
        this.base64Data = base64Data;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

}
