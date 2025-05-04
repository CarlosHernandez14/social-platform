/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.domain;

import java.io.Serializable;
import java.util.UUID;

public class Profile implements Serializable {

    private UUID idProfile;
    private String name;
    private String lastName; 
    private String phoneNumber; 
    
    // Foreign keys
    private UUID userId; 
    private UUID profileImageId; 

    public Profile() {
    }

    public Profile(UUID idProfile, String name, String lastName, String phoneNumber, UUID userId, UUID profileImageId) {
        this.idProfile = idProfile;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.profileImageId = profileImageId;
    }

    public Profile(String name, String lastName, String phoneNumber, UUID userId, UUID profileImageId) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.profileImageId = profileImageId;
        
        this.idProfile = UUID.randomUUID();
    }

    public UUID getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(UUID idProfile) {
        this.idProfile = idProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(UUID profileImageId) {
        this.profileImageId = profileImageId;
    }

    @Override
    public String toString() {
        return "Profile{" + "idProfile=" + idProfile + ", name=" + name + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", userId=" + userId + ", profileImageId=" + profileImageId + '}';
    }

    
}
