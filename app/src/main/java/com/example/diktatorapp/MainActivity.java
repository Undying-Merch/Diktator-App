package com.example.diktatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String userName = "";
    private String pass = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logon(View view){
        EditText userText = (EditText) findViewById(R.id.loginUserName);
        EditText passText = (EditText) findViewById(R.id.loginPassword);
        TextView testText = (TextView) findViewById(R.id.loginTest);
        userName = userText.getText().toString();
        pass = passText.getText().toString();


    }
}