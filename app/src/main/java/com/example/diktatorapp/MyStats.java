package com.example.diktatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.diktatorapp.Classes.Database;
import com.example.diktatorapp.Classes.ReportData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyStats extends AppCompatActivity {

    private int userId;
    Database dbobj = new Database();
    ArrayList<String> stringReportList;
    ArrayList<ReportData> reportList;
    TextView nameText, pointText;
    ListView reportView;
    private String name;
    RequestQueue requestQueue;
    ArrayAdapter<String> reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stats);
        requestQueue = Volley.newRequestQueue(this);
        userId = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        reportList = new ArrayList<>();
        stringReportList = new ArrayList<>();

        nameText = (TextView) findViewById(R.id.myStatName);
        pointText = (TextView) findViewById(R.id.myStatPoints);
        nameText.setText(name);
        pointText.setText(String.valueOf(getIntent().getIntExtra("points", 0)));

        reportView = (ListView) findViewById(R.id.ownDataList);
        reportView.setClickable(true);
        reportView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = reportView.getItemAtPosition(position).toString();

            }
        });

        getMyData();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(MyStats.this);
        builder.setMessage(tekst).setPositiveButton("OK", dialogListener).show();
    }

    private void getMyData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dbobj.getGetSusReport() + userId + "/", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String susName = jsonObject.getString("suspectId.navn");
                        String snitchName = jsonObject.getString("snitchId.navn");
                        String report = jsonObject.getString("rapportId.beskrivelse");
                        String time = jsonObject.getString("rapportId.tidspunkt");
                        ReportData data = new ReportData(susName, snitchName, report, time);
                        reportList.add(data);
                        stringReportList.add(String.valueOf(i) + ": " + susName);
                        dialogBox(susName);
                        }
                    dialogBox("Ho");
                    reportAdapter = new ArrayAdapter<String>(MyStats.this, android.R.layout.simple_list_item_1, stringReportList);
                    reportView.setAdapter(reportAdapter);

                } catch (Exception w) {
                    Toast.makeText(MyStats.this, w.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MyStats.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        requestQueue.add(jsonArrayRequest);
    }


}