package com.xiver.keycontrollingsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private TextView asd;
    private final String save_key = "cabinets";
    private final String save_key3 = "keys_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();


        String users = pref.getString(save_key3, "[]");

        asd.setText(users);
    }

    private void init() {
        pref = getSharedPreferences("Test2", MODE_PRIVATE);
        asd = findViewById(R.id.textView2);
    }

    public void addPeupleBtnOnClick(View v) {
        Intent intent = new Intent(this, CreatePeuple.class);
        startActivity(intent);
    }

    public void givKeyBtnOnClick(View v) {
        Intent intent = new Intent(this, GiveKey.class);
        startActivity(intent);
    }

    public void returnKeyBtnOnClick(View v) {
        Intent intent = new Intent(this, ReturnKey.class);
        startActivity(intent);
    }
    public void createCabinetKeyBtnOnClick(View v) {
        Intent intent = new Intent(this, CreateCabinet.class);
        startActivity(intent);
    }
}