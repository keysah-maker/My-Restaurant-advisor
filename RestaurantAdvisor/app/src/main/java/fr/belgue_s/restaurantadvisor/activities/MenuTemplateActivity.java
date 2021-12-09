package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.Menu;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuTemplateActivity extends AppCompatActivity implements Callback<String>, View.OnClickListener, DialogInterface.OnClickListener {

    private Menu menu;
    private Restaurant restaurant;

    private TextView mTextView;
    private TextView textViewName;
    private TextView textViewDescription;
    private TextView textViewPrice;

    private AlertDialog alertDialog;
    private EditText editText;

    private LoginActivity loginActivity = LoginActivity.getLoginInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_template);

        textViewName = findViewById(R.id.menu_name);
        textViewDescription = findViewById(R.id.menu_description);
        textViewPrice = findViewById(R.id.menu_price);

        Button deleteMenuButton = findViewById(R.id.menu_delete_button);

        deleteMenuButton.setOnClickListener(this);
        deleteMenuButton.setVisibility(View.GONE);

        if (loginActivity != null && loginActivity.getUser() != null) {
            deleteMenuButton.setVisibility(View.VISIBLE);
            alertDialog = new AlertDialog.Builder(this).create();
            editText = new EditText(this);
            alertDialog.setView(editText);

            alertDialog.setTitle("Edit the content");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Update", this);
        }

        menu = (Menu) this.getIntent().getSerializableExtra("menu");
        restaurant = (Restaurant) this.getIntent().getSerializableExtra("restaurant");

        if (menu != null) {
            fillTextView(menu);
        }
    }

    @Override
    public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(MenuTemplateActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(MenuTemplateActivity.this, "Menu successfully updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
        Toast.makeText(MenuTemplateActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    private void fillTextView(Menu menu) {
        textViewName.setText(menu.getName());
        textViewDescription.setText(menu.getDescription());
        textViewPrice.setText(String.valueOf(menu.getPrice()));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mTextView.setText(editText.getText());
        updateMenu();
    }

    public void selectedTextView(View view) {
        if (loginActivity != null && loginActivity.getUser() != null) {
            mTextView = (TextView) view;

            editText.setText(mTextView.getText());
            editText.setInputType(mTextView.getInputType());

            alertDialog.show();
        }
    }

    private void updateMenu() {
        String name = textViewName.getText().toString().trim();
        String description = textViewDescription.getText().toString().trim();
        float price = getFloat(textViewPrice.getText().toString().trim());

        Call<String> updateRestaurant = Retrofit2Client.getRetrofit2Client().create(Api.class).updateMenu(restaurant.getId(), menu.getId(), name, description, price);
        updateRestaurant.enqueue(this);
    }

    private void deleteMenu() {
        Call<String> updateRestaurant = Retrofit2Client.getRetrofit2Client().create(Api.class).deleteMenu(restaurant.getId(), menu.getId());
        updateRestaurant.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuTemplateActivity.this, "You must be authenticated to delete this menu.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MenuTemplateActivity.this, "Menu successfully deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(MenuTemplateActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    @Override
    public void onClick(View v) {
        if (R.id.menu_delete_button == v.getId()) {
            deleteMenu();
            Intent restaurantTemplateActivity = new Intent(getApplicationContext(), RestaurantTemplateActivity.class);
            restaurantTemplateActivity.putExtra("restaurant", restaurant);
            startActivity(restaurantTemplateActivity);
            finish();
        }
    }
}
