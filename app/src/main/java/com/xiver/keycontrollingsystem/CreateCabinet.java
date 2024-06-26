package com.xiver.keycontrollingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

public class CreateCabinet extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key = "cabinets";
    private EditText cabinetNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_cabinet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void init() {
        pref = getSharedPreferences("Storage123234345", MODE_PRIVATE);

        cabinetNum = findViewById(R.id.editTextNumber);
    }


    public void onCabinetCreate(View v) throws JSONException {
        String input = cabinetNum.getText().toString();

        String users = pref.getString(save_key, "[]");
        JSONArray jsonArray = new JSONArray(users);

//        if (input.length() > 5){
//            Toast.makeText(this, "Длинна кабинета не должна превышать 5 символов", Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }

        if (input.isEmpty()){
            Toast.makeText(this, "Поле не должно быть пустым!", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        boolean fl = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getString(i).equals(input))
            {
                fl = true;
            }
        }

        if (!fl){
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = pref.edit();
            jsonArray.put(input);
            edit.putString(save_key, jsonArray.toString());
            edit.apply();
            Toast.makeText(this, "Готово!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, "Этот кабинет уже существует!", Toast.LENGTH_SHORT)
                    .show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}