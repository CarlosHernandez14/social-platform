/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.socialmedia.views;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.socialmedia.dao.Dao;
import com.socialmedia.domain.FriendRequest;
import com.socialmedia.domain.FriendShip;
import com.socialmedia.domain.Image;
import com.socialmedia.domain.Post;
import com.socialmedia.domain.Profile;
import com.socialmedia.domain.User;
import com.socialmedia.utils.WrapLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HomeWindow extends javax.swing.JFrame {

    private User userLogged;
    private Profile profileLog;
    
    /**
     * Creates new form HomeWindow
     */
    public HomeWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public HomeWindow(User user) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.userLogged = user;
        
        // Set layouts
        WrapLayout wrapLayout = new WrapLayout();
        wrapLayout.setVgap(30);
        
        this.containerHome.setLayout(wrapLayout);
        this.containerMyPosts.setLayout(wrapLayout);
        
        this.containerPersonas.setLayout(new WrapLayout(FlowLayout.CENTER, 0, 10));
        
        this.containerFriends.setLayout(new WrapLayout(FlowLayout.CENTER, 0, 10));
        
        this.containerFriendReq.setLayout(new WrapLayout(FlowLayout.CENTER, 0, 10));

        
        initData();
    }
    
    private void initData() {
        // Load the user profile
        this.profileLog = Dao.getProfileByUserId(this.userLogged.getIdUser().toString());
        
        initHomeData();
        initFriends();
        initFriendRequests();
        
        
        
        this.buttonProfile.setText(this.profileLog.getName() + " "  + this.profileLog.getLastName());
        
    }
    
    public void initHomeData() {
        
        this.containerHome.removeAll();
        
        List<Post> allPosts = Dao.getAllPosts();
        
        if (allPosts == null || allPosts.isEmpty()) {
            JLabel label = new JLabel("Aun no existen publicaciones por mostrar :(");
            this.containerHome.add(label);
            this.containerHome.revalidate();
            this.containerHome.repaint();
            return;
        }
        
        for (Post post : allPosts) {
            // Not  show owned posts
            if(this.profileLog.getIdProfile().toString().equals(post.getProfileId().toString()))
                continue;
            if (Dao.isBlockedFriend(this.profileLog.getIdProfile().toString(), post.getProfileId().toString()))
                continue;
            
            if (Dao.isBlockedFriend(post.getProfileId().toString(), this.profileLog.getIdProfile().toString()))
                continue;
            
            
            PanelPost singlePost = new PanelPost(post, this.profileLog, this);
            this.containerHome.add(singlePost);
        }
        
        this.containerHome.revalidate();
        this.containerHome.repaint();
        
    }
    
    public void initMyPostsData() {
        
        
        this.containerMyPosts.removeAll();
        
        // Get the posts for the profile
        List<Post> userPosts = Dao.getPostsByProfileId(this.profileLog.getIdProfile().toString());
        
        initProfilePic();
        
        // If theres no posts
        if (userPosts == null || userPosts.isEmpty()) {
            JLabel label = new JLabel("Aun no tienes publicaciones por mostrar :(");
            this.containerMyPosts.add(label);
            this.containerMyPosts.revalidate();
            this.containerMyPosts.repaint();
            return;
        }
        
        for (Post userPost : userPosts) {
            PanelPost post = new PanelPost(userPost, this.profileLog, this);
            this.containerMyPosts.add(post);
        }
        
        this.containerMyPosts.revalidate();
        this.containerMyPosts.repaint();
        
    }
    
    private void initProfilePic() {
        // Load image data
        // Load the profile image if exists
        // Get the profile image if exists
        if (this.profileLog.getProfileImageId() != null) {
            Image image = Dao.getImageById(this.profileLog.getProfileImageId().toString());
            
            File foto = new File("images/" + image.getImage_path());
            
            try {
                byte[] fotoBytes = Files.readAllBytes(foto.toPath());

                this.panelImageProfilePic.setIcon(new ImageIcon(fotoBytes));

                this.panelImageProfilePic.revalidate();
                this.panelImageProfilePic.repaint();

            } catch (IOException ex) {
                System.out.println("Error al obtener la imagen del arhivo");
            }
        }
    }
    
    public void initPersonsData() {
        
        this.containerPersonas.removeAll();
        
        List<Profile> persons = Dao.getAllProfiles();
        
        // If theres no profiles
        if (persons == null || persons.isEmpty()) {
            JLabel label = new JLabel("No tienes amigos aun :(");
            this.containerPersonas.add(label);
            this.containerPersonas.revalidate();
            this.containerPersonas.repaint();
            return;
        }
        
        for (Profile person : persons) {
            
            if (person.getIdProfile().toString().equals(this.profileLog.getIdProfile().toString()))
                continue;
            
            PanelPerson panelPerson = new PanelPerson(person, profileLog, this);
            this.containerPersonas.add(panelPerson);
        }
        
        this.containerPersonas.revalidate();
        this.containerPersonas.repaint();
        
    }
    
    public void initFriends() {
        
        this.containerFriends.removeAll();
        
        List<FriendShip> friends = Dao.getFriendsByUserId(this.profileLog.getIdProfile().toString());
        
        
        // If theres no requests
        if (friends == null || friends.isEmpty()) {
            JLabel label = new JLabel("No tienes amigos aun :(");
            this.containerFriends.add(label);
            this.containerFriends.revalidate();
            this.containerFriends.repaint();
            return;
        }
        
        for (FriendShip friend : friends) {
            PanelFriendShip panelFriend = new PanelFriendShip(friend, this.profileLog, this);
            this.containerFriends.add(panelFriend);
        }
        
        this.containerFriends.revalidate();
        this.containerFriends.repaint();
        
    }
    
    public void initFriendRequests() {
        
        this.containerFriendReq.removeAll();
        
        List<FriendRequest> friendRequests = Dao.getFriendRequestsReceivedByUserId(this.profileLog.getIdProfile().toString());
        
        // If theres no requests
        if (friendRequests == null || friendRequests.isEmpty()) {
            JLabel label = new JLabel("No tienes solicitudes :(");
            this.containerFriendReq.add(label);
            this.containerFriendReq.revalidate();
            this.containerFriendReq.repaint();
            return;
        }
        
        for (FriendRequest friendRequest : friendRequests) {
            if (friendRequest.isIsReqAccepted() || friendRequest.isIsReqRejected()) 
                continue;
            
            PanelFriendReq panelFriendReq = new PanelFriendReq(friendRequest, this);
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

        popupProfile = new javax.swing.JPopupMenu();
        itemPerfil = new javax.swing.JMenuItem();
        itemCerrarSesion = new javax.swing.JMenuItem();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        panelNavbar = new javax.swing.JPanel();
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        btnHome = new javax.swing.JButton();
        btnMyPost = new javax.swing.JButton();
        buttonProfile = new javax.swing.JButton();
        btnPeople = new javax.swing.JButton();
        scrollSidebar = new javax.swing.JScrollPane();
        containerFriends = new javax.swing.JPanel();
        tabbedPaneBody = new javax.swing.JTabbedPane();
        scrollHome = new javax.swing.JScrollPane();
        containerHome = new javax.swing.JPanel();
        panelMyPosts = new javax.swing.JPanel();
        scrollMyPosts = new javax.swing.JScrollPane();
        containerMyPosts = new javax.swing.JPanel();
        panelImageProfilePic = new PanelImageRedondeado();
        containerInput = new RoundedPanel(20);
        fieldAddPost = new javax.swing.JTextField();
        scrollPersonas = new javax.swing.JScrollPane();
        containerPersonas = new javax.swing.JPanel();
        scrollFriendReq = new javax.swing.JScrollPane();
        containerFriendReq = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        popupProfile.setBackground(new java.awt.Color(255, 255, 255));

        itemPerfil.setBackground(new java.awt.Color(255, 255, 255));
        itemPerfil.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        itemPerfil.setForeground(new java.awt.Color(102, 102, 102));
        itemPerfil.setText("Editar Perfil");
        itemPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPerfilActionPerformed(evt);
            }
        });
        popupProfile.add(itemPerfil);

        itemCerrarSesion.setBackground(new java.awt.Color(255, 255, 255));
        itemCerrarSesion.setForeground(new java.awt.Color(102, 102, 102));
        itemCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/iconlogout.png"))); // NOI18N
        itemCerrarSesion.setText("Cerrar sesión");
        itemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarSesionActionPerformed(evt);
            }
        });
        popupProfile.add(itemCerrarSesion);

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelNavbar.setBackground(new java.awt.Color(255, 255, 255));
        panelNavbar.setBorder(new org.edisoncor.gui.util.DropShadowBorder());

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnHome.setBackground(new java.awt.Color(255, 255, 255));
        btnHome.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnHome.setForeground(new java.awt.Color(102, 102, 102));
        btnHome.setText("Inicio");
        btnHome.setBorder(new FlatButtonBorder());
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
        btnMyPost.setText("Mis publicaciones");
        btnMyPost.setBorder(new FlatButtonBorder());
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
        buttonProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon-profile.png"))); // NOI18N
        buttonProfile.setText("Juan Perez");
        buttonProfile.setBorder(new FlatButtonBorder());
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
        btnPeople.setText("Personas");
        btnPeople.setBorder(new FlatButtonBorder());
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

        javax.swing.GroupLayout panelNavbarLayout = new javax.swing.GroupLayout(panelNavbar);
        panelNavbar.setLayout(panelNavbarLayout);
        panelNavbarLayout.setHorizontalGroup(
            panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNavbarLayout.createSequentialGroup()
                .addComponent(panelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMyPost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPeople)
                .addGap(123, 123, 123)
                .addComponent(buttonProfile)
                .addGap(17, 17, 17))
        );
        panelNavbarLayout.setVerticalGroup(
            panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNavbarLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panelNavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMyPost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPeople, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel1.add(panelNavbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        scrollSidebar.setBackground(new java.awt.Color(255, 255, 255));
        scrollSidebar.setBorder(null);
        scrollSidebar.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerFriends.setBackground(new java.awt.Color(244, 244, 244));
        containerFriends.setMinimumSize(new java.awt.Dimension(239, 497));

        javax.swing.GroupLayout containerFriendsLayout = new javax.swing.GroupLayout(containerFriends);
        containerFriends.setLayout(containerFriendsLayout);
        containerFriendsLayout.setHorizontalGroup(
            containerFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );
        containerFriendsLayout.setVerticalGroup(
            containerFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        scrollSidebar.setViewportView(containerFriends);

        jPanel1.add(scrollSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 131, -1, 460));

        scrollHome.setBorder(null);

        containerHome.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout containerHomeLayout = new javax.swing.GroupLayout(containerHome);
        containerHome.setLayout(containerHomeLayout);
        containerHomeLayout.setHorizontalGroup(
            containerHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        containerHomeLayout.setVerticalGroup(
            containerHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );

        scrollHome.setViewportView(containerHome);

        tabbedPaneBody.addTab("Home", scrollHome);

        panelMyPosts.setBackground(new java.awt.Color(244, 244, 244));

        scrollMyPosts.setBackground(new java.awt.Color(255, 255, 255));
        scrollMyPosts.setBorder(null);
        scrollMyPosts.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerMyPosts.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout containerMyPostsLayout = new javax.swing.GroupLayout(containerMyPosts);
        containerMyPosts.setLayout(containerMyPostsLayout);
        containerMyPostsLayout.setHorizontalGroup(
            containerMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        containerMyPostsLayout.setVerticalGroup(
            containerMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        scrollMyPosts.setViewportView(containerMyPosts);

        panelImageProfilePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/DefaultPerson.jpg"))); // NOI18N

        javax.swing.GroupLayout panelImageProfilePicLayout = new javax.swing.GroupLayout(panelImageProfilePic);
        panelImageProfilePic.setLayout(panelImageProfilePicLayout);
        panelImageProfilePicLayout.setHorizontalGroup(
            panelImageProfilePicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        panelImageProfilePicLayout.setVerticalGroup(
            panelImageProfilePicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        containerInput.setBackground(new java.awt.Color(255, 255, 255));
        containerInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        fieldAddPost.setEditable(false);
        fieldAddPost.setBackground(new java.awt.Color(255, 255, 255));
        fieldAddPost.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fieldAddPost.setForeground(new java.awt.Color(102, 102, 102));
        fieldAddPost.setText("¿En que piensas?, ¡Publicalo!");
        fieldAddPost.setBorder(null);
        fieldAddPost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fieldAddPost.setEnabled(false);
        fieldAddPost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fieldAddPostMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout containerInputLayout = new javax.swing.GroupLayout(containerInput);
        containerInput.setLayout(containerInputLayout);
        containerInputLayout.setHorizontalGroup(
            containerInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldAddPost)
                .addContainerGap())
        );
        containerInputLayout.setVerticalGroup(
            containerInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldAddPost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelMyPostsLayout = new javax.swing.GroupLayout(panelMyPosts);
        panelMyPosts.setLayout(panelMyPostsLayout);
        panelMyPostsLayout.setHorizontalGroup(
            panelMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMyPostsLayout.createSequentialGroup()
                .addComponent(scrollMyPosts, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelMyPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelImageProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(containerInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMyPostsLayout.setVerticalGroup(
            panelMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMyPostsLayout.createSequentialGroup()
                .addGroup(panelMyPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelMyPostsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelImageProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMyPostsLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(containerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollMyPosts, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPaneBody.addTab("tab4", panelMyPosts);

        scrollPersonas.setBorder(null);

        containerPersonas.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout containerPersonasLayout = new javax.swing.GroupLayout(containerPersonas);
        containerPersonas.setLayout(containerPersonasLayout);
        containerPersonasLayout.setHorizontalGroup(
            containerPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        containerPersonasLayout.setVerticalGroup(
            containerPersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );

        scrollPersonas.setViewportView(containerPersonas);

        tabbedPaneBody.addTab("Home", scrollPersonas);

        jPanel1.add(tabbedPaneBody, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 510, 530));

        scrollFriendReq.setBorder(null);
        scrollFriendReq.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerFriendReq.setBackground(new java.awt.Color(244, 244, 244));
        containerFriendReq.setMinimumSize(new java.awt.Dimension(250, 490));

        javax.swing.GroupLayout containerFriendReqLayout = new javax.swing.GroupLayout(containerFriendReq);
        containerFriendReq.setLayout(containerFriendReqLayout);
        containerFriendReqLayout.setHorizontalGroup(
            containerFriendReqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        containerFriendReqLayout.setVerticalGroup(
            containerFriendReqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        scrollFriendReq.setViewportView(containerFriendReq);

        jPanel1.add(scrollFriendReq, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 260, 460));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Amistades");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 240, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Solicitudes de  amistad");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 100, 230, 30));

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
        this.popupProfile.show(this.buttonProfile, 0, this.buttonProfile.getHeight());
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
        new AddPostWindow(this.profileLog, this).setVisible(true);
    }//GEN-LAST:event_fieldAddPostMouseClicked

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new LoginWindow().setVisible(true);
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void itemPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPerfilActionPerformed
        // TODO add your handling code here:
        new EditProfileWindow(this.profileLog).setVisible(true);
    }//GEN-LAST:event_itemPerfilActionPerformed

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
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemPerfil;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelImage panelImageProfilePic;
    private javax.swing.JPanel panelMyPosts;
    private javax.swing.JPanel panelNavbar;
    private javax.swing.JPopupMenu popupProfile;
    private javax.swing.JScrollPane scrollFriendReq;
    private javax.swing.JScrollPane scrollHome;
    private javax.swing.JScrollPane scrollMyPosts;
    private javax.swing.JScrollPane scrollPersonas;
    private javax.swing.JScrollPane scrollSidebar;
    private javax.swing.JTabbedPane tabbedPaneBody;
    // End of variables declaration//GEN-END:variables
}
