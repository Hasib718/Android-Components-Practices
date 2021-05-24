package com.hasib.petzone.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hasib.petzone.UserType;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String mobile;
    private UserType type;
    private String email;
    private String password;

    public User(String name, String mobile, UserType type, String email, String password) {
        this.name = name;
        this.mobile = mobile;
        this.type = type;
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public UserType getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile=" + mobile +
                ", type=" + type +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
