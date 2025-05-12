/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.socialmedia.manejoarchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.socialmedia.modelos.Bloquear;
import com.socialmedia.modelos.Solicitud;
import com.socialmedia.modelos.Amistad;
import com.socialmedia.modelos.Foto;
import com.socialmedia.modelos.Publicacion;
import com.socialmedia.modelos.ReaccionDePublicacion;
import com.socialmedia.modelos.Perfil;
import com.socialmedia.modelos.Reaccion;
import com.socialmedia.modelos.TipoDeReaccion;
import com.socialmedia.modelos.Usuario;
import java.util.Iterator;


/**
 *
 * @author CruzZ
 */
public class ManejoArchivos {
    
    // Methodos to handle user data
    public static Usuario login (String username, String password) {
        
        // Load the users from the file
        List<Usuario> users = getAllUsers();
        
        String hashPass = Contrasena.hashPassword(password);
        
        // Check if the user exists
        for (Usuario u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(hashPass)) {
                return u;
            }
        }
        // If the user does not exist, return null

        return null;
    }

    public static boolean register (String username, String email, String password) {
        
        // Load the users from the file
        List<Usuario> users = getAllUsers();
        // Check if the user already exists
        for (Usuario u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("El usuario ya existe");
                return false;
            }
        }
        // Add the user to the list
        Usuario newUser = new Usuario(email, Contrasena.hashPassword(password));
        users.add(newUser);
        // Save the users to the file
        if (saveUsers(users)) {
            // If the user was saved successfully register the profile of the user
            Perfil profile = new Perfil(username, username, null, newUser.getIdUser(), null);
            List<Perfil> profiles = getAllProfiles();
            profiles.add(profile);
            if (saveProfiles(profiles)) {
                System.out.println("Usuario registrado correctamente");
                return true;
            } else {
                System.out.println("Error al registrar el perfil del usuario");
                return false;
            }
        } else {
            System.out.println("Error al registrar el usuario");
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Usuario> getAllUsers() {
        // Load the users from the file 

        File file = new File(Constantes.USERS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Usuario> users = (List<Usuario>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + Constantes.USERS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveUsers(List<Usuario> users) {
        // Save the users to the file 

        File file = new File(Constantes.USERS_FILE);

        // Save the users to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los usuarios", e);
            e.printStackTrace();
        }
        
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE PROFILES DATA

    // Method to get all profiles
    @SuppressWarnings("unchecked")
    public static List<Perfil> getAllProfiles() {
        // Load the profiles from the file 

        File file = new File(Constantes.PROFILE_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Perfil> profiles = (List<Perfil>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (profiles == null || profiles.isEmpty()) {
                    profiles = new ArrayList<>();
                }

                return profiles;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.PROFILE_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveProfiles(List<Perfil> profiles) {
        // Save the profiles to the file 

        File file = new File(Constantes.PROFILE_FILE);

        // Save the profiles to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(profiles);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los perfiles", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to get a profile by userId
    public static Perfil getProfileByUserId(String userId) {
        // Load the profiles from the file 

        List<Perfil> profiles = getAllProfiles();

        // Check if the profile exists
        for (Perfil p : profiles) {
            if (p.getUserId().toString().equals(userId)) {
                return p;
            }
        }

        return null;
    }

    // Method to get a profile by id
    public static Perfil getProfileById(String profileId) {
        // Load the profiles from the file 

        List<Perfil> profiles = getAllProfiles();

        // Check if the profile exists
        for (Perfil p : profiles) {
            if (p.getIdProfile().toString().equals(profileId)) {
                return p;
            }
        }

        return null;
    }

    // Method to update a profile
    public static boolean updateProfile(String profileId, Perfil profile) {
        // Load the profiles from the file 

        List<Perfil> profiles = getAllProfiles();

        // Check if the profile exists
        for (Perfil p : profiles) {
            if (p.getIdProfile().toString().equals(profileId)) {
                // Update the profile
                p.setName(profile.getName());
                p.setLastName(profile.getLastName());
                p.setAddress(profile.getAddress());
                p.setPhoneNumber(profile.getPhoneNumber());
                p.setProfileImageId(profile.getProfileImageId());
                break;
            }
        }

        // Save the profiles to the file 
        return saveProfiles(profiles);
    }

    ////////////////////////////////////////////////////////////////////////
    
    // METHODS TO HANLDE POSTS DATA

    // Method to get all posts
    @SuppressWarnings("unchecked")
    public static List<Publicacion> getAllPosts() {
        // Load the posts from the file 

        File file = new File(Constantes.POSTS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Publicacion> posts = (List<Publicacion>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (posts == null || posts.isEmpty()) {
                    posts = new ArrayList<>();
                }

                return posts;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.POSTS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean savePosts(List<Publicacion> posts) {
        // Save the posts to the file 

        File file = new File(Constantes.POSTS_FILE);

        // Save the posts to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(posts);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los posts", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to get all the posts of a profile
    public static List<Publicacion> getPostsByProfileId(String profileId) {
        // Load the posts from the file 

        List<Publicacion> posts = getAllPosts();

        // Check if the profile exists
        List<Publicacion> profilePosts = new ArrayList<>();
        for (Publicacion p : posts) {
            if (p.getProfileId().toString().equals(profileId)) {
                profilePosts.add(p);
            }
        }

        return profilePosts;
    }

    // Method to save a post
    public static boolean savePost(Publicacion post) {
        // Load the posts from the file 

        List<Publicacion> posts = getAllPosts();

        // Add the post to the list
        posts.add(post);

        // Save the posts to the file 
        return savePosts(posts);
    }

    // Method to delete a post
    public static boolean deletePost(String postId) {
        // Load the posts from the file 

        List<Publicacion> posts = getAllPosts();

        // Check if the post exists
        Iterator<Publicacion> it = posts.iterator();
        while (it.hasNext()) {
            Publicacion p = it.next();
            if (p.getIdPost().toString().equals(postId)) {
                it.remove();  // elimina de manera segura
                break;
            }
        }
        
        // Save the posts to the file 
        if (savePosts(posts)) {
            // Delete the images of the post
            if (deleteImagesByPostId(postId)) {
                return true;
            } else {
                System.out.println("Error al eliminar las imagenes del post");
                return false;
            }
        } else {
            System.out.println("Error al eliminar el post");
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////////////

    // METHODS TO HANDLE IMAGES DATA
    // Method to get all images
    @SuppressWarnings("unchecked")
    public static List<Foto> getAllImages() {
        // Load the images from the file 

        File file = new File(Constantes.IMAGES_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Foto> images = (List<Foto>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (images == null || images.isEmpty()) {
                    images = new ArrayList<>();
                }

                return images;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.IMAGES_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveImages(List<Foto> images) {
        // Save the images to the file 

        File file = new File(Constantes.IMAGES_FILE);

        // Save the images to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(images);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las imagenes", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save an image
    public static boolean saveImage(Foto image) {
        // Load the images from the file 

        List<Foto> images = getAllImages();

        // Add the image to the list
        images.add(image);

        // Save the images to the file 
        return saveImages(images);
    }

    // Method to get an image by id
    public static Foto getImageById(String imageId) {
        // Load the images from the file 

        List<Foto> images = getAllImages();

        // Check if the image exists
        for (Foto i : images) {
            if (i.getIdImage().toString().equals(imageId)) {
                return i;
            }
        }

        return null;
    }

    // Method to get an image by postId
    public static List<Foto> getImagesByPostId(String postId) {
        // Load the images from the file 

        List<Foto> images = getAllImages();

        // Check if the image exists
        List<Foto> postImages = new ArrayList<>();
        for (Foto i : images) {
            if (i.getPostId() != null && i.getPostId().toString().equals(postId)) {
                postImages.add(i);
            }
        }

        return postImages;
    }

    // Delete all images of a post by postId
    public static boolean deleteImagesByPostId(String postId) {
        // Load the images from the file 

        List<Foto> images = getAllImages();

        // Check if the image exists
        Iterator<Foto> it = images.iterator();
        while (it.hasNext()) {
            Foto img = it.next();
            if (img.getPostId() != null
                && img.getPostId().toString().equals(postId)) {
                it.remove();  // elimina usando el Iterator
            }
        }


        // Save the images to the file 
        return saveImages(images);
    }

    //////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE REACTIONS DATA

    // Method to get all reactions
    @SuppressWarnings("unchecked")
    public static List<Reaccion> getAllReactions() {
        // Load the reactions from the file 

        File file = new File(Constantes.REACTIONS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Reaccion> reactions = (List<Reaccion>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (reactions == null || reactions.isEmpty()) {
                    reactions = new ArrayList<>();
                }

                return reactions;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.REACTIONS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveReactions(List<Reaccion> reactions) {
        // Save the reactions to the file 

        File file = new File(Constantes.REACTIONS_FILE);

        // Save the reactions to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(reactions);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las reacciones", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a reaction
    public static boolean saveReaction(Reaccion reaction) {
        // Load the reactions from the file 

        List<Reaccion> reactions = getAllReactions();

        // Add the reaction to the list
        reactions.add(reaction);

        // Save the reactions to the file 
        return saveReactions(reactions);
    }

    // Method to get a reaction by type (TipoDeReaccion (Enum))
    public static Reaccion getReactionByType(TipoDeReaccion type) {
        // Load the reactions from the file 

        List<Reaccion> reactions = getAllReactions();

        // Check if the reaction exists
        for (Reaccion r : reactions) {
            if (r.getType().equals(type)) {
                return r;
            }
        }

        return null;
    }

    // Method to save a reaction by type (TipoDeReaccion (Enum))
    public static boolean saveReactionByType(TipoDeReaccion type) {
        // Load the reactions from the file 

        List<Reaccion> reactions = getAllReactions();

        // Check if the reaction exists
        for (Reaccion r : reactions) {
            if (r.getType().equals(type)) {
                return false;
            }
        }
        
        // Add the reaction to the list
        Reaccion reaction = new Reaccion(type);
        reactions.add(reaction);

        // Save the reactions to the file 
        return saveReactions(reactions);
    }


    //////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE THE REACTIONS FOR POSTS DATA
    // Method to get all the reactions for a post
    @SuppressWarnings("unchecked")
    public static List<ReaccionDePublicacion> getAllPostReactions() {
        // Load the reactions from the file 

        File file = new File(Constantes.POST_REACTIONS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<ReaccionDePublicacion> postReactions = (List<ReaccionDePublicacion>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (postReactions == null || postReactions.isEmpty()) {
                    postReactions = new ArrayList<>();
                }

                return postReactions;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.POST_REACTIONS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean savePostReactions(List<ReaccionDePublicacion> postReactions) {
        // Save the reactions to the file 

        File file = new File(Constantes.POST_REACTIONS_FILE);

        // Save the reactions to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(postReactions);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las reacciones de los posts", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a reaction for a post
    public static boolean savePostReaction(ReaccionDePublicacion postReaction) {
        // Load the reactions from the file 

        List<ReaccionDePublicacion> postReactions = getAllPostReactions();

        // Add the reaction to the list
        postReactions.add(postReaction);

        // Save the reactions to the file 
        return savePostReactions(postReactions);
    }
    // Method to get all the reactions for a post by postId
    public static List<ReaccionDePublicacion> getPostReactionsByPostId(String postId) {
        // Load the reactions from the file 

        List<ReaccionDePublicacion> postReactions = getAllPostReactions();

        // Check if the reaction exists
        List<ReaccionDePublicacion> postReactionsList = new ArrayList<>();
        for (ReaccionDePublicacion pr : postReactions) {
            if (pr.getPostId().toString().equals(postId)) {
                postReactionsList.add(pr);
            }
        }

        return postReactionsList;
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE FRIENDS REQUESTS DATA

    // Method to get all the friends requests
    @SuppressWarnings("unchecked")
    public static List<Solicitud> getAllFriendRequests() {
        // Load the friends requests from the file 

        File file = new File(Constantes.FRIEND_REQ_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Solicitud> friendRequests = (List<Solicitud>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (friendRequests == null || friendRequests.isEmpty()) {
                    friendRequests = new ArrayList<>();
                }

                return friendRequests;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.FRIEND_REQ_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveFriendRequests(List<Solicitud> friendRequests) {
        // Save the friends requests to the file 

        File file = new File(Constantes.FRIEND_REQ_FILE);

        // Save the friends requests to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(friendRequests);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las solicitudes de amistad", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a friend request
    public static boolean saveFriendRequest(Solicitud friendRequest) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Add the friend request to the list
        friendRequests.add(friendRequest);

        // Save the friends requests to the file 
        return saveFriendRequests(friendRequests);
    }

    // Method to get all the friends requests by userId
    public static List<Solicitud> getFriendRequestsByUserId(String userId) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        List<Solicitud> userFriendRequests = new ArrayList<>();
        for (Solicitud fr : friendRequests) {
            if (fr.getProfileReqId().toString().equals(userId)) {
                userFriendRequests.add(fr);
            }
        }

        return userFriendRequests;
    }

    // Method to get all the friends requests received by userId (receivedId)
    public static List<Solicitud> getFriendRequestsReceivedByUserId(String userId) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        List<Solicitud> userFriendRequests = new ArrayList<>();
        for (Solicitud fr : friendRequests) {
            if (fr.getProfielReceivedId().toString().equals(userId)) {
                userFriendRequests.add(fr);
            }
        }

        return userFriendRequests;
    }

    // Method to check if a friend request is sent by a userId (profileReqId) to another userId (receivedId)
    public static boolean isFriendRequestSent(String profileReqId, String receivedId) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        for (Solicitud fr : friendRequests) {
            if (fr.getProfileReqId().toString().equals(profileReqId) && fr.getProfielReceivedId().toString().equals(receivedId)) {
                return true;
            }
        }

        return false;
    }

    // Method to update a friend request
    public static boolean updateFriendRequest(String friendReqId, Solicitud friendRequest) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        for (Solicitud fr : friendRequests) {
            if (fr.getIdFriendRequest().toString().equals(friendReqId)) {
                // Update the friend request
                fr.setIsReqAccepted(friendRequest.isIsReqAccepted());
                fr.setIsReqRejected(friendRequest.isIsReqAccepted());
                fr.setMessage(friendRequest.getMessage());
                break;
            }
        }

        // Save the friends requests to the file 
        return saveFriendRequests(friendRequests);
    }

    // Method to get if a friend request is rejected by a userId (profileReqId) to another userId (receivedId)
    public static boolean isFriendRequestRejected(String profileReqId, String receivedId) {
        // Load the friends requests from the file 

        List<Solicitud> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        for (Solicitud fr : friendRequests) {
            if (fr.getProfileReqId().toString().equals(profileReqId) && fr.getProfielReceivedId().toString().equals(receivedId)) {
                return fr.isIsReqRejected();
            }
        }

        return false;
    }

    /////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE FRIENDS DATA

    // Method to get all the friends
    @SuppressWarnings("unchecked")
    public static List<Amistad> getAllFriends() {
        // Load the friends from the file 

        File file = new File(Constantes.FRIENDS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Amistad> friends = (List<Amistad>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (friends == null || friends.isEmpty()) {
                    friends = new ArrayList<>();
                }

                return friends;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.FRIENDS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveFriends(List<Amistad> friends) {
        // Save the friends to the file 

        File file = new File(Constantes.FRIENDS_FILE);

        // Save the friends to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(friends);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los amigos", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a friend
    public static boolean saveFriend(Amistad friend) {
        // Load the friends from the file 

        List<Amistad> friends = getAllFriends();

        // Add the friend to the list
        friends.add(friend);

        // Save the friends to the file 
        return saveFriends(friends);
    }

    // Method to get all the friends by userId (profileId) or (friendId)
    public static List<Amistad> getFriendsByUserId(String userId) {
        // Load the friends from the file 

        List<Amistad> friends = getAllFriends();

        // Check if the friend exists
        List<Amistad> userFriends = new ArrayList<>();
        for (Amistad f : friends) {
            if (f.getProfileId().toString().equals(userId) || f.getFriendId().toString().equals(userId)) {
                userFriends.add(f);
            }
        }

        return userFriends;
    }

    // Method to check if a profile is a friend of another profile
    public static boolean isFriend(String profileId, String friendId) {
        // Load the friends from the file 

        List<Amistad> friends = getAllFriends();

        // Check if the friend exists
        for (Amistad f : friends) {
            if (f.getProfileId().toString().equals(profileId) && f.getFriendId().toString().equals(friendId)) {
                return true;
            }
        }

        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////
    /// METHODS TO HANDLE BLOCKED FRIENDS DATA
    
    // Method to get all the blocked friends
    @SuppressWarnings("unchecked")
    public static List<Bloquear> getAllBlockedFriends() {
        // Load the blocked friends from the file 

        File file = new File(Constantes.BLOCKED_FRIENDS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Bloquear> blockedFriends = (List<Bloquear>) ois.readObject();
                ois.close();
                // Check if the list is empty
                if (blockedFriends == null || blockedFriends.isEmpty()) {
                    blockedFriends = new ArrayList<>();
                }

                return blockedFriends;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Archivo no encontrado: " + Constantes.BLOCKED_FRIENDS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveBlockedFriends(List<Bloquear> blockedFriends) {
        // Save the blocked friends to the file 

        File file = new File(Constantes.BLOCKED_FRIENDS_FILE);

        // Save the blocked friends to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(blockedFriends);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los amigos bloqueados", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a blocked friend by (idWhoBlock) and (idBlockedProfile)
    public static boolean saveBlockedFriend(Bloquear blockedFriend) {
        // Load the blocked friends from the file 

        List<Bloquear> blockedFriends = getAllBlockedFriends();

        // Add the blocked friend to the list
        blockedFriends.add(blockedFriend);

        // Save the blocked friends to the file 
        return saveBlockedFriends(blockedFriends);
    }

    // Method to check if a profile is blocked by another profile (idWhoBlock) and (idBlockedProfile)
    public static boolean isBlockedFriend(String idWhoBlock, String idBlockedProfile) {
        // Load the blocked friends from the file 

        List<Bloquear> blockedFriends = getAllBlockedFriends();

        // Check if the blocked friend exists
        for (Bloquear bf : blockedFriends) {
            if (bf.getIdWhoBlock().toString().equals(idWhoBlock) && bf.getIdBlockedProfile().toString().equals(idBlockedProfile)) {
                return true;
            }
        }

        return false;
    }

    // Method to delete a blocked friend by (idWhoBlock) and (idBlockedProfile)
    public static boolean deleteBlockedFriend(String idWhoBlock, String idBlockedProfile) {
        // Load the blocked friends from the file 

        List<Bloquear> blockedFriends = getAllBlockedFriends();

        // Check if the blocked friend exists
        for (Bloquear bf : blockedFriends) {
            if (bf.getIdWhoBlock().toString().equals(idWhoBlock) && bf.getIdBlockedProfile().toString().equals(idBlockedProfile)) {
                blockedFriends.remove(bf);
                break;
            }
        }

        // Save the blocked friends to the file 
        return saveBlockedFriends(blockedFriends);
    }

    
}
