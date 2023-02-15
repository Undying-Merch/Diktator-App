package com.example.diktatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.diktatorapp.Classes.Persons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class createUser extends AppCompatActivity {

    EditText input1, input2, input3;
    Button createBNT;
    //Used for how long a phone number should be
    public int phoneLenght = 8;
    //Used for how long a social security number should be
    public int cprLenght = 10;
    boolean stage1 = false;
    boolean stage2 = false;
    boolean userInUse = false;
    boolean cprInUse = false;

    RequestQueue requestQueue;
    Database dbobj = new Database();
    Persons person = new Persons();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        requestQueue = Volley.newRequestQueue(this);

        input1 = (EditText) findViewById(R.id.createInput1);
        input2 = (EditText) findViewById(R.id.createInput2);
        input3 = (EditText) findViewById(R.id.createInput3);
        createBNT = (Button) findViewById(R.id.createUserBTN);
    }

    //A function for checking a string is of the right lenght.
    public boolean checkLenght(int lenght, String lenghtChekcer){
        boolean isCorrect = true;
        if (lenghtChekcer.length() != lenght){
            isCorrect = false;
            Toast.makeText(this, "The number you've entered isn't correct", Toast.LENGTH_SHORT).show();
        }
        return isCorrect;
    }

    //A function to check if any boxes has an empty string
    public boolean checkForEmpty(){
        boolean check = true;

        if (input1.getText().toString().matches("") || input2.getText().toString().matches("") || input3.getText().toString().matches("")){
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    //A function for clearing input fields
    public void clearInput(){
        input1.setText("");
        input2.setText("");
        input3.setText("");
    }

    //A function meant to progress the stages of creating a user
    public void nextStage1(){
        person.setName(input1.getText().toString());
        person.setUserName(input2.getText().toString());
        person.setCpr(input3.getText().toString());
        clearInput();
        stage1 = true;
        input1.setHint("Mail:");
        input2.setHint("Address:");
        input3.setHint("Zip-Code:");
    }

    //The function called when pressing the button, for creating user
    public void createUser(View view) throws JSONException {

        //First stage, where it checks if the user allready exist in the DB
        if (stage1 == false && stage2 == false){
            if (checkForEmpty() == true && checkLenght(cprLenght, input3.getText().toString()) == true){
                existingUserCheck(input2.getText().toString(), input3.getText().toString());
            }
        }
        //Stage 2 where we get some more info
        else if (stage1 == true && stage2 == false) {
            if (checkForEmpty() == true){
                person.setMail(input1.getText().toString());
                person.setAddress(input2.getText().toString());
                String zipHolder = input3.getText().toString();
                person.setZip(Integer.parseInt(zipHolder));
                input1.setHint("Password:");
                input2.setHint("Re-Enter Password:");
                input3.setHint("Phone:");
                createBNT.setText("Create user");
                clearInput();
                stage1 = false;
                stage2 = true;
            }
        }
        //The final stage where it will send the data to the DB
        else if (stage1 == false && stage2 == true && checkLenght(phoneLenght, input3.getText().toString()) == true) {
            if (input1.getText().toString().matches(input2.getText().toString())){
                createUserData(input1.getText().toString(), input3.getText().toString());
            }
            else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        }


    }

    //A DB call to check for existing users
    private void existingUserCheck(String usernameInput, String cprInput) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dbobj.getGetCPR(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String brugernavn = jsonObject.getString("brugernavn");
                        String cpr = jsonObject.getString("cpr");
                        String cprString = String.valueOf(cpr);
                        //If statements to check if a user already exists.
                        if (usernameInput.matches(brugernavn)) {
                            userInUse = true;
                        }
                        if (cpr.matches(cprInput)){
                            cprInUse = true;
                        }
                    }
                } catch (Exception w) {
                    Toast.makeText(createUser.this, w.getMessage(), Toast.LENGTH_LONG);
                }
                if (userInUse == false && cprInUse == false){
                    nextStage1();
                }
                else {
                    Toast.makeText(createUser.this, "User already exists", Toast.LENGTH_SHORT).show();
                    cprInUse = false;
                    userInUse = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(createUser.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    //The function to create the user in DB.
    private void createUserData(String pass, String phone) {
        StringRequest request = new StringRequest(Request.Method.POST, dbobj.getPostPerson(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //A dialog window to show the user has been created, and sending the user back to login.
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                createUser.this.finish();
                                break;
                        }
                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(createUser.this);
                builder.setMessage("User created, returning to login.").setPositiveButton("OK", dialogListener).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(createUser.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders()  {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Token " + dbobj.getToken());
                return headers;
            }
            ////
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("navn", person.getName());
                params.put("adresse", person.getAddress());
                params.put("mail", person.getMail());
                params.put("tlf", phone);
                params.put("postnummer", String.valueOf(person.getZip()));
                params.put("point", "400");
                params.put("cpr", String.valueOf(person.getCpr()));
                params.put("brugernavn", person.getUserName());
                params.put("password", pass);
                return params; //return the parameters
            }
        };
        requestQueue.add(request);
    }
}