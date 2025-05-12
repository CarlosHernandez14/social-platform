/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.socialmedia.diseno;

import com.socialmedia.manejoarchivos.ManejoArchivos;
import com.socialmedia.modelos.Solicitud;
import com.socialmedia.modelos.Amistad;
import com.socialmedia.modelos.Foto;
import com.socialmedia.modelos.Publicacion;
import com.socialmedia.modelos.Perfil;
import com.socialmedia.modelos.Usuario;
import com.socialmedia.manejoarchivos.PlantillaColumnas;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author CruzZ
 */
public class InicioPantalla extends javax.swing.JFrame {

    private Usuario userLogged;
    private Perfil profileLog;
    
    /**
     * Creates new form HomeWindow
     */
    public InicioPantalla() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public InicioPantalla(Usuario user) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.userLogged = user;
        
        // Set layouts
        PlantillaColumnas wrapLayout = new PlantillaColumnas();
        wrapLayout.setVgap(30);
        
        this.containerHome.setLayout(wrapLayout);
        this.containerMyPosts.setLayout(wrapLayout);
        
        this.containerPersonas.setLayout(new PlantillaColumnas(FlowLayout.CENTER, 0, 10));
        
        this.containerFriends.setLayout(new PlantillaColumnas(FlowLayout.CENTER, 0, 10));
        
