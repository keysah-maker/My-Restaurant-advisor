package fr.belgue_s.restaurantadvisor.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Restaurant implements Serializable {

    private int id;
    private String name;
    private String description;
    private float grade;
    private String localization;
    private String phone_number;
    private String website;
    private String hours;

    public Restaurant(String name, String description, float grade, String localization, String phone_number, String website, String hours) {
        this.name = name;
        this.description = description;
        this.grade = grade;
        this.localization = localization;
        this.phone_number = phone_number;
        this.website = website;
        this.hours = hours;
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

    public float getGrade() {
        return grade;
    }

    public String getLocalization() {
        return localization;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getWebsite() {
        return website;
    }

    public String getHours() {
        return hours;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "Description: " + description + "\n\n" +
                "Phone number: " + phone_number + "\n" +
                "Website: " + website + "\n\n" +
                hours;
    }
}
