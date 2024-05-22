package com.example.trratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }
    public void perehod_na_menu (View v){
        Intent intent = new Intent(this, starter.class);
        startActivity(intent);
    }
    public void perehod_na_onas (View v){
        Intent intent = new Intent(this, load.class);
        startActivity(intent);
    }
    public void perehod_na_contact (View v){
        Intent intent = new Intent(this, contact.class);
        startActivity(intent);
    }
    public void perehod_na_pop (View v){
        Intent intent = new Intent(this, populardishes.class);
        startActivity(intent);
    }
    public void perehod_na_rezerv (View v){
        Intent intent = new Intent(this, Rezerv.class);
        startActivity(intent);
    }
    public void launchAbout(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/maps/43/kazan/house/bolshaya_krasnaya_ulitsa_55/YEAYdwRjQUAPQFtvfXt4d3VnYw==/?ll=49.133857%2C55.796947&z=16"));
        startActivity(browserIntent);
    }
}