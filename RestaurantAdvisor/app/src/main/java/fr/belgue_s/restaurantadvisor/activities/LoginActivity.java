package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<User> {

    private EditText editTextLogin;
    private EditText editTextPassword;
    public User user;

    @SuppressLint("StaticFieldLeak")
    public static volatile LoginActivity loginInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.login);
        editTextPassword = findViewById(R.id.password);

        findViewById(R.id.login_sign_in).setOnClickListener(this);
        findViewById(R.id.login_register_button).setOnClickListener(this);
        findViewById(R.id.login_restaurants_button).setOnClickListener(this);

    }

    private void userLogin() {
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (login.isEmpty()) {
            editTextLogin.setError("Login is required");
            editTextLogin.requestFocus();
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }

        Call<User> authUser = Retrofit2Client.getRetrofit2Client().create(Api.class).authUser(login, password);
        authUser.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(LoginActivity.this, "Authenticated", Toast.LENGTH_LONG).show();
        if (response.body() != null) {
            this.user = response.body();
        }

        Intent restaurantActivity = new Intent(getApplicationContext(), RestaurantActivity.class);
        startActivity(restaurantActivity);
        finish();
    }

    @Override
    public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_register_button) {
            Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(registerActivity);
            finish();
        }

        if (v.getId() == R.id.login_sign_in) {
            userLogin();
            return;
        }

        if (v.getId() == R.id.login_restaurants_button) {
            Intent restaurantActivity = new Intent(getApplicationContext(), RestaurantActivity.class);
            startActivity(restaurantActivity);
            finish();
        }

    }

    public User getUser() {
        return user;
    }

    public static LoginActivity getLoginInstance() {
        return loginInstance;
    }

}
