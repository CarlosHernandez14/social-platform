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

import com.socialmedia.domain.FriendRequest;
import com.socialmedia.domain.FriendShip;
import com.socialmedia.domain.Image;
import com.socialmedia.domain.Post;
import com.socialmedia.domain.PostReaction;
import com.socialmedia.domain.Profile;
import com.socialmedia.domain.Reaction;
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

    public static boolean register (String username, String email, String password) {
        
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
        User newUser = new User(email, PasswordUtils.hashPassword(password));
        users.add(newUser);
        // Save the users to the file
        if (saveUsers(users)) {
            // If the user was saved successfully register the profile of the user
            Profile profile = new Profile(username, username, null, newUser.getIdUser(), null);
            List<Profile> profiles = getAllProfiles();
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

    ////////////////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE PROFILES DATA

    // Method to get all profiles
    @SuppressWarnings("unchecked")
    public static List<Profile> getAllProfiles() {
        // Load the profiles from the file 

        File file = new File(AppConstants.PROFILE_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Profile> profiles = (List<Profile>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.PROFILE_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveProfiles(List<Profile> profiles) {
        // Save the profiles to the file 

        File file = new File(AppConstants.PROFILE_FILE);

        // Save the profiles to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(profiles);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los perfiles", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to get a profile by userId
    public static Profile getProfileByUserId(String userId) {
        // Load the profiles from the file 

        List<Profile> profiles = getAllProfiles();

        // Check if the profile exists
        for (Profile p : profiles) {
            if (p.getUserId().toString().equals(userId)) {
                return p;
            }
        }

        return null;
    }

    // Method to get a profile by id
    public static Profile getProfileById(String profileId) {
        // Load the profiles from the file 

        List<Profile> profiles = getAllProfiles();

        // Check if the profile exists
        for (Profile p : profiles) {
            if (p.getIdProfile().toString().equals(profileId)) {
                return p;
            }
        }

        return null;
    }

    ////////////////////////////////////////////////////////////////////////
    
    // METHODS TO HANLDE POSTS DATA

    // Method to get all posts
    @SuppressWarnings("unchecked")
    public static List<Post> getAllPosts() {
        // Load the posts from the file 

        File file = new File(AppConstants.POSTS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Post> posts = (List<Post>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.POSTS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean savePosts(List<Post> posts) {
        // Save the posts to the file 

        File file = new File(AppConstants.POSTS_FILE);

        // Save the posts to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(posts);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los posts", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to get all the posts of a profile
    public static List<Post> getPostsByProfileId(String profileId) {
        // Load the posts from the file 

        List<Post> posts = getAllPosts();

        // Check if the profile exists
        List<Post> profilePosts = new ArrayList<>();
        for (Post p : posts) {
            if (p.getProfileId().toString().equals(profileId)) {
                profilePosts.add(p);
            }
        }

        return profilePosts;
    }

    // Method to save a post
    public static boolean savePost(Post post) {
        // Load the posts from the file 

        List<Post> posts = getAllPosts();

        // Add the post to the list
        posts.add(post);

        // Save the posts to the file 
        return savePosts(posts);
    }

    // Method to delete a post
    public static boolean deletePost(String postId) {
        // Load the posts from the file 

        List<Post> posts = getAllPosts();

        // Check if the post exists
        for (Post p : posts) {
            if (p.getIdPost().toString().equals(postId)) {
                posts.remove(p);
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
    public static List<Image> getAllImages() {
        // Load the images from the file 

        File file = new File(AppConstants.IMAGES_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Image> images = (List<Image>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.IMAGES_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveImages(List<Image> images) {
        // Save the images to the file 

        File file = new File(AppConstants.IMAGES_FILE);

        // Save the images to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(images);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las imagenes", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save an image
    public static boolean saveImage(Image image) {
        // Load the images from the file 

        List<Image> images = getAllImages();

        // Add the image to the list
        images.add(image);

        // Save the images to the file 
        return saveImages(images);
    }

    // Method to get an image by id
    public static Image getImageById(String imageId) {
        // Load the images from the file 

        List<Image> images = getAllImages();

        // Check if the image exists
        for (Image i : images) {
            if (i.getIdImage().toString().equals(imageId)) {
                return i;
            }
        }

        return null;
    }

    // Method to get an image by postId
    public static List<Image> getImagesByPostId(String postId) {
        // Load the images from the file 

        List<Image> images = getAllImages();

        // Check if the image exists
        List<Image> postImages = new ArrayList<>();
        for (Image i : images) {
            if (i.getPostId().toString().equals(postId)) {
                postImages.add(i);
            }
        }

        return postImages;
    }

    // Delete all images of a post by postId
    public static boolean deleteImagesByPostId(String postId) {
        // Load the images from the file 

        List<Image> images = getAllImages();

        // Check if the image exists
        for (Image i : images) {
            if (i.getPostId().toString().equals(postId)) {
                images.remove(i);
            }
        }

        // Save the images to the file 
        return saveImages(images);
    }

    //////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE REACTIONS DATA

    // Method to get all reactions
    @SuppressWarnings("unchecked")
    public static List<Reaction> getAllReactions() {
        // Load the reactions from the file 

        File file = new File(AppConstants.REACTIONS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Reaction> reactions = (List<Reaction>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.REACTIONS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveReactions(List<Reaction> reactions) {
        // Save the reactions to the file 

        File file = new File(AppConstants.REACTIONS_FILE);

        // Save the reactions to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(reactions);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las reacciones", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a reaction
    public static boolean saveReaction(Reaction reaction) {
        // Load the reactions from the file 

        List<Reaction> reactions = getAllReactions();

        // Add the reaction to the list
        reactions.add(reaction);

        // Save the reactions to the file 
        return saveReactions(reactions);
    }

    //////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE THE REACTIONS FOR POSTS DATA
    // Method to get all the reactions for a post
    @SuppressWarnings("unchecked")
    public static List<PostReaction> getAllPostReactions() {
        // Load the reactions from the file 

        File file = new File(AppConstants.POST_REACTIONS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<PostReaction> postReactions = (List<PostReaction>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.POST_REACTIONS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean savePostReactions(List<PostReaction> postReactions) {
        // Save the reactions to the file 

        File file = new File(AppConstants.POST_REACTIONS_FILE);

        // Save the reactions to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(postReactions);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las reacciones de los posts", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a reaction for a post
    public static boolean savePostReaction(PostReaction postReaction) {
        // Load the reactions from the file 

        List<PostReaction> postReactions = getAllPostReactions();

        // Add the reaction to the list
        postReactions.add(postReaction);

        // Save the reactions to the file 
        return savePostReactions(postReactions);
    }
    // Method to get all the reactions for a post by postId
    public static List<PostReaction> getPostReactionsByPostId(String postId) {
        // Load the reactions from the file 

        List<PostReaction> postReactions = getAllPostReactions();

        // Check if the reaction exists
        List<PostReaction> postReactionsList = new ArrayList<>();
        for (PostReaction pr : postReactions) {
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
    public static List<FriendRequest> getAllFriendRequests() {
        // Load the friends requests from the file 

        File file = new File(AppConstants.FRIEND_REQ_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<FriendRequest> friendRequests = (List<FriendRequest>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.FRIEND_REQ_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveFriendRequests(List<FriendRequest> friendRequests) {
        // Save the friends requests to the file 

        File file = new File(AppConstants.FRIEND_REQ_FILE);

        // Save the friends requests to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(friendRequests);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar las solicitudes de amistad", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a friend request
    public static boolean saveFriendRequest(FriendRequest friendRequest) {
        // Load the friends requests from the file 

        List<FriendRequest> friendRequests = getAllFriendRequests();

        // Add the friend request to the list
        friendRequests.add(friendRequest);

        // Save the friends requests to the file 
        return saveFriendRequests(friendRequests);
    }

    // Method to get all the friends requests by userId
    public static List<FriendRequest> getFriendRequestsByUserId(String userId) {
        // Load the friends requests from the file 

        List<FriendRequest> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        List<FriendRequest> userFriendRequests = new ArrayList<>();
        for (FriendRequest fr : friendRequests) {
            if (fr.getProfileReqId().toString().equals(userId)) {
                userFriendRequests.add(fr);
            }
        }

        return userFriendRequests;
    }

    // Method to get all the friends requests received by userId (receivedId)
    public static List<FriendRequest> getFriendRequestsReceivedByUserId(String userId) {
        // Load the friends requests from the file 

        List<FriendRequest> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        List<FriendRequest> userFriendRequests = new ArrayList<>();
        for (FriendRequest fr : friendRequests) {
            if (fr.getProfielReceivedId().toString().equals(userId)) {
                userFriendRequests.add(fr);
            }
        }

        return userFriendRequests;
    }

    // Method to check if a friend request is sent by a userId (profileReqId) to another userId (receivedId)
    public static boolean isFriendRequestSent(String profileReqId, String receivedId) {
        // Load the friends requests from the file 

        List<FriendRequest> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        for (FriendRequest fr : friendRequests) {
            if (fr.getProfileReqId().toString().equals(profileReqId) && fr.getProfielReceivedId().toString().equals(receivedId)) {
                return true;
            }
        }

        return false;
    }

    // Method to update a friend request
    public static boolean updateFriendRequest(String friendReqId, FriendRequest friendRequest) {
        // Load the friends requests from the file 

        List<FriendRequest> friendRequests = getAllFriendRequests();

        // Check if the friend request exists
        for (FriendRequest fr : friendRequests) {
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

    /////////////////////////////////////////////////////////////////////////////
    // METHODS TO HANDLE FRIENDS DATA

    // Method to get all the friends
    @SuppressWarnings("unchecked")
    public static List<FriendShip> getAllFriends() {
        // Load the friends from the file 

        File file = new File(AppConstants.FRIENDS_FILE);

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<FriendShip> friends = (List<FriendShip>) ois.readObject();
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
            System.out.println("Archivo no encontrado: " + AppConstants.FRIENDS_FILE);
        }


        return new ArrayList<>();
    }

    public static boolean saveFriends(List<FriendShip> friends) {
        // Save the friends to the file 

        File file = new File(AppConstants.FRIENDS_FILE);

        // Save the friends to the file 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(friends);
            oos.flush();
            oos.close();

            return true;
        } catch (IOException e) {
            Logger.getLogger(Dao.class.getName()).log(java.util.logging.Level.SEVERE, "No se pudieron guardar los amigos", e);
            e.printStackTrace();
        }
        
        return false;
    }

    // Method to save a friend
    public static boolean saveFriend(FriendShip friend) {
        // Load the friends from the file 

        List<FriendShip> friends = getAllFriends();

        // Add the friend to the list
        friends.add(friend);

        // Save the friends to the file 
        return saveFriends(friends);
    }

    // Method to get all the friends by userId (profileId) or (friendId)
    public static List<FriendShip> getFriendsByUserId(String userId) {
        // Load the friends from the file 

        List<FriendShip> friends = getAllFriends();

        // Check if the friend exists
        List<FriendShip> userFriends = new ArrayList<>();
        for (FriendShip f : friends) {
            if (f.getProfileId().toString().equals(userId) || f.getFriendId().toString().equals(userId)) {
                userFriends.add(f);
            }
        }

        return userFriends;
    }

    // Method to check if a profile is a friend of another profile
    public static boolean isFriend(String profileId, String friendId) {
        // Load the friends from the file 

        List<FriendShip> friends = getAllFriends();

        // Check if the friend exists
        for (FriendShip f : friends) {
            if (f.getProfileId().toString().equals(profileId) && f.getFriendId().toString().equals(friendId)) {
                return true;
            }
        }

        return false;
    }

    
}
