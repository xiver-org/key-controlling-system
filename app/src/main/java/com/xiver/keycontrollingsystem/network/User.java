package com.xiver.keycontrollingsystem.network;

public class User {
    private int id;
    private String email;
    private String username;
    private String name;
    private String surname;
    private String ico_url;
    private int role_id;
    private boolean is_active;
    private boolean is_superuser;
    private boolean is_verified;


    public User(int id, String email, String username, String name, String surname, String ico_url, int role_id, boolean is_active, boolean is_superuser, boolean is_verified) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.ico_url = ico_url;
        this.role_id = role_id;
        this.is_active = is_active;
        this.is_superuser = is_superuser;
        this.is_verified = is_verified;
    }

    public User() {
        this.id = -1;
        this.email = null;
        this.username = null;
        this.name = null;
        this.surname = null;
        this.ico_url = null;
        this.role_id = -1;
        this.is_active = false;
        this.is_superuser = false;
        this.is_verified = false;
    }

    public int getId() {
        return id;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getEmail() {
        return email;
    }

    public String getIco_url() {
        return ico_url;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public boolean getIsActive() {
        return is_active;
    }

    public boolean getIsSuperuser() {
        return is_superuser;
    }

    public boolean getIsVerified() {
        return is_verified;
    }
}
