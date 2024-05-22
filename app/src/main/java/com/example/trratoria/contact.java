package com.example.trratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
    public void perehod_na_menu(View v) {
        Intent intent = new Intent(this, loading.class);
        startActivity(intent);
    }
}