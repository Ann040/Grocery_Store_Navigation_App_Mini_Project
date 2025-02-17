package com.example.madprojectprac;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {
    ListView aisleListView;
    String[] aisles = {"Dairy", "Confectionery", "Fruits", "Vegetables", "Beverages", "Bakery", "Seafood", "Meat", "Florist"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        aisleListView = findViewById(R.id.aisleListView);
        ArrayAdapter<String> aisleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aisles);
        aisleListView.setAdapter(aisleAdapter);

        aisleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAisle = aisles[position];
                Intent intent = new Intent(Categories.this, aisledetail.class);
                intent.putExtra("aisle", selectedAisle);
                startActivity(intent);
            }
        });
    }
}