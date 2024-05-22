package com.xiver.keycontrollingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RemoveCabinet extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key = "cabinets";
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_cabinet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = (Spinner) findViewById(R.id.spinner4);


        pref = getSharedPreferences("Storage123234345", MODE_PRIVATE);

        String keys = pref.getString(save_key, "[]");

        if (keys.equals("[]")) {
            Toast.makeText(this, "В базе нет ключей!", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        JSONArray jsonUsers;
        try {
            jsonUsers = new JSONArray(keys);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        List<String> list = new ArrayList<String>();
        int len = jsonUsers.length();
        for (int i=0;i < len;i++){
            try {
                list.add(jsonUsers.get(i).toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Выберите кабинет");
    }


    public void onClick(View v) throws JSONException {
        String cabinet = (String) spinner.getSelectedItem();

        if (cabinet.isEmpty()) {
            Toast.makeText(this, "Поле не может быть пустым!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        String jsonString = pref.getString(save_key, "[]");
        JSONArray keys = new JSONArray(jsonString);

        JSONArray res = new JSONArray();
        for (int i = 0; i < keys.length(); i++) {
            if (!keys.getString(i).equals(cabinet)) {
                res.put(keys.getString(i));
            }
        }
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = pref.edit();
        edit.putString(save_key, res.toString());
        edit.apply();

        Toast.makeText(this, "Готово!", Toast.LENGTH_SHORT)
                .show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}