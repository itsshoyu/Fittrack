package com.fittrack;

import com.fittrack.model.User;

public class SessionManager {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return (currentUser != null) ? currentUser.getId() : 0;
    }

    public static String getCurrentUsername() {
        return (currentUser != null) ? currentUser.getUsername() : null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}