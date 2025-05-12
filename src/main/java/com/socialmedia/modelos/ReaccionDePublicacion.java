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
public class ReaccionDePublicacion implements Serializable {
    
    private UUID idPostReaction;
    private UUID postId; 
    private UUID reactionId;

    public ReaccionDePublicacion() {
    }

    public ReaccionDePublicacion(UUID idPostReaction, UUID postId, UUID reactionId) {
        this.idPostReaction = idPostReaction;
        this.postId = postId;
        this.reactionId = reactionId;
    }

    public ReaccionDePublicacion(UUID postId, UUID reactionId) {
        this.postId = postId;
        this.reactionId = reactionId;
        
        this.idPostReaction = UUID.randomUUID();
    }

    public UUID getIdPostReaction() {
        return idPostReaction;
    }

    public void setIdPostReaction(UUID idPostReaction) {
        this.idPostReaction = idPostReaction;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getReactionId() {
        return reactionId;
    }

    public void setReactionId(UUID reactionId) {
        this.reactionId = reactionId;
    }

    @Override
    public String toString() {
        return "PostReaction{" + "idPostReaction=" + idPostReaction + ", postId=" + postId + ", reactionId=" + reactionId + '}';
    }
    
    
}
