package com.xiver.keycontrollingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveKey extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key = "users";
    private final String save_key2 = "cabinets";
    private final String save_key3 = "keys_history";


    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_give_key);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        pref = getSharedPreferences("Storage123234345", MODE_PRIVATE);

        String users = pref.getString(save_key2, "[]");
        JSONArray jsonUsers;
        try {
            jsonUsers = new JSONArray(users);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        List<String> list = new ArrayList<String>();
        if (jsonUsers != null) {
            int len = jsonUsers.length();
            for (int i=0;i < len;i++){
                try {
                    list.add(jsonUsers.get(i).toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setPrompt("Выберите человека");

        String cabinets = pref.getString(save_key, "[]");
        JSONArray jsonCabinets;
        try {
            jsonCabinets = new JSONArray(cabinets);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        List<String> list2 = new ArrayList<String>();
        if (jsonCabinets != null) {
            int len = jsonCabinets.length();
            for (int i=0;i < len;i++){
                try {
                    list2.add(jsonCabinets.get(i).toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setPrompt("Выберите кабинет");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onGiveBtnClick(View v) throws JSONException {
        String cabinet = (String) spinner1.getSelectedItem();
        String fio = (String) spinner2.getSelectedItem();

        String jsonString = pref.getString(save_key3, "[]");
        JSONArray keysHistory = new JSONArray(jsonString);
        LocalDate currentDate = LocalDate.now();
        String[] currentTime = LocalTime.now().toString().split("\\.");
        currentTime = Arrays.copyOfRange(currentTime[0].split(":"), 0, 2);
        keysHistory.put(fio + "|" + cabinet + "|" + currentDate.toString() + " " + currentTime[0] + ":" + currentTime[1] + "|" + " ");
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = pref.edit();
        edit.putString(save_key3, keysHistory.toString());
        edit.apply();
        Toast.makeText(this, "Готово!", Toast.LENGTH_SHORT)
                .show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}