/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.socialmedia.domain.User;
import com.socialmedia.utils.AppConstants;
import com.socialmedia.utils.PasswordUtils;

public class Dao {
    
    // Methodos to handle user data
    public static User login (String username, String password) {
        
        // Load the users from the file
        List<User> users = getAllUsers();
        
        String hashPass = PasswordUtils.hashPassword(password);
        
        // Check if the user exists
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(hashPass)) {
                return u;
            }
        }
        // If the user does not exist, return null

        return null;
    }

    public static boolean register (String username, String password) {
        
        // Load the users from the file
        List<User> users = getAllUsers();
        // Check if the user already exists
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("El usuario ya existe");
                return false;
            }
        }
        // Add the user to the list
        users.add(new User(username, PasswordUtils.hashPassword(password)));
        // Save the users to the file
        if (saveUsers(users)) {
            System.out.println("Usuario registrado correctamente");
            return true;
        } else {
            System.out.println("Error al registrar el usuario");
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<User> getAllUsers() {
        // Load the users from the file 

        File file = new File(AppConstants.USERS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<User> users = (List<User>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (users == null || users.isEmpty()) {
                    users = new ArrayList<>();
                }

                return users;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + AppConstants.USERS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveUsers(List<User> users) {
        // Save the users to the file 

        File file = new File(AppConstants.USERS_FILE);

        // Save the users to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los usuarios", e);
            e.printStackTrace();
        }
        
        return false;
    }
    
}
