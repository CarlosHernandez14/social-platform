/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.modelos;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author CruzZ
 */
public class Bloquear implements Serializable {
    
    private UUID idBlockedFriend;
    private UUID idWhoBlock;
    private UUID idBlockedProfile;

    public Bloquear() {
    }

    public Bloquear(UUID idBlockedFriend, UUID idWhoBlock, UUID idBlockedProfile) {
        this.idBlockedFriend = idBlockedFriend;
        this.idWhoBlock = idWhoBlock;
        this.idBlockedProfile = idBlockedProfile;
    }

    public Bloquear(UUID idWhoBlock, UUID idBlockedProfile) {
        this.idWhoBlock = idWhoBlock;
        this.idBlockedProfile = idBlockedProfile;
        
        this.idBlockedFriend = UUID.randomUUID();
    }

    public UUID getIdBlockedFriend() {
        return idBlockedFriend;
    }

    public void setIdBlockedFriend(UUID idBlockedFriend) {
        this.idBlockedFriend = idBlockedFriend;
    }

    public UUID getIdWhoBlock() {
        return idWhoBlock;
    }

    public void setIdWhoBlock(UUID idWhoBlock) {
        this.idWhoBlock = idWhoBlock;
    }

    public UUID getIdBlockedProfile() {
        return idBlockedProfile;
    }

    public void setIdBlockedProfile(UUID idBlockedProfile) {
        this.idBlockedProfile = idBlockedProfile;
    }

    @Override
    public String toString() {
        return "BlockedFriend{" + "idBlockedFriend=" + idBlockedFriend + ", idWhoBlock=" + idWhoBlock + ", idBlockedProfile=" + idBlockedProfile + '}';
    }
    
}
