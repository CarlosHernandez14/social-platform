/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author CruzZ
 */
public class Solicitud implements Serializable {

    
    private UUID idFriendRequest;
    private UUID profileReqId;
    private UUID profielReceivedId;
    private boolean isReqAccepted; 
    private boolean isReqRejected;
    private String message;
    
    private Date createdAt;

    public Solicitud() {
    }

    public Solicitud(UUID idFriendRequest, UUID profileReqId, UUID profielReceivedId, boolean isReqAccepted, boolean isReqRejected, Date createdAt, String message) {
        this.idFriendRequest = idFriendRequest;
        this.profileReqId = profileReqId;
        this.profielReceivedId = profielReceivedId;
        this.isReqAccepted = isReqAccepted;
        this.isReqRejected = isReqRejected;
        this.createdAt = createdAt;
        this.message = message;
    }

    public Solicitud(UUID profileReqId, UUID profielReceivedId, boolean isReqAccepted, boolean isReqRejected, Date createdAt, String message) {
        this.profileReqId = profileReqId;
        this.profielReceivedId = profielReceivedId;
        this.isReqAccepted = isReqAccepted;
        this.isReqRejected = isReqRejected;
        this.createdAt = createdAt;
        this.message = message;
        
        this.idFriendRequest = UUID.randomUUID();
    }

    public UUID getIdFriendRequest() {
        return idFriendRequest;
    }

    public void setIdFriendRequest(UUID idFriendRequest) {
        this.idFriendRequest = idFriendRequest;
    }

    public UUID getProfileReqId() {
        return profileReqId;
    }

    public void setProfileReqId(UUID profileReqId) {
        this.profileReqId = profileReqId;
    }

    public UUID getProfielReceivedId() {
        return profielReceivedId;
    }

    public void setProfielReceivedId(UUID profielReceivedId) {
        this.profielReceivedId = profielReceivedId;
    }

    public boolean isIsReqAccepted() {
        return isReqAccepted;
    }

    public void setIsReqAccepted(boolean isReqAccepted) {
        this.isReqAccepted = isReqAccepted;
    }

    public boolean isIsReqRejected() {
        return isReqRejected;
    }

    public void setIsReqRejected(boolean isReqRejected) {
        this.isReqRejected = isReqRejected;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FriendRequest{" + "idFriendRequest=" + idFriendRequest + ", profileReqId=" + profileReqId + ", profielReceivedId=" + profielReceivedId + ", isReqAccepted=" + isReqAccepted + ", isReqRejected=" + isReqRejected + ", message=" + message + ", createdAt=" + createdAt + '}';
    }
    
    
}
