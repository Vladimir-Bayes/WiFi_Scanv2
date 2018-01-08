package com.example.karl.wifi_scanv2;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by KarL on 2017/11/8.
 */

public class OpenWiFi extends Activity {
    private WifiManager wifimanager;
    private WifiInfo wifiinfo;
    List<String> current_info = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_info);
        wifimanager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiinfo = wifimanager.getConnectionInfo();
        current_info.add("BSSID: "+wifiinfo.getBSSID());
        current_info.add("SSID: "+wifiinfo.getSSID());
        current_info.add("MAC: "+wifiinfo.getMacAddress());
        current_info.add("IP: "+intIP2StringIP(wifiinfo.getIpAddress()));
        current_info.add("Rssi: "+wifiinfo.getRssi()+"dBm");
        current_info.add("LinkSpeed: "+wifiinfo.getLinkSpeed()+"Mbps");
        current_info.add("HiddenSSID: "+wifiinfo.getHiddenSSID());
        current_info.add("Network ID: "+wifiinfo.getNetworkId());
        current_info.add("Net2Str: "+wifiinfo.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,current_info);
        ListView list = (ListView) findViewById(R.id.listview2);
        list.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.button_finish_in_current);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
