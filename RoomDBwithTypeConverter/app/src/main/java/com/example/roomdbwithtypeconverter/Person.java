package com.example.roomdbwithtypeconverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "activeDays")
    private HashMap<String, Boolean> activeDays;

    public Person(int id, String name, String city, HashMap<String, Boolean> activeDays) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.activeDays = activeDays;
    }

    @Ignore
    public Person(String name, String city, HashMap<String, Boolean> activeDays) {
        this.name = name;
        this.city = city;
        this.activeDays = activeDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<String, Boolean> getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(HashMap<String, Boolean> activeDays) {
        this.activeDays = activeDays;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", activeDays=" + activeDays +
                '}';
    }
}
