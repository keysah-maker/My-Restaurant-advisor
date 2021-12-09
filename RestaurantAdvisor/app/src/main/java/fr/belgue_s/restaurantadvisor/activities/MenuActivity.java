package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.api.Api;
import fr.belgue_s.restaurantadvisor.api.Retrofit2Client;
import fr.belgue_s.restaurantadvisor.models.Menu;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<List<Menu>> {

    private Restaurant restaurant;
    private ListView listViewMenus;
    private List<Menu> menusList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listViewMenus = findViewById(R.id.menus_content);
        listViewMenus.setOnItemClickListener(this);

        restaurant = (Restaurant) this.getIntent().getSerializableExtra("restaurant");

        Api api = Retrofit2Client.getRetrofit2Client().create(Api.class);
        Call<List<Menu>> call = api.getMenus(restaurant.getId());
        call.enqueue(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Menu element = menusList.get(position);

        Intent restaurantTemplateActivity = new Intent(getApplicationContext(), MenuTemplateActivity.class);
        restaurantTemplateActivity.putExtra("menu", element);
        restaurantTemplateActivity.putExtra("restaurant", restaurant);
        startActivity(restaurantTemplateActivity);
        finish();
    }

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
            menusList = response.body();
            listViewMenus.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, menusList));
        }
    }

    @Override
    public void onFailure(@NotNull Call<List<Menu>> call, @NotNull Throwable t) {
        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}