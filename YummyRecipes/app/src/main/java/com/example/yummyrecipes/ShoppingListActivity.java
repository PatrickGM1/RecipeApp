package com.example.yummyrecipes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingListActivity extends AppCompatActivity {

    private Button homeButton, favoritesButton, shoppingListButton;
    private Button addButton;
    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;
    private List<String> shoppingList;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ShoppingListPrefs";
    private static final String SHOPPING_LIST_KEY = "ShoppingList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        homeButton = findViewById(R.id.homeButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        shoppingListButton = findViewById(R.id.shoppingListButton);
        addButton = findViewById(R.id.addButton);
        shoppingListView = findViewById(R.id.shoppingListView);

        shoppingList = new ArrayList<>(loadShoppingList());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        shoppingListView.setAdapter(adapter);

        setupBottomBar();

        favoritesButton.setOnClickListener(view -> {
            selectButton(favoritesButton);
            // Navigate to FavoritesActivity
            Intent intent = new Intent(ShoppingListActivity.this, FavoritesActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });
        shoppingListButton.setOnClickListener(view -> selectButton(shoppingListButton));

        homeButton.setOnClickListener(view -> {
            selectButton(homeButton);
            // Navigate to MainActivity
            Intent intent = new Intent(ShoppingListActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Remove animation
        });


        addButton.setOnClickListener(view -> showAddItemDialog());

        shoppingListView.setOnItemClickListener((parent, view, position, id) -> {
            shoppingList.remove(position);
            adapter.notifyDataSetChanged();
            saveShoppingList();
        });
    }

    private void setupBottomBar() {
        selectButton(shoppingListButton);
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

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText inputItem = viewInflated.findViewById(R.id.inputItem);
        final EditText inputQuantity = viewInflated.findViewById(R.id.inputQuantity);
        final Spinner unitSpinner = viewInflated.findViewById(R.id.unitSpinner);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            String item = inputItem.getText().toString().trim();
            String quantity = inputQuantity.getText().toString().trim();
            String unit = unitSpinner.getSelectedItem().toString();
            if (!item.isEmpty() && !quantity.isEmpty()) {
                shoppingList.add(item + " - " + quantity + " " + unit);
                adapter.notifyDataSetChanged();
                saveShoppingList();
            } else {
                Toast.makeText(ShoppingListActivity.this, "Item or quantity cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveShoppingList() {
        Set<String> set = new HashSet<>(shoppingList);
        sharedPreferences.edit().putStringSet(SHOPPING_LIST_KEY, set).apply();
    }

    private Set<String> loadShoppingList() {
        return sharedPreferences.getStringSet(SHOPPING_LIST_KEY, new HashSet<>());
    }
}
