package com.example.madprojectprac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class finalshoppinglist extends AppCompatActivity {
    ListView shoppingListView;
    Button btn, btn1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalshoppinglist);

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finalshoppinglist.this, navigation.class);
                startActivity(intent);
            }
        });
        shoppingListView = findViewById(R.id.shoppingListView);
        ArrayList<String> selectedItems = shoppinglistmanager.getInstance().getShoppingList();

        ArrayAdapter<String> shoppingListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedItems);
        shoppingListView.setAdapter(shoppingListAdapter);
    }
}