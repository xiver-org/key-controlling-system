package com.xiver.keycontrollingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReturnKey extends AppCompatActivity {

    private final String save_key3 = "keys_history";
    private Spinner spinner;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_return_key);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pref = getSharedPreferences("Storage123234345", MODE_PRIVATE);

        String keysHistory = pref.getString(save_key3, "[]");

        JSONArray jsonKeysHistory;
        try {
            jsonKeysHistory = new JSONArray(keysHistory);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        List<String> list2 = new ArrayList<String>();

        for (int i = 0; i < jsonKeysHistory.length(); i++) {
            try {
                String[] parts = jsonKeysHistory.getString(i).split("\\|");
                if (parts[3].equals(" ") && !list2.contains(parts[1])) {
                    list2.add(parts[1]);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        spinner = (Spinner) findViewById(R.id.spinner3);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        spinner.setPrompt("Выберите кабинет");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onGiveBtnClick(View v) throws JSONException {
        String cabinet = (String) spinner.getSelectedItem();

        String jsonString = pref.getString(save_key3, "[]");
        JSONArray keysHistory = new JSONArray(jsonString);
        LocalDate currentDate = LocalDate.now();
        String[] currentTime = LocalTime.now().toString().split("\\.");
        currentTime = Arrays.copyOfRange(currentTime[0].split(":"), 0, 2);

        JSONArray res = new JSONArray();

        for (int i = 0; i < keysHistory.length(); i++) {
            try {
                String[] parts = keysHistory.getString(i).split("\\|");

                if (parts[1].equals(cabinet)){
                    parts[3] = currentDate.toString() + " " + currentTime[0] + ":" + currentTime[1];
                }

                StringBuilder rs = new StringBuilder();

                for (int j = 0; j < parts.length; j++) {
                    if (j != parts.length - 1){
                        rs.append(parts[j]).append("|");
                    } else {
                        rs.append(parts[j]);
                    }
                }

                res.put(rs);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = pref.edit();
        edit.putString(save_key3, res.toString());
        edit.apply();
        Toast.makeText(this, "Готово!", Toast.LENGTH_SHORT)
                .show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}