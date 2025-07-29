package com.kelas.konversisuhu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText temperatureInput;
    private Spinner conversionSpinner;
    private Button convertButton;
    private TextView resultTextView;

    // Array to hold conversion options
    private String[] conversionOptions = {"Celcius to Fahrenheit", "Fahrenheit to Celcius",
            "Celcius to Reamur", "Reamur to Celcius",
            "Celcius to Kelvin", "Kelvin to Celcius"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        temperatureInput = findViewById(R.id.editTextNumberDecimal);
        conversionSpinner = findViewById(R.id.spinner);
        convertButton = findViewById(R.id.button);
        resultTextView = findViewById(R.id.textView4);

        // Setup Spinner (Dropdown)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, conversionOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionSpinner.setAdapter(adapter);

        // Set up the button click listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        String inputStr = temperatureInput.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Masukkan suhu terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double inputValue = Double.parseDouble(inputStr);
            double result = 0.0;
            String unit = "";

            int selectedConversion = conversionSpinner.getSelectedItemPosition();

            switch (selectedConversion) {
                case 0: // Celcius to Fahrenheit
                    result = (inputValue * 9 / 5) + 32;
                    unit = "°F";
                    break;
                case 1: // Fahrenheit to Celcius
                    result = (inputValue - 32) * 5 / 9;
                    unit = "°C";
                    break;
                case 2: // Celcius to Reamur
                    result = inputValue * 4 / 5;
                    unit = "°Ré";
                    break;
                case 3: // Reamur to Celcius
                    result = inputValue * 5 / 4;
                    unit = "°C";
                    break;
                case 4: // Celcius to Kelvin
                    result = inputValue + 273.15;
                    unit = "K";
                    break;
                case 5: // Kelvin to Celcius
                    result = inputValue - 273.15;
                    unit = "°C";
                    break;
            }
            resultTextView.setText(String.format("%.2f %s", result, unit));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Masukkan angka yang valid!", Toast.LENGTH_SHORT).show();
        }
    }
}