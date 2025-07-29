package com.kelas.counter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View; // Import View
import android.widget.Button; // Import Button
import android.widget.TextView; // Import TextView

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView counterTextView; // Declare TextView
    private Button btnUp; // Declare Button for UP
    private Button btnDwn; // Declare Button for DOWN
    private int count = 0; // Initialize a counter variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        counterTextView = findViewById(R.id.count);
        btnUp = findViewById(R.id.btnUP);
        btnDwn = findViewById(R.id.btnDWN);

        // Set initial text
        counterTextView.setText(String.valueOf(count));

        // Set OnClickListener for UP button
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount();
            }
        });

        // Set OnClickListener for DOWN button
        btnDwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount();
            }
        });
    }

    // Method to increment the count
    private void incrementCount() {
        count++;
        counterTextView.setText(String.valueOf(count));
    }

    // Method to decrement the count
    private void decrementCount() {
        count--;
        counterTextView.setText(String.valueOf(count));
    }
}