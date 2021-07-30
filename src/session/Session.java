package session;

import models.User;


public class Session {
    public static User user = null;
    public static Object navData = null;
    public static String pageName = "login";

    public static void clearAll() {
        user = null;
        navData = null;

    }
}
