package fr.belgue_s.restaurantadvisor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fr.belgue_s.restaurantadvisor.R;
import fr.belgue_s.restaurantadvisor.models.Restaurant;

public class RestaurantHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_history);

        ListView listViewHistory = findViewById(R.id.restaurant_content);
        ArrayList<Restaurant> history = (ArrayList<Restaurant>) this.getIntent().getSerializableExtra("history");

        if (history != null && history.size() > 0) {
            ArrayAdapter historyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, history);
            listViewHistory.setAdapter(historyAdapter);
        }
    }
}
