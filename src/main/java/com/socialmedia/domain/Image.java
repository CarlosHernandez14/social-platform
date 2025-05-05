/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Image implements Serializable {

    private UUID idImage; 
    private String image_path;
    private Date createdAt;
    
    private UUID postId;

    public Image() {
    }

    public Image(UUID idImage, String image_path, Date createdAt, UUID postId) {
        this.idImage = idImage;
        this.image_path = image_path;
        this.createdAt = createdAt;
        this.postId = postId;
    }

    public Image(String image_path, Date createdAt, UUID postId) {
        this.image_path = image_path;
        this.createdAt = createdAt;
        this.postId = postId;
        
        this.idImage = UUID.randomUUID();
    }
    
    public Image(String image_path, Date createdAt) {
        this.image_path = image_path;
        this.createdAt = createdAt;
        
        this.idImage = UUID.randomUUID();
    }
    

    public UUID getIdImage() {
        return idImage;
    }

    public void setIdImage(UUID idImage) {
        this.idImage = idImage;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Image{" + "idImage=" + idImage + ", image_path=" + image_path + ", createdAt=" + createdAt + ", postId=" + postId + '}';
    }
    
    
}