        this.containerFriendReq.setLayout(new PlantillaColumnas(FlowLayout.CENTER, 0, 10));

        
        initData();
    }
    
    private void initData() {
        // Load the user profile
        this.profileLog = ManejoArchivos.getProfileByUserId(this.userLogged.getIdUser().toString());
        
        initHomeData();
        initFriends();
        initFriendRequests();
        
        
        
        this.buttonProfile.setText(this.profileLog.getName() + " "  + this.profileLog.getLastName());
        
    }
    
    public void initHomeData() {
        
        this.containerHome.removeAll();
        
        List<Publicacion> allPosts = ManejoArchivos.getAllPosts();
        
        if (allPosts == null || allPosts.isEmpty()) {
            JLabel label = new JLabel("Aun no existen publicaciones por mostrar :(");
            this.containerHome.add(label);
            this.containerHome.revalidate();
            this.containerHome.repaint();
            return;
        }
        
        for (Publicacion post : allPosts) {
            // Not  show owned posts
            if(this.profileLog.getIdProfile().toString().equals(post.getProfileId().toString()))
                continue;
            if (ManejoArchivos.isBlockedFriend(this.profileLog.getIdProfile().toString(), post.getProfileId().toString()))
                continue;
            
            if (ManejoArchivos.isBlockedFriend(post.getProfileId().toString(), this.profileLog.getIdProfile().toString()))
                continue;
            
            
            PanelPublicacion singlePost = new PanelPublicacion(post, this.profileLog, this);
            this.containerHome.add(singlePost);
        }
        
        this.containerHome.revalidate();
        this.containerHome.repaint();
        
    }
    
    public void initMyPostsData() {
        
        
        this.containerMyPosts.removeAll();
        
        // Get the posts for the profile
        List<Publicacion> userPosts = ManejoArchivos.getPostsByProfileId(this.profileLog.getIdProfile().toString());
        
        initProfilePic();
        
        // If theres no posts
        if (userPosts == null || userPosts.isEmpty()) {
            JLabel label = new JLabel("Aun no tienes publicaciones por mostrar :(");
            this.containerMyPosts.add(label);
            this.containerMyPosts.revalidate();
            this.containerMyPosts.repaint();
            return;
        }
        
        for (Publicacion userPost : userPosts) {
            PanelPublicacion post = new PanelPublicacion(userPost, this.profileLog, this);
            this.containerMyPosts.add(post);
        }
        
        this.containerMyPosts.revalidate();
        this.containerMyPosts.repaint();
        
    }
    
    private void initProfilePic() {
        
    }
    
    public void initPersonsData() {
        
        this.containerPersonas.removeAll();
        
        List<Perfil> persons = ManejoArchivos.getAllProfiles();
        
        // If theres no profiles
        if (persons == null || persons.isEmpty()) {
            JLabel label = new JLabel("No tienes amigos aun :(");
            this.containerPersonas.add(label);
            this.containerPersonas.revalidate();
            this.containerPersonas.repaint();
            return;
        }
        
        for (Perfil person : persons) {
            
            if (person.getIdProfile().toString().equals(this.profileLog.getIdProfile().toString()))
                continue;
            
            PanelPersonas panelPerson = new PanelPersonas(person, profileLog, this);
            this.containerPersonas.add(panelPerson);
        }
        
        this.containerPersonas.revalidate();
        this.containerPersonas.repaint();
        
    }
    
    public void initFriends() {
        
        this.containerFriends.removeAll();
        
        List<Amistad> friends = ManejoArchivos.getFriendsByUserId(this.profileLog.getIdProfile().toString());
        
        
        // If theres no requests
        if (friends == null || friends.isEmpty()) {
            JLabel label = new JLabel("No tienes amigos aun :(");
            this.containerFriends.add(label);
            this.containerFriends.revalidate();
            this.containerFriends.repaint();
            return;
        }
        
        for (Amistad friend : friends) {
            PanelAmistad panelFriend = new PanelAmistad(friend, this.profileLog, this);
            this.containerFriends.add(panelFriend);
        }
        
        this.containerFriends.revalidate();
        this.containerFriends.repaint();
        
    }
    
    public void initFriendRequests() {
        
        this.containerFriendReq.removeAll();
        
        List<Solicitud> friendRequests = ManejoArchivos.getFriendRequestsReceivedByUserId(this.profileLog.getIdProfile().toString());
        
        // If theres no requests
        if (friendRequests == null || friendRequests.isEmpty()) {
            JLabel label = new JLabel("No tienes solicitudes :(");
            this.containerFriendReq.add(label);
            this.containerFriendReq.revalidate();
            this.containerFriendReq.repaint();
            return;
        }
        
        for (Solicitud friendRequest : friendRequests) {
            if (friendRequest.isIsReqAccepted() || friendRequest.isIsReqRejected()) 
                continue;
            
            PanelSolicitud panelFriendReq = new PanelSolicitud(friendRequest, this);
            this.containerFriendReq.add(panelFriendReq);
        }
        
        this.containerFriendReq.revalidate();
        this.containerFriendReq.repaint();
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        panelNavbar = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnMyPost = new javax.swing.JButton();
        buttonProfile = new javax.swing.JButton();
        btnPeople = new javax.swing.JButton();
        botonCerrarSesion = new javax.swing.JButton();
        containerInput = new RoundedPanel(20);
        fieldAddPost = new javax.swing.JTextField();
        scrollSidebar = new javax.swing.JScrollPane();
        containerFriends = new javax.swing.JPanel();
        tabbedPaneBody = new javax.swing.JTabbedPane();
        scrollHome = new javax.swing.JScrollPane();
        containerHome = new javax.swing.JPanel();
        panelMyPosts = new javax.swing.JPanel();
        scrollMyPosts = new javax.swing.JScrollPane();
        containerMyPosts = new javax.swing.JPanel();
        scrollPersonas = new javax.swing.JScrollPane();
        containerPersonas = new javax.swing.JPanel();
        scrollFriendReq = new javax.swing.JScrollPane();
        containerFriendReq = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelNavbar.setBackground(new java.awt.Color(0, 0, 0));

        btnHome.setBackground(new java.awt.Color(255, 255, 255));
        btnHome.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnHome.setForeground(new java.awt.Color(102, 102, 102));
        btnHome.setText("Inicio");
        btnHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnHome.setBorderPainted(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnMyPost.setBackground(new java.awt.Color(255, 255, 255));
        btnMyPost.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnMyPost.setForeground(new java.awt.Color(102, 102, 102));
        btnMyPost.setText("Publicaciones");
        btnMyPost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnMyPost.setBorderPainted(false);
        btnMyPost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMyPost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMyPostMouseClicked(evt);
            }
        });
        btnMyPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyPostActionPerformed(evt);
            }
        });

        buttonProfile.setBackground(new java.awt.Color(255, 255, 255));
        buttonProfile.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        buttonProfile.setForeground(new java.awt.Color(0, 102, 153));
        buttonProfile.setText("Juan Perez");
        buttonProfile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonProfile.setBorderPainted(false);
        buttonProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonProfile.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        buttonProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProfileActionPerformed(evt);
            }
        });

        btnPeople.setBackground(new java.awt.Color(255, 255, 255));
        btnPeople.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnPeople.setForeground(new java.awt.Color(102, 102, 102));
        btnPeople.setText("Buscar Personas");
        btnPeople.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPeople.setBorderPainted(false);
        btnPeople.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPeople.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPeopleMouseClicked(evt);
            }
        });
        btnPeople.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeopleActionPerformed(evt);
            }
        });

        botonCerrarSesion.setBackground(new java.awt.Color(255, 255, 255));
        botonCerrarSesion.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        botonCerrarSesion.setForeground(new java.awt.Color(0, 102, 153));
        botonCerrarSesion.setText("Cerrar sesion");
        botonCerrarSesion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        botonCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonCerrarSesion.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        botonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarSesionActionPerformed(evt);
            }
        });

        containerInput.setBackground(new java.awt.Color(0, 102, 102));
        containerInput.setForeground(new java.awt.Color(255, 255, 255));
        containerInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        fieldAddPost.setEditable(false);
        fieldAddPost.setBackground(new java.awt.Color(0, 102, 102));
        fieldAddPost.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fieldAddPost.setForeground(new java.awt.Color(255, 255, 255));
        fieldAddPost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldAddPost.setText("AGREGAR PUBLICACION");
        fieldAddPost.setBorder(null);
        fieldAddPost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fieldAddPost.setEnabled(false);
        fieldAddPost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fieldAddPostMouseClicked(evt);
            }
        });
        fieldAddPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldAddPostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containerInputLayout = new javax.swing.GroupLayout(containerInput);
        containerInput.setLayout(containerInputLayout);
        containerInputLayout.setHorizontalGroup(
            containerInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldAddPost, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        containerInputLayout.setVerticalGroup(
            containerInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerInputLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(fieldAddPost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelNavbarLayout = new javax.swing.GroupLayout(panelNavbar);
        panelNavbar.setLayout(panelNavbarLayout);
        panelNavbarLayout.setHorizontalGroup(
            panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNavbarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyPost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPeople)
                .addGap(28, 28, 28)
                .addComponent(containerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(botonCerrarSesion)
                .addGap(44, 44, 44)
                .addComponent(buttonProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelNavbarLayout.setVerticalGroup(
            panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNavbarLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelNavbarLayout.createSequentialGroup()
                        .addComponent(containerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNavbarLayout.createSequentialGroup()
                        .addGroup(panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMyPost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPeople, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
        );

        jPanel1.add(panelNavbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        scrollSidebar.setBackground(new java.awt.Color(255, 255, 255));
        scrollSidebar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollSidebar.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerFriends.setBackground(new java.awt.Color(0, 0, 0));
        containerFriends.setMinimumSize(new java.awt.Dimension(239, 497));

        javax.swing.GroupLayout containerFriendsLayout = new javax.swing.GroupLayout(containerFriends);
        containerFriends.setLayout(containerFriendsLayout);
        containerFriendsLayout.setHorizontalGroup(
            containerFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        containerFriendsLayout.setVerticalGroup(
            containerFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        scrollSidebar.setViewportView(containerFriends);

        jPanel1.add(scrollSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 131, 270, 190));

        scrollHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        containerHome.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout containerHomeLayout = new javax.swing.GroupLayout(containerHome);
        containerHome.setLayout(containerHomeLayout);
        containerHomeLayout.setHorizontalGroup(
            containerHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 758, Short.MAX_VALUE)
        );
        containerHomeLayout.setVerticalGroup(
            containerHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );

        scrollHome.setViewportView(containerHome);

        tabbedPaneBody.addTab("Home", scrollHome);

        panelMyPosts.setBackground(new java.awt.Color(0, 0, 0));

        scrollMyPosts.setBackground(new java.awt.Color(255, 255, 255));
        scrollMyPosts.setBorder(null);
        scrollMyPosts.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerMyPosts.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout containerMyPostsLayout = new javax.swing.GroupLayout(containerMyPosts);
        containerMyPosts.setLayout(containerMyPostsLayout);
        containerMyPostsLayout.setHorizontalGroup(
            containerMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        containerMyPostsLayout.setVerticalGroup(
            containerMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        scrollMyPosts.setViewportView(containerMyPosts);

        javax.swing.GroupLayout panelMyPostsLayout = new javax.swing.GroupLayout(panelMyPosts);
        panelMyPosts.setLayout(panelMyPostsLayout);
        panelMyPostsLayout.setHorizontalGroup(
            panelMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollMyPosts, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        panelMyPostsLayout.setVerticalGroup(
            panelMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMyPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollMyPosts, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
        );

        tabbedPaneBody.addTab("tab4", panelMyPosts);

        scrollPersonas.setBorder(null);

        containerPersonas.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout containerPersonasLayout = new javax.swing.GroupLayout(containerPersonas);
        containerPersonas.setLayout(containerPersonasLayout);
        containerPersonasLayout.setHorizontalGroup(
            containerPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
        containerPersonasLayout.setVerticalGroup(
            containerPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );

        scrollPersonas.setViewportView(containerPersonas);

        tabbedPaneBody.addTab("Home", scrollPersonas);

        jPanel1.add(tabbedPaneBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 730, 530));

        scrollFriendReq.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollFriendReq.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerFriendReq.setBackground(new java.awt.Color(0, 0, 0));
        containerFriendReq.setMinimumSize(new java.awt.Dimension(250, 490));

        javax.swing.GroupLayout containerFriendReqLayout = new javax.swing.GroupLayout(containerFriendReq);
        containerFriendReq.setLayout(containerFriendReqLayout);
        containerFriendReqLayout.setHorizontalGroup(
            containerFriendReqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        containerFriendReqLayout.setVerticalGroup(
            containerFriendReqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        scrollFriendReq.setViewportView(containerFriendReq);

        jPanel1.add(scrollFriendReq, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 270, 220));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Amistades");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 240, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Solicitudes de  amistad");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 230, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnMyPostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMyPostMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMyPostMouseClicked

    private void buttonProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonProfileActionPerformed

    private void btnPeopleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPeopleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeopleMouseClicked

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        this.initHomeData();
        
        this.tabbedPaneBody.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnMyPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyPostActionPerformed
        // TODO add your handling code here:
        
        this.initMyPostsData();
        
        this.tabbedPaneBody.setSelectedIndex(1);
        
    }//GEN-LAST:event_btnMyPostActionPerformed

    private void btnPeopleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeopleActionPerformed
        // TODO add your handling code here:
        this.initPersonsData();
        
        this.tabbedPaneBody.setSelectedIndex(2);
    }//GEN-LAST:event_btnPeopleActionPerformed

    private void fieldAddPostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldAddPostMouseClicked
        // TODO add your handling code here:
        new CrearPublicacion(this.profileLog, this).setVisible(true);
    }//GEN-LAST:event_fieldAddPostMouseClicked

    private void fieldAddPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldAddPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldAddPostActionPerformed

    private void botonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarSesionActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new InicioSesion().setVisible(true);
    }//GEN-LAST:event_botonCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InicioPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioPantalla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCerrarSesion;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnMyPost;
    private javax.swing.JButton btnPeople;
    private javax.swing.JButton buttonProfile;
    private javax.swing.JPanel containerFriendReq;
    private javax.swing.JPanel containerFriends;
    private javax.swing.JPanel containerHome;
    private javax.swing.JPanel containerInput;
    private javax.swing.JPanel containerMyPosts;
    private javax.swing.JPanel containerPersonas;
    private javax.swing.JTextField fieldAddPost;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPanel panelMyPosts;
    private javax.swing.JPanel panelNavbar;
    private javax.swing.JScrollPane scrollFriendReq;
    private javax.swing.JScrollPane scrollHome;
    private javax.swing.JScrollPane scrollMyPosts;
    private javax.swing.JScrollPane scrollPersonas;
    private javax.swing.JScrollPane scrollSidebar;
    private javax.swing.JTabbedPane tabbedPaneBody;
    // End of variables declaration//GEN-END:variables
}
