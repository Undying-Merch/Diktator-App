package com.example.diktatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyPage extends AppCompatActivity {

    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        userName = getIntent().getStringExtra("userName");

    }
}