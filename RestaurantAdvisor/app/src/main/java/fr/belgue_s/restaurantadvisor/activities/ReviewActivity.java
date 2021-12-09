package fr.belgue_s.restaurantadvisor.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import fr.belgue_s.restaurantadvisor.models.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class ReviewActivity extends AppCompatActivity implements Callback<List<Review>>, View.OnClickListener {

    private LoginActivity loginActivity = LoginActivity.getLoginInstance();
    private ListView listViewReview;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        listViewReview = findViewById(R.id.reviews_content);

        Button createReviewButton = findViewById(R.id.review_create_button);
        createReviewButton.setOnClickListener(this);

        restaurant = (Restaurant) this.getIntent().getSerializableExtra("restaurant");

        Api api = Retrofit2Client.getRetrofit2Client().create(Api.class);
        Call<List<Review>> call = api.getReviews(restaurant.getId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<List<Review>> call, @NotNull Response<List<Review>> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(ReviewActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (response.body() != null) {
            if (response.body().size() == 0)
                Toast.makeText(ReviewActivity.this, "No reviews found for this restaurant", Toast.LENGTH_SHORT).show();

            ArrayAdapter reviewsAdapter = new ArrayAdapter<>(ReviewActivity.this, android.R.layout.simple_list_item_1, response.body());
            listViewReview.setAdapter(reviewsAdapter);
        }
    }

    @Override
    public void onFailure(@NotNull Call<List<Review>> call, @NotNull Throwable t) {
        Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (R.id.review_create_button == v.getId()) {
            if (loginActivity != null && loginActivity.getUser() != null) {
                Intent reviewCreateActivity = new Intent(ReviewActivity.this, ReviewCreateActivity.class);
                reviewCreateActivity.putExtra("restaurant", restaurant);
                startActivity(reviewCreateActivity);
                return;
            }
            Toast.makeText(ReviewActivity.this, "You must be logged in to write a review.", Toast.LENGTH_SHORT).show();
        }
    }
}
