package com.kelas.messagedialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare Button variables
    private Button buttonToast;
    private Button buttonAlertDialog;
    private Button buttonAlertDialogButton;

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

        // Initialize buttons by finding their IDs from the layout
        buttonToast = findViewById(R.id.button2);
        buttonAlertDialog = findViewById(R.id.button3);
        buttonAlertDialogButton = findViewById(R.id.button4);

        // Set OnClickListener for the Toast button
        buttonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a simple Toast message
                Toast.makeText(MainActivity.this, "This is a Toast message!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener for the Alert Dialog button
        buttonAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show a basic AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Simple Alert Dialog");
                builder.setMessage("This is a basic alert dialog message.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action to perform when OK is clicked
                        Toast.makeText(MainActivity.this, "OK button clicked", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // Set OnClickListener for the Alert Dialog with Buttons button
        buttonAlertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show an AlertDialog with multiple buttons
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert Dialog with Buttons");
                builder.setMessage("Choose an option:");

                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Accept clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Decline clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNeutralButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Later clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialogWithButtons = builder.create();
                alertDialogWithButtons.show();
            }
        });
    }
}