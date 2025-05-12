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
public class Usuario implements Serializable {

    private UUID idUser;
    private String username;
    private String password;

    public Usuario() {
    }

    public Usuario(UUID idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }
    
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        
        // Create random UUID
        this.idUser = UUID.randomUUID();
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", username=" + username + ", password=" + password + '}';
    }
    
    
    
}
