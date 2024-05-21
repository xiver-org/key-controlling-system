package com.xiver.keycontrollingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class CreatePeople extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key = "users";
    private EditText fio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_peuple);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        pref = getSharedPreferences("Test4", MODE_PRIVATE);

        fio = findViewById(R.id.editTextText);
    }


    public void onPeopleCreate(View v) throws JSONException {
        String input = fio.getText().toString();

        if (input.length() > 30){
            Toast.makeText(this, "ФИО не должно превышать 30 символов", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        String users = pref.getString(save_key, "[]");

        JSONArray jsonArray = new JSONArray(users);

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
            Toast.makeText(this, "Этот пользователь уже существует!", Toast.LENGTH_SHORT)
                    .show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}