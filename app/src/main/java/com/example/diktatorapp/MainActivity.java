package com.example.diktatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.diktatorapp.Classes.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText userText, passText;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    RequestQueue requestQueue;
    Database dbobj = new Database();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        userText = findViewById(R.id.loginUserName);
        passText = findViewById(R.id.loginPassword);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.loginRemember);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        //Check if there is a saved username
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            userText.setText(loginPreferences.getString("username", ""));
            //If we want to remember password
            //passText.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

    }
    //Intent for going to create activity
    public void gotoCreate(View view){
        Intent intent = new Intent(MainActivity.this, createUser.class);
        startActivity(intent);

    }
    //View used for login button
    public void logon(View view){
        String userName = userText.getText().toString();
        String pass = passText.getText().toString();

        loginCheck(userName, pass);

    }

    public void dialogBox(String tekst){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(tekst).setPositiveButton("OK", dialogListener).show();
    }
    //Function to check login data in DB
    private void loginCheck(String brugernavnInput, String passwordInput) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dbobj.getGetListe(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                boolean userTrue = false;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String brugernavn = jsonObject.getString("brugernavn");
                        if (brugernavn.matches(brugernavnInput)){
                            String password = jsonObject.getString("password");
                            userTrue = true;
                            if (password.matches(passwordInput)){

                                //Checks if checkbox is marked, to remember username
                                if (saveLoginCheckBox.isChecked()) {
                                    loginPrefsEditor.putBoolean("saveLogin", true);
                                    loginPrefsEditor.putString("username", brugernavnInput);
                                    //If we want to remember password as well
                                    //loginPrefsEditor.putString("password", passwordInput);
                                    loginPrefsEditor.commit();
                                } else {
                                    loginPrefsEditor.clear();
                                    loginPrefsEditor.commit();
                                }

                                Intent intent = new Intent(MainActivity.this, MyPage.class);
                                intent.putExtra("userName", brugernavn);
                                startActivity(intent);
                            }
                            else{
                                dialogBox("Incorrect password");
                            }
                        }
                    }
                    if (userTrue != true){
                        dialogBox("User does not exist");
                    }
                } catch (Exception w) {
                    Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Token " + dbobj.getToken());
                return headers;
            }

        };
        requestQueue.add(jsonArrayRequest);
    }

}