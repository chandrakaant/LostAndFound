package com.model;

import java.util.List;

public class Admin {

    private int adminId;
    private String name;
    private String email;
    private String password;
    private User users;
    private Item items;

    //--------------------------------
    public Admin(){

    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //--------------------------------------------
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }
}
