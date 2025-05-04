/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.domain;

import java.io.Serializable;
import java.util.UUID;

public class Post implements Serializable{
    
    private UUID idPost;
    private String name; 
    private String description;
   
    // Foreign relations
    private UUID profileId;

    public Post() {
    }

    public Post(UUID idPost, String name, String description, UUID profileId) {
        this.idPost = idPost;
        this.name = name;
        this.description = description;
        this.profileId = profileId;
    }

    public Post(String name, String description, UUID profileId) {
        this.name = name;
        this.description = description;
        this.profileId = profileId;
        
        this.idPost = UUID.randomUUID();
    }

    public UUID getIdPost() {
        return idPost;
    }

    public void setIdPost(UUID idPost) {
        this.idPost = idPost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "Post{" + "idPost=" + idPost + ", name=" + name + ", description=" + description + ", profileId=" + profileId + '}';
    }
    
    
    
}
