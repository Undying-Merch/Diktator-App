package com.example.diktatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    private String url = "http://10.130.54.25:8000/data/liste/?format=json";
    private String token = "025ae88c5625e6d0718dfe63edb05b387159a872";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void logon(View view){
        EditText userText = (EditText) findViewById(R.id.loginUserName);
        EditText passText = (EditText) findViewById(R.id.loginPassword);
        TextView testText = (TextView) findViewById(R.id.loginTest);
        String userName = userText.getText().toString();
        String pass = passText.getText().toString();

        loginCheck(userName, pass);

    }

    private void loginCheck(String brugernavnInput, String passwordInput) {
        TextView testText = (TextView) findViewById(R.id.loginTest);
        TextView testText2 = (TextView) findViewById(R.id.loginTest2);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String brugernavn = jsonObject.getString("brugernavn");
                        String password = jsonObject.getString("password");
                        if (brugernavn.matches(brugernavnInput) && password.matches(passwordInput)){
                            Toast.makeText(MainActivity.this, "Hello there", Toast.LENGTH_SHORT).show();
                        }
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
                headers.put("Authorization", "Token " + token);
                return headers;
            }

        };
        requestQueue.add(jsonArrayRequest);
    }

}