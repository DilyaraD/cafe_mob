package com.example.trratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dinner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
    }
    public void perehod_na_pizza(View v) {
        Intent intent = new Intent(this, pizza.class);
        startActivity(intent);
    }
    public void perehod_na_starter(View v) {
        Intent intent = new Intent(this, starter.class);
        startActivity(intent);
    }
    public void perehod_na_drinks(View v) {
        Intent intent = new Intent(this, drinks.class);
        startActivity(intent);
    }
    public void perehod_na_desert(View v) {
        Intent intent = new Intent(this, desertigl.class);
        startActivity(intent);
    }
    public void perehod_na_menu(View v) {
        Intent intent = new Intent(this, loading.class);
        startActivity(intent);
    }
}