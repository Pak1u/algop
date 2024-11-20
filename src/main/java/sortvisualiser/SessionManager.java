package sortvisualiser;

public class SessionManager {
    private static String loggedInUser = null;
    
    // Set the logged-in user
    public static void setLoggedInUser(String username) {
        loggedInUser = username;
    }
    
    // Get the logged-in user
    public static String getLoggedInUser() {
        return loggedInUser;
    }
    
    // Logout the user (clear the session)
    public static void logout() {
        loggedInUser = null;
    }
    
    // Check if the user is logged in
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
