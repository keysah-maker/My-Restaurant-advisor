package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.Menu;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import fr.belgue_s.restaurantadvisor.models.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantTemplateActivity extends AppCompatActivity implements Callback<String>, DialogInterface.OnClickListener, View.OnClickListener {

    private Restaurant restaurant;

    private TextView mTextView;

    private TextView textViewName;
    private TextView textViewDescription;
    private TextView textViewGrade;
    private TextView textViewLocalization;
    private TextView textViewPhoneNumber;
    private TextView textViewWebsite;
    private TextView textViewHours;

    private AlertDialog alertDialog;
    private EditText editText;

    private LoginActivity loginActivity = LoginActivity.getLoginInstance();

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_template);

        textViewName = findViewById(R.id.restaurant_name);
        textViewDescription = findViewById(R.id.restaurant_description);
        textViewGrade = findViewById(R.id.restaurant_grade);
        textViewLocalization = findViewById(R.id.restaurant_localization);
        textViewPhoneNumber = findViewById(R.id.restaurant_phone_number);
        textViewWebsite = findViewById(R.id.restaurant_website);
        textViewHours = findViewById(R.id.restaurant_hours);

        Button restaurantMenusButton = findViewById(R.id.menus_button);
        Button restaurantDeleteButton = findViewById(R.id.restaurant_delete_button);
        Button restaurantShareButton = findViewById(R.id.restaurant_invite_button);
        Button restaurantReviewsButtons = findViewById(R.id.restaurant_review_button);

        restaurantMenusButton.setOnClickListener(this);
        restaurantShareButton.setOnClickListener(this);
        restaurantDeleteButton.setOnClickListener(this);
        restaurantReviewsButtons.setOnClickListener(this);

        restaurantDeleteButton.setVisibility(View.GONE);

        if (loginActivity != null && loginActivity.getUser() != null) {
            restaurantDeleteButton.setVisibility(View.VISIBLE);

            alertDialog = new AlertDialog.Builder(this).create();
            editText = new EditText(this);

            alertDialog.setView(editText);
            alertDialog.setTitle("Edit the content");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Update", this);
        }

        restaurant = (Restaurant) this.getIntent().getSerializableExtra("restaurant");

        if (restaurant != null) {
            fillTextView(restaurant);
        }
    }

    @SuppressLint("SetTextI18n")
    private void fillTextView(Restaurant restaurant) {
        textViewName.setText(restaurant.getName());
        textViewDescription.setText(restaurant.getDescription());
        textViewGrade.setText(String.valueOf(restaurant.getGrade()));
        textViewLocalization.setText(restaurant.getLocalization());
        textViewPhoneNumber.setText(restaurant.getPhoneNumber());
        textViewWebsite.setText(restaurant.getWebsite());
        textViewHours.setText(restaurant.getHours());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mTextView.setText(editText.getText());
        updateRestaurant();
    }

    public void selectedTextView(View view) {
        if (loginActivity != null && loginActivity.getUser() != null) {
            mTextView = (TextView) view;

            editText.setText(mTextView.getText());
            editText.setInputType(mTextView.getInputType());

            alertDialog.show();
            return;
        }

        if (textViewName.getId() == view.getId()) {
            Uri url = Uri.parse("http://www.google.com/#q=" + textViewName.getText().toString().trim());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(browserIntent);
            return;
        }

        if (textViewLocalization.getId() == view.getId()) {
            Uri address = Uri.parse("geo:0,0?q=" + textViewLocalization.getText().toString().trim());
            Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, address);
            googleMapIntent.setPackage("com.google.android.apps.maps");
            startActivity(googleMapIntent);
            return;
        }

        if (textViewLocalization.getId() == view.getId()) {
            Uri address = Uri.parse("geo:0,0?q=" + textViewLocalization.getText().toString().trim());
            Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, address);
            googleMapIntent.setPackage("com.google.android.apps.maps");
            startActivity(googleMapIntent);
            return;
        }

        if (textViewPhoneNumber.getId() == view.getId()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Uri url = Uri.parse("tel:" + textViewPhoneNumber.getText().toString().trim());
                Intent callRestaurant = new Intent(Intent.ACTION_CALL, url);
                callRestaurant.setData(url);
                startActivity(callRestaurant);
            }
            return;
        }

        if (textViewWebsite.getId() == view.getId()) {
            String url = textViewWebsite.getText().toString().trim();

            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;

            Uri uri = Uri.parse(url);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browserIntent);
        }

    }

    @Override
    public void onClick(View v) {
        if (R.id.menus_button == v.getId()) {
            Call<List<Menu>> updateRestaurant = Retrofit2Client.getRetrofit2Client().create(Api.class).getMenus(restaurant.getId());
            updateRestaurant.enqueue(new Callback<List<Menu>>() {
                @Override
                public void onResponse(@NotNull Call<List<Menu>> call, @NotNull Response<List<Menu>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.body() != null) {
                        if (response.body().size() == 0) {
                            Toast.makeText(getApplicationContext(), "No menus found for this restaurant", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent menuActivity = new Intent(getApplicationContext(), MenuActivity.class);
                        menuActivity.putExtra("restaurant", restaurant);
                        startActivity(menuActivity);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Menu>> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        if (R.id.restaurant_delete_button == v.getId()) {
            deleteRestaurant();
            Intent restaurantActivity = new Intent(getApplicationContext(), RestaurantActivity.class);
            restaurantActivity.putExtra("restaurant", restaurant);
            startActivity(restaurantActivity);
            finish();
        }

        if (R.id.restaurant_review_button == v.getId()) {
            Call<List<Review>> restaurantReviews = Retrofit2Client.getRetrofit2Client().create(Api.class).getReviews(restaurant.getId());
            restaurantReviews.enqueue(new Callback<List<Review>>() {
                @Override
                public void onResponse(@NotNull Call<List<Review>> call, @NotNull Response<List<Review>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.body() != null) {
                        Intent reviewActivity = new Intent(getApplicationContext(), ReviewActivity.class);
                        reviewActivity.putExtra("restaurant", restaurant);
                        startActivity(reviewActivity);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Review>> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        if (R.id.restaurant_invite_button == v.getId()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Hey, \n\n" +
                    "I would like to go to " + restaurant.getName() + " " +
                    "restaurant with you, the address is " + restaurant.getLocalization() + " " +
                    "and the phone number is " + restaurant.getPhoneNumber() + ".\n\n" +
                    "Hope you enjoy it :)\n\n" +
                    "This message has been sent from Restaurant Advisor application.";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Restaurant Advisor");
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share using"));
        }

    }

    private void deleteRestaurant() {
        Call<String> updateRestaurant = Retrofit2Client.getRetrofit2Client().create(Api.class).deleteRestaurant(restaurant.getId());
        updateRestaurant.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RestaurantTemplateActivity.this, "You must be authenticated to delete this restaurant.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(RestaurantTemplateActivity.this, "Restaurant successfully deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(RestaurantTemplateActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateRestaurant() {
        String name = textViewName.getText().toString().trim();
        String description = textViewDescription.getText().toString().trim();
        float grade = getFloat(textViewGrade.getText().toString().trim());
        String localization = textViewLocalization.getText().toString().trim();
        String phone_number = textViewPhoneNumber.getText().toString().trim();
        String website = textViewWebsite.getText().toString().trim();
        String hours = textViewHours.getText().toString().trim();

        Call<String> updateRestaurant = Retrofit2Client.getRetrofit2Client().create(Api.class).updateRestaurant(restaurant.getId(), name, description, grade, localization, phone_number, website, hours);
        updateRestaurant.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<String> call, Response<String> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Restaurant successfully updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(@NotNull Call<String> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private float getFloat(String value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Float.parseFloat(value);
            }
        } catch (NumberFormatException ignored) {
        }
        return 0;
    }
}
