package com.example.yummyrecipes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FavoritesActivity extends AppCompatActivity {

    private Button homeButton, favoritesButton, shoppingListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        homeButton = findViewById(R.id.homeButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        shoppingListButton = findViewById(R.id.shoppingListButton);

        selectButton(favoritesButton);
        homeButton.setOnClickListener(view -> {
            selectButton(homeButton);
            Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });
        shoppingListButton.setOnClickListener(view -> {
            selectButton(shoppingListButton);
            Intent intent = new Intent( FavoritesActivity.this, ShoppingListActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });
    }

    private void setupBottomBar() {
        selectButton(favoritesButton);
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
