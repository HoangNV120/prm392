package com.he176875.myapplication.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.he176875.myapplication.R;

public class MainTwoActivity extends AppCompatActivity {

    EditText username, password;
    TextView test;
    Button btnResult;

    String mySave= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_two);
        username = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPass);
        btnResult = findViewById(R.id.btnLogin);
        test = findViewById(R.id.lbOutput);

        SharedPreferences mypref = getSharedPreferences("mysave", MODE_PRIVATE);
        mySave = mypref.getString("ls", "");
        test.setText(mySave);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                mySave = user;

                // Lưu vào SharedPreferences
                SharedPreferences mypref = getSharedPreferences("mysave", MODE_PRIVATE);
                SharedPreferences.Editor myedit = mypref.edit();
                myedit.putString("ls", mySave);
                myedit.apply();

                // Gửi sang HomeActivity
                Intent intent = new Intent(MainTwoActivity.this, HomeActivity.class);
                intent.putExtra("username", mySave); // truyền dữ liệu
                startActivity(intent);
                finish(); // Đóng MainActivity nếu muốn
            }
        });

    }

    //Luu du lieu
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mypref = getSharedPreferences("mysave", MODE_PRIVATE);
        SharedPreferences .Editor myedit = mypref.edit();
        myedit.putString("ls", mySave);
        myedit.commit();
    }
}