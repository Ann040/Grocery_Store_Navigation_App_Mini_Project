package com.example.madprojectprac;


import android.app.DatePickerDialog;
import android.content.Intent;  // Import Intent for navigation
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    EditText username, email, password;
    TextView birthdateText;
    Button loginSignupBtn, switchModeBtn, birthdateBtn;
    DBHelper DB;
    boolean isLoginMode = true;  // Initially in login mode

    private EditText passwordEditText;
    private TextView passwordStrengthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        birthdateText = findViewById(R.id.birthdateText);
        loginSignupBtn = findViewById(R.id.loginSignupBtn);
        switchModeBtn = findViewById(R.id.switchModeBtn);
        birthdateBtn = findViewById(R.id.birthdateBtn);
        DB = new DBHelper(this);


        passwordEditText = findViewById(R.id.password);  // Password input field
        passwordStrengthTextView = findViewById(R.id.passwordStrengthTextView);  // TextView for feedback

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String password = charSequence.toString();
                validatePasswordStrength(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        // Switch between login and signup mode
        switchModeBtn.setOnClickListener(view -> toggleMode());


        // Pick birthdate for new users
        birthdateBtn.setOnClickListener(view -> showDatePickerDialog());


        // Handle login or signup
        loginSignupBtn.setOnClickListener(view -> {
            String user = username.getText().toString();
            String mail = email.getText().toString();
            String pass = password.getText().toString();
            String birthdate = birthdateText.getText().toString();

            // Input validation
            if (user.isEmpty() || pass.isEmpty() || (!isLoginMode && (mail.isEmpty() || birthdate.isEmpty()))) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isLoginMode && !isValidEmail(mail)) {  // Only check email format in signup mode
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isLoginMode) {
                // Login validation
                if (DB.validateLogin(user, pass)) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Navigate to ActivityHomePage
                    Intent intent = new Intent(MainActivity.this, home_pg.class);
                    startActivity(intent);
                    finish(); // Optional: to close MainActivity

                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Check if email or username exists
                if (DB.checkUsernameOrEmail(user, mail)) {
                    Toast.makeText(this, "Username or Email already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Insert new user
                    if (DB.insertData(user, mail, pass, birthdate)) {
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        toggleMode();  // Switch back to login mode

                        // Save user details in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.putString("email", mail);          // Save the email
                        editor.putString("birthdate", birthdate);  // Save the birthdate
                        editor.apply();

                        // Debugging check
                        String savedUsername = sharedPreferences.getString("username", "default");
                        Log.d("DEBUG_PREF", "Username saved in SharedPreferences: " + savedUsername);

                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    // Switch between Login and Signup
    private void toggleMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            email.setVisibility(View.GONE);
            birthdateText.setVisibility(View.GONE);
            birthdateBtn.setVisibility(View.GONE);
            loginSignupBtn.setText("Login");
            switchModeBtn.setText("New User? Sign Up");
        } else {
            email.setVisibility(View.VISIBLE);
            birthdateText.setVisibility(View.VISIBLE);
            birthdateBtn.setVisibility(View.VISIBLE);
            loginSignupBtn.setText("Sign Up");
            switchModeBtn.setText("Already a user? Login");
        }
    }


    // Show DatePicker for birthdate selection
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> birthdateText.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            passwordStrengthTextView.setText("Weak Password: Must be at least 8 characters.");
            passwordStrengthTextView.setTextColor(getResources().getColor(R.color.red, null));  // Red for weak
        } else if (!password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordStrengthTextView.setText("Weak Password: Include uppercase, number, and special character.");
            passwordStrengthTextView.setTextColor(getResources().getColor(R.color.red, null));  // Red for weak
        } else {
            passwordStrengthTextView.setText("Strong Password!");
            passwordStrengthTextView.setTextColor(getResources().getColor(R.color.green, null));  // Green for strong
        }
    }
}


