package com.example.diktatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyPage extends AppCompatActivity {

    Persons person;
    Database dbobj = new Database();
    RequestQueue requestQueue;
    ProgressBar pointProgress;
    TextView pointText, nameText;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        requestQueue = Volley.newRequestQueue(this);

        pointProgress = findViewById(R.id.pointBar);
        nameText = findViewById(R.id.mySiteName);
        pointText = findViewById(R.id.pointText);

        user = getIntent().getStringExtra("userName");
        getUser(user);



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


                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("navn");
                    String address = jsonObject.getString("adresse");
                    String mail = jsonObject.getString("mail");
                    int phone = jsonObject.getInt("tlf");
                    int zip = jsonObject.getInt("postnummer");
                    int point = jsonObject.getInt("point");
                    String cpr = jsonObject.getString("cpr");
                    String userName = jsonObject.getString("brugernavn");
                    String pass = jsonObject.getString("password");
                    person = new Persons(id, name, address, mail, phone, zip, point, cpr, userName, pass);

                    setView(person.getName(), person.getPoints());

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
        getUser(user);
    }


}