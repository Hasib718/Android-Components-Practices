package com.hasib.petzone.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hasib.petzone.PetCategory;

@Entity(tableName = "food_table")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private PetCategory type;
    private int quantity;
    private int price;
    private String image;
    private int requestCount;

    public Food(String title, PetCategory type, int quantity, int price, String image, int requestCount) {
        this.title = title;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.requestCount = requestCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public PetCategory getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", requestCount=" + requestCount +
                '}';
    }
}
