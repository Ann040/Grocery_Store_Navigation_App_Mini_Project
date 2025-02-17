package com.example.madprojectprac;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class contact_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        // Email TextView - Implicit Intent to open an email app
        TextView emailTextView = findViewById(R.id.email);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:sam060618183030@gmail.com")); // Use your actual email
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });

        // Contact Us TextView - Implicit Intent to open the call dialer
        TextView contactUsTextView = findViewById(R.id.contact);
        contactUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+91xxxxxxxxxx")); // Use your actual contact number
                startActivity(callIntent);
            }
        });
    }
}