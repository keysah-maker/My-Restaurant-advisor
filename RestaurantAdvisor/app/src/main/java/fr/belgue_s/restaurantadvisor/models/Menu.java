package fr.belgue_s.restaurantadvisor.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Menu implements Serializable {

    private int id;
    private String name;
    private String description;
    private float price;

    public Menu(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}