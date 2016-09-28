package com.example.macavilang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macavilang.jaguarfund_android.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
        String urlToken = preferences.getString("token",null);
        if(urlToken == null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
            startActivity(intent);
        }




    }

}
