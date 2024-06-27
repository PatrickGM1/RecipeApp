package com.example.yummyrecipes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button homeButton, favoritesButton, shoppingListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeButton = findViewById(R.id.homeButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        shoppingListButton = findViewById(R.id.shoppingListButton);

        setupBottomBar();

        homeButton.setOnClickListener(view -> selectButton(homeButton));
        favoritesButton.setOnClickListener(view -> {
            selectButton(favoritesButton);
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });

        shoppingListButton.setOnClickListener(view -> {
            selectButton(shoppingListButton);
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupBottomBar() {
        selectButton(homeButton);
    }

    private void selectButton(Button selectedButton) {
        resetButtonStyles();
        selectedButton.setTextColor(Color.parseColor("#008080"));
        selectedButton.setBackgroundColor(Color.TRANSPARENT);
    }

    private void resetButtonStyles() {
        Button[] buttons = {homeButton, favoritesButton, shoppingListButton};
        for (Button button : buttons) {
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
