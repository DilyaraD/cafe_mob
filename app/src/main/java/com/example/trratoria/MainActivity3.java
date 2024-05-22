package com.example.trratoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity3 extends AppCompatActivity {
    private Connection connection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

    }

    public void perehod_na_main (View v){
        Intent intent = new Intent(this, loading.class);
        startActivity(intent);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://146.19.207.159:1433;databaseName=cafe;", "sa", "DbReFoRpm228@");
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Class fail", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Connected no", Toast.LENGTH_SHORT).show();
        }
    }
}