package fr.belgue_s.restaurantadvisor.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Review {

    private int id;
    private int user_id;
    private String user_login;
    private int restaurant_id;
    private float rate;
    private String review;
    private Timestamp created_at;

    @SuppressLint("SimpleDateFormat")

    public Review(int user_id, String user_login, int restaurant_id, float rate, String review) {
        this.user_id = user_id;
        this.user_login = user_login;
        this.restaurant_id = restaurant_id;
        this.rate = rate;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public String getUserLogin() {
        return user_login;
    }

    public int getUserID() {
        return user_id;
    }

    public int getRestaurantID() {
        return restaurant_id;
    }

    public float getRate() {
        return rate;
    }

    public String getReview() {
        return review;
    }

    @SuppressLint("SimpleDateFormat")
    public String getReviewDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(created_at);
    }

    @NonNull
    @Override
    public String toString() {
        return this.review + "\n\nRate: " + rate + "/10\n\n" + "Posted by " + user_login + " on " + getReviewDate() + '\n';
    }
}
