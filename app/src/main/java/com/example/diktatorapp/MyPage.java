package com.example.diktatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.diktatorapp.Classes.Database;
import com.example.diktatorapp.Classes.Persons;
import com.example.diktatorapp.Classes.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kotlinx.coroutines.Delay;

public class MyPage extends AppCompatActivity {

    String username = "";
    Persons person;
    Settings appSetting;
    Database dbobj = new Database();
    RequestQueue requestQueue;
    ProgressBar pointProgress;
    TextView pointText, nameText;
    Button superBTN;
    private SharedPreferences loginPreferences, currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        requestQueue = Volley.newRequestQueue(this);

        pointProgress = findViewById(R.id.pointBar);
        nameText = findViewById(R.id.mySiteName);
        pointText = findViewById(R.id.pointText);
        superBTN = findViewById(R.id.otherStatBTN);
        superBTN.setEnabled(false);
        superBTN.setVisibility(View.INVISIBLE);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        currentUser = getSharedPreferences("userPref", MODE_PRIVATE);

        person = new Persons();
        appSetting = new Settings(getIntent().getIntExtra("pointThreshold" , 500), getIntent().getIntExtra("startUpPoints", 300));

        getUser(currentUser.getString("userName", "Test"));


    }
    private void setView(String name, int points){
        nameText.setText(name);
        pointText.setText("Your points: " + points);
        pointProgress.setProgress(points / 7);
    }

    public void dialogBox(String tekst) {
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MyPage.this);
        builder.setMessage(tekst).setPositiveButton("OK", dialogListener).show();
    }

    private void getUser(String brugernavn) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, dbobj.getGetPerson() + brugernavn + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject  = response;
                try {

                    person.setId(jsonObject.getInt("id"));
                    person.setName(jsonObject.getString("navn"));
                    person.setAddress(jsonObject.getString("adresse"));
                    person.setMail(jsonObject.getString("mail"));
                    person.setPhone(jsonObject.getInt("tlf"));
                    person.setZip(jsonObject.getInt("postnummer"));
                    person.setPoints(jsonObject.getInt("point"));
                    person.setCpr(jsonObject.getString("cpr"));
                    person.setUserName(jsonObject.getString("brugernavn"));
                    person.setPassword(jsonObject.getString("password"));
                    person.setWorksector(jsonObject.getInt("worksector"));
                    setView(person.getName(), person.getPoints());

                    if (person.getPoints() >= appSetting.getPointThreshold()){
                        superBTN.setEnabled(true);
                        superBTN.setVisibility(View.VISIBLE);
                    }

                } catch (Exception w) {
                    Toast.makeText(MyPage.this, w.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyPage.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialogBox(error.getMessage());
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
        requestQueue.add(jsonObjectRequest);
    }


    public void view(View view){
        dialogBox(person.getName());
    }
    public void seeOwnData(View view){
        Intent intent = new Intent(MyPage.this, MyStats.class);
        intent.putExtra("id", person.getId());
        intent.putExtra("userName", person.getUserName());
        intent.putExtra("name", person.getName());
        intent.putExtra("points", person.getPoints());
        startActivity(intent);
    }
    public void goToReport(View view){
        Intent intent = new Intent(MyPage.this, Report.class);
        startActivity(intent);
    }




}