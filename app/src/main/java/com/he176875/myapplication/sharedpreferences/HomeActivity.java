package com.he176875.myapplication.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.he176875.myapplication.R;

public class HomeActivity extends AppCompatActivity {
    TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloText = findViewById(R.id.lbOutput);

        // Lấy dữ liệu từ Intent
        String name = getIntent().getStringExtra("username");
        if (name == null || name.isEmpty()) {
            // Nếu không có dữ liệu từ intent, thử lấy từ SharedPreferences
            SharedPreferences mypref = getSharedPreferences("mysave", MODE_PRIVATE);
            name = mypref.getString("ls", "User");
        }

        helloText.setText("Hello " + name);
    }
}
