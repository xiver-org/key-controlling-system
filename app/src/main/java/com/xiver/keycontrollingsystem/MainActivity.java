package com.xiver.keycontrollingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xiver.keycontrollingsystem.network.Network;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Network network = new Network();

        if(network.isLogged()) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void loginBtnTriggerr(View v) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void registrationTextTriggerr(View v) {
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }
}