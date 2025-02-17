package com.example.madprojectprac;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class home_pg extends AppCompatActivity {

    ImageButton profileBtn, showMapBtn, startShoppingBtn;
    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private Button rateUsButton;
    private Button contactUsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pg);

        // Initialize buttons
        profileBtn = findViewById(R.id.profileBtn);
        showMapBtn = findViewById(R.id.showMapBtn);
        startShoppingBtn = findViewById(R.id.startShoppingBtn);

        // Handle Profile button click
        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(home_pg.this, profile.class);
            startActivity(intent);
        });

        // Handle Show Map button click
        showMapBtn.setOnClickListener(v -> {
            Intent intent = new Intent(home_pg.this, Categories.class);
            startActivity(intent);
        });

        // Handle Start Shopping button click
        startShoppingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(home_pg.this, navigation.class);
            startActivity(intent);
        });

        // Drawer layout and menu icon setup
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.menu_icon);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Set NavigationView width to 75% of screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.75);
        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
        params.width = width;
        navigationView.setLayoutParams(params);

        // Initialize buttons in the navigation drawer
        rateUsButton = navigationView.findViewById(R.id.rate_us_button);
        contactUsButton = navigationView.findViewById(R.id.contact_us_button);

        // Set up menu icon to open the drawer
        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Set up the Rate Us button functionality
        rateUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(home_pg.this, Rate_us.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);  // Close drawer after clicking
        });

        // Set up the Contact Us button functionality
        contactUsButton.setOnClickListener(v -> {
            // Call your contact functionality here
            openContactUsPage();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close drawer after clicking
        });
    }

    private void openContactUsPage() {
        contactUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(home_pg.this, contact_us.class);
            startActivity(intent);
        });
    }
}
