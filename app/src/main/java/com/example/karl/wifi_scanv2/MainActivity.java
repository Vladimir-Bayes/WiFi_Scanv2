package com.example.karl.wifi_scanv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.*;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private WifiManager wifimanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifimanager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Button button_open = (Button)findViewById(R.id.button_1);
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wifimanager.isWifiEnabled()){
                    wifimanager.setWifiEnabled(true);
                }
                Intent intent = new Intent(MainActivity.this,OpenWiFi.class);
                startActivity(intent);
            }
        });
        Button button_close = (Button) findViewById(R.id.button_4);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wifimanager.isWifiEnabled()){
                    wifimanager.setWifiEnabled(false);
                }
            }
        });

        Button button_exit = (Button)findViewById(R.id.button_5);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });

        Button button_scan = (Button)findViewById(R.id.button_2);
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wifimanager.isWifiEnabled()){
                    wifimanager.setWifiEnabled(true);
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, scanresult.class);
                startActivity(intent);
            }
        });

    }
}
