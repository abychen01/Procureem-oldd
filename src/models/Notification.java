package models;

public class Notification {
    private int id;
    private String title;
    private String message;

    public Notification(int id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
