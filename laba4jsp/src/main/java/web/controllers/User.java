package web.controllers;

public class User {
    private static User ourInstance = new User();
    private int id;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private boolean isLoggedIn;

    public static User getInstance() {
        return ourInstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private User() {
        isLoggedIn = false;
        id = -1;

    }
}
