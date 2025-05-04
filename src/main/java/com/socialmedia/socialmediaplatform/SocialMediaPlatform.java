/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.socialmedia.socialmediaplatform;

import com.formdev.flatlaf.FlatLightLaf;
import com.socialmedia.dao.Dao;
import com.socialmedia.views.LoginWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author carlo
 */
public class SocialMediaPlatform {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
//        Dao.getAllUsers().forEach(System.out::println);
        
//        Dao.register("charly@gmail.com", "charly");

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Error al cargar el look an feel");
        }
        
        
        new LoginWindow().setVisible(true);
        
        
    }
}
