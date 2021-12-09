package fr.belgue_s.restaurantadvisor.models;

@SuppressWarnings("unused")
public class User {

    private int id;
    private String login;
    private String name;
    private String firstname;
    private String email;
    private int age;
    private boolean isConnected = false;

    public User(String login, String name, String firstname, String email, int age) {
        this.login = login;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
