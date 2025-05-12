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
public class Reaccion implements Serializable {
    
    private UUID idReaction;
    private TipoDeReaccion type;

    public Reaccion() {
    }

    public Reaccion(UUID idReaction, TipoDeReaccion type) {
        this.idReaction = idReaction;
        this.type = type;
    }

    public Reaccion(TipoDeReaccion type) {
        this.type = type;
        
        this.idReaction = UUID.randomUUID();
    }

    public UUID getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(UUID idReaction) {
        this.idReaction = idReaction;
    }

    public TipoDeReaccion getType() {
        return type;
    }

    public void setType(TipoDeReaccion type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reaction{" + "idReaction=" + idReaction + ", type=" + type + '}';
    }
    
    
    
}
