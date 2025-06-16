package com.he176875.myapplication.internalstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.he176875.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvRawContent;
    private TextView tvFileContent;
    private Button btnReadRaw;
    private Button btnFileIO;

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

        // Initialize UI components
        tvRawContent = findViewById(R.id.tvRawContent);
        tvFileContent = findViewById(R.id.tvFileContent);
        btnReadRaw = findViewById(R.id.btnReadRaw);
        btnFileIO = findViewById(R.id.btnFileIO);

        // Set up button click listeners
        btnReadRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromRawResource();
            }
        });

        btnFileIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeAndReadInternalFile();
            }
        });
    }

    // Example 1: Using openRawResource to read a file from the raw resources
    private void readFromRawResource() {
        try {
            // Get the raw resource as an input stream
            InputStream inputStream = getResources().openRawResource(R.raw.sample_text);

            // Read the content from the input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Close the resources
            reader.close();
            inputStream.close();

            // Display the content
            tvRawContent.setText(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
            tvRawContent.setText("Error reading raw resource: " + e.getMessage());
        }
    }

    // Example 2: Using openFileOutput to write to internal storage and openFileInput to read from it
    private void writeAndReadInternalFile() {
        String filename = "example_file.txt";
        String content = "This is a test file written using openFileOutput and read using openFileInput.";

        // Part 1: Write to internal storage using openFileOutput
        try {
            FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            tvFileContent.setText("Error writing file: " + e.getMessage());
        }

        // Part 2: Read from internal storage using openFileInput
        try {
            FileInputStream inputStream = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();
            inputStream.close();

            tvFileContent.setText(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
            tvFileContent.setText("Error reading file: " + e.getMessage());
        }
    }
}