/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.domain;

import java.io.Serializable;
import java.util.UUID;

public class Reaction implements Serializable {
    
    private UUID idReaction;
    private ReactionType type;

    public Reaction() {
    }

    public Reaction(UUID idReaction, ReactionType type) {
        this.idReaction = idReaction;
        this.type = type;
    }

    public Reaction(ReactionType type) {
        this.type = type;
        
        this.idReaction = UUID.randomUUID();
    }

    public UUID getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(UUID idReaction) {
        this.idReaction = idReaction;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reaction{" + "idReaction=" + idReaction + ", type=" + type + '}';
    }
    
    
    
}
