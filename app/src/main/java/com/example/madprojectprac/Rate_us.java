package com.example.madprojectprac;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Rate_us extends AppCompatActivity {
    RatingBar facilityRating;
    Button submitRatingBtn,backh;
    TextView tv;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us); // Ensure this matches your XML filename
        db = new DBHelper(this);

        facilityRating = findViewById(R.id.facilityRating);
        submitRatingBtn = findViewById(R.id.submitRatingBtn);
        backh = findViewById(R.id.backh);
        backh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rate_us.this, home_pg.class);
                startActivity(intent);
            }
        });
        tv = findViewById(R.id.tv);
        String str = "Our grocery store navigation app designed to simplify your shopping experience. \n\n"+
                "Our mission is to help you effortlessly locate products, saving you time and reducing the stress of grocery shopping.\n\n"+
                "With a user-friendly interface, ShopMate allows you to find your favorite items in the store with ease. Whether you’re searching for fresh produce, dairy, or snacks, our app provides clear navigation through the aisles to ensure you can quickly locate what you need.";
        tv.setGravity(Gravity.CENTER);
        tv.setText(str);
        submitRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = facilityRating.getRating();

                // Provide feedback to the user based on their rating submission
                if (rating > 0) {
                    Toast.makeText(Rate_us.this, "Thank you for your feedback! You rated us " + rating + " stars.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Rate_us.this, "Please select a rating before submitting.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
