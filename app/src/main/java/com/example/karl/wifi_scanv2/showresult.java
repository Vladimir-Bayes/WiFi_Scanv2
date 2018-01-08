package com.example.karl.wifi_scanv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by KarL on 2017/11/8.
 */

public class showresult extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String str ="None Found";
        Intent intent = getIntent();
        str = intent.getStringExtra("Check_result");
        LinearLayout ll = new LinearLayout(this);
        ll.setPadding(20,20,20,20);
        TextView tv = new TextView(this);
        tv.setTextSize(24);
        tv.setText(str);
        ll.addView(tv);
        setContentView(ll);
    }
}
