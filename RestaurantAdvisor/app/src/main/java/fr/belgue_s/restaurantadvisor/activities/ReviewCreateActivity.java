package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import fr.belgue_s.restaurantadvisor.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewCreateActivity extends AppCompatActivity implements Callback<String>, View.OnClickListener {

    private LoginActivity loginActivity = LoginActivity.getLoginInstance();
    private User user = loginActivity.getUser();

    private EditText editTextReview;
    private EditText editTextRate;

    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        Button submitReviewButton = findViewById(R.id.review_create_submit_button);
        submitReviewButton.setOnClickListener(this);

        editTextReview = findViewById(R.id.review_create_content);
        editTextRate = findViewById(R.id.review_create_rate);

        restaurant = (Restaurant) this.getIntent().getSerializableExtra("restaurant");
    }

    @Override
    public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(ReviewCreateActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
            return;
        }

        if (response.body() != null) {
            Toast.makeText(ReviewCreateActivity.this, "Your review has been successfully submitted", Toast.LENGTH_LONG).show();
            Intent reviewActivity = new Intent(ReviewCreateActivity.this, ReviewActivity.class);
            reviewActivity.putExtra("restaurant", restaurant);
            startActivity(reviewActivity);
            finish();
        }
    }

    @Override
    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (R.id.review_create_submit_button == v.getId()) {
            float rate;
            try {
                rate = getFloat(editTextRate.getText().toString().trim());
            } catch (NumberFormatException e) {
                editTextRate.setError("You have to specify a correct number");
                editTextRate.requestFocus();
                return;
            }

            String review = editTextReview.getText().toString().trim();

            if (rate > 10 || rate < 0) {
                editTextRate.setError("The rate must be between 0 and 10");
                editTextRate.requestFocus();
                return;
            }

            if (review.length() < 10) {
                editTextReview.setError("The review must have a minimum of 10 characters");
                editTextReview.requestFocus();
                return;
            }

            Api api = Retrofit2Client.getRetrofit2Client().create(Api.class);
            Call<String> call = api.addReview(restaurant.getId(), user.getId(), user.getLogin(), restaurant.getId(), rate, review);
            call.enqueue(this);
        }
    }

    private float getFloat(String value) throws NumberFormatException {
        if (!TextUtils.isEmpty(value)) {
            return Float.parseFloat(value);
        }
        return 0;
    }
}
