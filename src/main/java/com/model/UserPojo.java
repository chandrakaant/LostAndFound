package com.model;

import com.custom.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserPojo {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    //@ValidEmail
    private String email;
    @NotNull
    private Long phone;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    private Integer age;

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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
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
}
