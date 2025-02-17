package com.example.madprojectprac;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.madprojectprac.R;
import com.example.madprojectprac.shoppinglistmanager;

import java.util.ArrayList;

public class aisledetail extends AppCompatActivity {
    ListView itemListView;
    Button saveButton;
    ArrayList<String> selectedItems = new ArrayList<>();

    String[] dairyItems = {"Cheese", "Eggs", "Milk", "Butter", "Yogurt", "Cream", "Cottage Cheese", "Sour Cream", "Ghee", "Lassi"};

    // Confectionery aisle items
    String[] confectioneryItems = {"Candy", "Chocolate", "Gummies", "Lollipops", "Marshmallows", "Caramel", "Toffee", "Nougat", "Jelly Beans", "Licorice"};

    // Fruits aisle items
    String[] fruitItems = {"Apple", "Banana", "Orange", "Grapes", "Mango", "Pineapple", "Strawberry", "Blueberry", "Watermelon", "Peach"};

    // Vegetables aisle items
    String[] vegetableItems = {"Carrot", "Broccoli", "Spinach", "Onion", "Potato", "Tomato", "Bell Pepper", "Cabbage", "Cauliflower", "Zucchini"};

    // Beverages aisle items
    String[] beverageItems = {"Juice", "Soda", "Tea", "Coffee", "Water", "Milkshake", "Smoothie", "Energy Drink", "Lemonade", "Iced Tea"};

    // Bakery aisle items
    String[] bakeryItems = {"Bread", "Croissant", "Muffin", "Bagel", "Doughnut", "Cake", "Pastry", "Baguette", "Pita", "Biscuit"};

    // Seafood aisle items
    String[] seafoodItems = {"Salmon", "Shrimp", "Crab", "Lobster", "Tuna", "Oysters", "Scallops", "Mussels", "Cod", "Trout"};

    // Meat aisle items
    String[] meatItems = {"Chicken", "Beef", "Pork", "Turkey", "Lamb", "Sausage", "Bacon", "Ham", "Veal", "Duck"};

    // Florist aisle items
    String[] floristItems = {"Rose", "Lily", "Tulip", "Sunflower", "Daisy", "Orchid", "Carnation", "Hydrangea", "Peony", "Lavender"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisledetail);

        itemListView = findViewById(R.id.itemListView);
        saveButton = findViewById(R.id.saveButton);

        String selectedAisle = getIntent().getStringExtra("aisle");
        String[] items = getItemsForAisle(selectedAisle);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        itemListView.setAdapter(itemAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = itemListView.getCheckedItemPositions();
                selectedItems.clear();
                for (int i = 0; i < items.length; i++) {
                    if (checked.get(i)) {
                        selectedItems.add(items[i]);
                    }
                }
                if (!selectedItems.isEmpty()) {
                    shoppinglistmanager.getInstance().addItems(selectedItems);
                    showSelectionConfirmation();
                } else {
                    Toast.makeText(aisledetail.this, "No items selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String[] getItemsForAisle(String aisle) {
        switch (aisle) {
            case "Dairy":
                return dairyItems;
            case "Confectionery":
                return confectioneryItems;
            case "Fruits":
                return fruitItems;
            case "Vegetables":
                return vegetableItems;
            case "Beverages":
                return beverageItems;
            case "Bakery":
                return bakeryItems;
            case "Seafood":
                return seafoodItems;
            case "Meat":
                return meatItems;
            case "Florist":
                return floristItems;
            default:
                return new String[]{};
        }
    }

    private void showSelectionConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Items Saved")
                .setMessage("Your items have been saved. Would you like to continue shopping?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Go back to the Categories page
                        Intent intent = new Intent(aisledetail.this, Categories.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No, View List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Go to the Final Shopping List page
                        Intent intent = new Intent(aisledetail.this, finalshoppinglist.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}