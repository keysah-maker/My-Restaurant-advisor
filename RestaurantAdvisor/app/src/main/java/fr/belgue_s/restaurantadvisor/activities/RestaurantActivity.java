package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, Callback<List<Restaurant>>, TextWatcher {

    private ListView listViewRestaurant;
    private ArrayAdapter restaurantAdapter;
    private LoginActivity loginActivity = LoginActivity.getLoginInstance();
    private ArrayList<Restaurant> history = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        listViewRestaurant = findViewById(R.id.restaurant_content);

        TextView textViewSearchBar = findViewById(R.id.restaurant_search);

        Button signInButton = findViewById(R.id.main_sign_in);
        Button registerButton = findViewById(R.id.main_register_button);
        Button userInfoButton = findViewById(R.id.user_info_button);
        Button historyButton = findViewById(R.id.main_history_button);

        signInButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        userInfoButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);

        listViewRestaurant.setOnItemClickListener(this);
        textViewSearchBar.addTextChangedListener(this);

        userInfoButton.setVisibility(View.GONE);

        if (loginActivity != null && loginActivity.getUser() != null) {
            signInButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            userInfoButton.setVisibility(View.VISIBLE);
        }

        Api api = Retrofit2Client.getRetrofit2Client().create(Api.class);
        Call<List<Restaurant>> call = api.getRestaurants();
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (response.body() != null) {
            if (response.body().size() == 0) {
                Toast.makeText(getApplicationContext(), "No restaurants found.", Toast.LENGTH_SHORT).show();
                return;
            }

            restaurantAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, response.body());
            listViewRestaurant.setAdapter(restaurantAdapter);
        }
    }

    @Override
    public void onFailure(@NotNull Call<List<Restaurant>> call, Throwable t) {
        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Restaurant element = (Restaurant) parent.getItemAtPosition(position);
        if (!history.contains(element))
            history.add(element);

        Intent restaurantTemplateActivity = new Intent(getApplicationContext(), RestaurantTemplateActivity.class);
        restaurantTemplateActivity.putExtra("restaurant", element);
        startActivity(restaurantTemplateActivity);
    }

    @Override
    public void onClick(View v) {
        if (R.id.main_sign_in == v.getId()) {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }

        if (R.id.main_register_button == v.getId()) {
            Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(registerActivity);
        }

        if (R.id.user_info_button == v.getId()) {
            Intent userInfoActivity = new Intent(getApplicationContext(), UserInfoActivity.class);
            startActivity(userInfoActivity);
        }

        if (R.id.main_history_button == v.getId()) {
            if (history.size() > 0) {
                Intent restaurantHistoryActivity = new Intent(getApplicationContext(), RestaurantHistoryActivity.class);
                restaurantHistoryActivity.putExtra("history", history);
                startActivity(restaurantHistoryActivity);
                return;
            }
            Toast.makeText(getApplicationContext(), "No history found.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (restaurantAdapter != null) {
            this.restaurantAdapter.getFilter().filter(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}