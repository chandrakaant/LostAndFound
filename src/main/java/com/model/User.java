package com.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "userName" , nullable = false)
    private String userName;

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "phone" , nullable = false)
    private long phone;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "age" , nullable = false)
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_item", joinColumns = { @JoinColumn(name = "userId")}, inverseJoinColumns = { @JoinColumn(name = "itemId") })
    private List<Item> items;

    //----------------------------------------------------
    public User(){

    }

    public User(long phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User(String userName, String email, long phone, String password, String address, int age) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.age = age;
    }

    //-------------------------------------------------------------------
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItem(List<Item> items) {
        this.items = items;
    }
}
