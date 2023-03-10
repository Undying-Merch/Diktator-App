package com.example.diktatorapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.Manifest;

public class Report extends AppCompatActivity {

    Switch selfReport;
    Button searchBtn;
    boolean checkedButton, isGPSEnabled;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        selfReport = (Switch) findViewById(R.id.reportSwitch);
        searchBtn = (Button) findViewById(R.id.phoneSearchBtn);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPSEnabled) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);
        }



        checkedButton = false;

        selfReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkedButton == false) {
                    checkedButton = true;
                    searchBtn.setEnabled(false);
                } else {
                    checkedButton = false;
                    searchBtn.setEnabled(true);
                }
            }
        });

        bluetoothPerm();

        bluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null){
            dialogBox("Phone doesn't support bluetooth");
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(reciever, filter);


    }


    private final BroadcastReceiver reciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAdress = device.getAddress();
                dialogBox("Name: " + deviceName + " - Mac: " + deviceHardwareAdress);
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();

        unregisterReceiver(reciever);
    }



    public void check(View v) {

        boolean startCheck = bluetoothAdapter.startDiscovery();
        dialogBox(String.valueOf(startCheck));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Report.this);
        builder.setMessage(tekst).setPositiveButton("OK", dialogListener).show();
    }

    public void searchForBT(View view){

    }

    private void bluetoothPerm(){
        if (ContextCompat.checkSelfPermission(Report.this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 2);
                return;
            }
        }
        if (ContextCompat.checkSelfPermission(Report.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }
        if (ContextCompat.checkSelfPermission(Report.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                return;
            }
        }
        if (ContextCompat.checkSelfPermission(Report.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                return;
            }
        }

    }
}