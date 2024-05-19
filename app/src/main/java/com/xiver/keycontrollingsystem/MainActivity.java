package com.xiver.keycontrollingsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
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

        TableLayout table_layout = findViewById(R.id.table_layout);

        String keysHistory = pref.getString(save_key3, "[]");

        JSONArray jsonKeysHistory;
        try {
            jsonKeysHistory = new JSONArray(keysHistory);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < jsonKeysHistory.length(); i++) {
            try {
                String[] parts = jsonKeysHistory.getString(i).split("\\|");

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 4; j++) {
                    TextView a = new TextView(this);
                    a.setTextSize(20);

                    a.setText(parts[j] + "\t\t\t\t\t\t\t\t\t\t");
                    row.addView(a);
                }
                table_layout.addView(row);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void init() {
        pref = getSharedPreferences("Test2", MODE_PRIVATE);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//
//        LinearLayout linear_layout = findViewById(R.id.linear_layout);
//
//        String keysHistory = pref.getString(save_key3, "[]");
//
//        JSONArray jsonKeysHistory;
//        try {
//            jsonKeysHistory = new JSONArray(keysHistory);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (int i = 0; i < jsonKeysHistory.length(); i++) {
//            try {
//                String[] parts = jsonKeysHistory.getString(i).split("\\|");
//
//                LinearLayout row = new LinearLayout(this);
//                row.setBaselineAligned(true);
//                for (int j = 0; j < 4; j++) {
//                    TextView a = new TextView(this);
//                    a.setText(parts[j]);
//                    row.addView(a);
//                }
//                linear_layout.addView(row);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

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