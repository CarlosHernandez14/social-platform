/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class FriendShip implements Serializable {

    private UUID idFriendShip;
    
    // Foreign key relations
    private UUID profileId;
    private UUID friendId;
    private Date createdAt;

    public FriendShip() {
    }

    public FriendShip(UUID idFriendShip, UUID profileId, UUID friendId, Date createdAt) {
        this.idFriendShip = idFriendShip;
        this.profileId = profileId;
        this.friendId = friendId;
        this.createdAt = createdAt;
    }

    public FriendShip(UUID profileId, UUID friendId, Date createdAt) {
        this.profileId = profileId;
        this.friendId = friendId;
        this.createdAt = createdAt;
        
        this.idFriendShip = UUID.randomUUID();
    }

    public UUID getIdFriendShip() {
        return idFriendShip;
    }

    public void setIdFriendShip(UUID idFriendShip) {
        this.idFriendShip = idFriendShip;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FriendShip{" + "idFriendShip=" + idFriendShip + ", profileId=" + profileId + ", friendId=" + friendId + ", createdAt=" + createdAt + '}';
    }

    
    
}
