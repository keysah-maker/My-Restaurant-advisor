package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Callback<ResponseBody> {

    private EditText editTextFirstName;
    private EditText editTextName;
    private EditText editTextLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmation;
    private EditText editTextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFirstName = findViewById(R.id.register_firstname);
        editTextName = findViewById(R.id.register_name);
        editTextLogin = findViewById(R.id.register_login);
        editTextEmail = findViewById(R.id.register_email);
        editTextPassword = findViewById(R.id.register_password);
        editTextPasswordConfirmation = findViewById(R.id.password_confirm);
        editTextAge = findViewById(R.id.register_age);

        findViewById(R.id.login_sign_in).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            userSignUp();
        } else if (v.getId() == R.id.login_sign_in) {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
    }

    private void userSignUp() {
        String firstname = editTextFirstName.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String login = editTextLogin.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password_confirm = editTextPasswordConfirmation.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        checkMissingData(firstname, editTextFirstName, "Firstname is required");
        checkMissingData(name, editTextName, "Name is required");
        checkMissingData(login, editTextLogin, "Login is required");
        checkMissingData(email, editTextEmail, "Email is required");
        checkMissingData(password, editTextPassword, "Password is required");
        checkMissingData(password_confirm, editTextPassword, "Password confirmation is required");
        checkMissingData(password_confirm, editTextAge, "Age is required");

        if (!password.equals(password_confirm)) {
            editTextPasswordConfirmation.setError("The confirmation password does not match the password entered.");
            editTextPasswordConfirmation.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        Call<ResponseBody> userRegister = Retrofit2Client.getRetrofit2Client().create(Api.class).registerUser(login, password, email, name, firstname,age);
        userRegister.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<ResponseBody> call, Response<ResponseBody> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(RegisterActivity.this, "Your login or email is already taken", Toast.LENGTH_LONG).show();
            return;
        }

        if (response.body() != null) {
            Toast.makeText(RegisterActivity.this, "Your account has been successfully created", Toast.LENGTH_LONG).show();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }

    @Override
    public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void checkMissingData(String data, EditText editText, String message) {
        if (data.isEmpty()) {
            editText.setError(message);
            editText.requestFocus();
        }
    }

}
