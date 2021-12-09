package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.models.User;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginActivity loginActivity = LoginActivity.getLoginInstance();

    private TextView textViewLogin;
    private TextView textViewName;
    private TextView textViewFirstName;
    private TextView textViewEmail;
    private TextView textViewAge;

    private User user = loginActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        textViewLogin = findViewById(R.id.userinfo_login);
        textViewName = findViewById(R.id.userinfo_name);
        textViewFirstName = findViewById(R.id.userinfo_firstname);
        textViewEmail = findViewById(R.id.userinfo_email);
        textViewAge = findViewById(R.id.userinfo_age);

        Button signOutButton = findViewById(R.id.userinfo_signout_button);

        signOutButton.setOnClickListener(this);
        fillTextView(user);
    }

    @SuppressLint("SetTextI18n")
    private void fillTextView(User user) {
        textViewLogin.setText(user.getLogin());
        textViewName.setText(user.getName());
        textViewFirstName.setText(user.getFirstName());
        textViewEmail.setText(user.getEmail());
        textViewAge.setText(String.valueOf(user.getAge()));
    }

    @Override
    public void onClick(View v) {
        if (R.id.userinfo_signout_button == v.getId()) {
            Toast.makeText(this, "Your successfully signed out", Toast.LENGTH_LONG).show();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }
}
