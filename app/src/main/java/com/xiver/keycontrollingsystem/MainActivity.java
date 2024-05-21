package com.xiver.keycontrollingsystem;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key3 = "keys_history";
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @SuppressLint("SetTextI18n")
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

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
            }
        });

        TableLayout table_layout = findViewById(R.id.table_layout);

        String keysHistory = pref.getString(save_key3, "[]");

        JSONArray jsonKeysHistory;
        try {
            jsonKeysHistory = new JSONArray(keysHistory);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        TableRow fstRow = new TableRow(this);
        fstRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        String[] fst_parts = "Фамилия.Кабинет.Время выдачи.Время возврата".split("\\.");
        for (int j = 0; j < 4; j++) {
            TextView a = new TextView(this);
            a.setTextSize(20);

            a.setText(fst_parts[j] + "\t\t\t");
            fstRow.addView(a);
        }
        table_layout.addView(fstRow);

        for (int i = jsonKeysHistory.length() - 1; i >= 0; i--) {
            try {
                String[] parts = jsonKeysHistory.getString(i).split("\\|");

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 4; j++) {
                    TextView a = new TextView(this);
                    a.setTextSize(20);

                    if (parts[j].length() > 20){
                        char[] dst = new char[20];
                        String str = parts[j];
                        str.getChars(0, 20, dst, 0);
                        a.setText(String.copyValueOf(dst) + "..." + "\t\t\t");
                    } else {
                        a.setText(parts[j] + "\t\t\t");
                    }
                    row.addView(a);
                }
                table_layout.addView(row);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void init() {
        pref = getSharedPreferences("Storage123234345", MODE_PRIVATE);
    }
    public void exportCsvData(View v) throws FileNotFoundException {
        if (!this.checkPermissions()) {
            requestPermissions();
            if (!this.checkPermissions()){
                return;
            }
        }

        String keysHistory = pref.getString(save_key3, "[]");

        StringBuilder res = new StringBuilder();

        res.append("Фамилия;Кабинет;Время выдачи;Время возврата");

        JSONArray jsonKeysHistory;
        try {
            jsonKeysHistory = new JSONArray(keysHistory);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        for (int i = jsonKeysHistory.length() - 1; i >= 0; i--) {
            try {
                String[] parts = jsonKeysHistory.getString(i).split("\\|");

                res.append("\n" + parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3]);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "KeyHistory.csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        try {
            FileOutputStream writer = new FileOutputStream(f);
            writer.write(res.toString().getBytes());
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this, "Готово! Файл находится по пути: " + filePath, Toast.LENGTH_SHORT)
                .show();
    }

    public void addPeupleBtnOnClick(View v) {
        Intent intent = new Intent(this, CreatePeople.class);
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




    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readCheck = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int writeCheck = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Разрешение")
                    .setMessage("Пожалуйста, дайте разрешение на запись в хранилище")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:$s", new Object[]{getApplicationContext().getPackageName()})));
                                activityResultLauncher.launch(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                activityResultLauncher.launch(intent);
                            }
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 30);
        }
    }
}