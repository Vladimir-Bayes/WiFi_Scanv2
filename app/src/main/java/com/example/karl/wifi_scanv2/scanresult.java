package com.example.karl.wifi_scanv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.karl.wifi_scanv2.SDCardHelper.getSDCardPrivateFilesDir;
import static com.example.karl.wifi_scanv2.SDCardHelper.loadFileFromSDCard;


/**
 * Created by KarL on 2017/11/8.
 */

public class scanresult extends Activity {
    private WifiManager wifimanager;
    private List<ScanResult> list_scanresult;
    List<String> SSID = new ArrayList<String>();
    List<String> MAC = new ArrayList<String>();
    List<String> Frequency = new ArrayList<String>();
    List<String> RSSI = new ArrayList<String>();
    List<String> CAP = new ArrayList<String>();
    List<String> Channel = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_frame);

        //Button Finish
        Button button_finish = (Button)findViewById(R.id.button_finish);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.textview2);
                clear_data();
                textView.setText("NULL");
                finish();
            }
        });

        //Button Rescan
        Button button_rescan = (Button)findViewById(R.id.button_rescan);
        button_rescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rescan();
            }
        });

        //Button Input
        Button button_input = (Button)findViewById(R.id.button_input);
        button_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edittext = (EditText)findViewById(R.id.edit_text_1);
                String input_MAC = edittext.getText().toString();
                TextView textview = (TextView)findViewById(R.id.textview_input_echo);
                textview.setText(input_MAC);
            }
        });

        //Button Check
        Button button_check = (Button)findViewById(R.id.button_3);
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target_MACSSID;
                String str="None Found";
//                TextView textView = (TextView)findViewById(R.id.textview2);
                TextView textView1 = (TextView)findViewById(R.id.textview_input_echo);
                target_MACSSID = textView1.getText().toString();
                for(int i=0;i<MAC.size();i++){
                    if(MAC.get(i).indexOf(target_MACSSID)!=-1||SSID.get(i).indexOf(target_MACSSID)!=-1){
                        if(i%2==1){
                            str = "Check Result for Target "+target_MACSSID+": True\r\n"+"MAC: "+MAC.get(i)+"\r\nSSID: "+SSID.get(i);
//                            textView.setText(str);
                        }
                        else{
                            str = "Check Result for Target "+target_MACSSID+": True\r\n"+"MAC: "+MAC.get(i)+"\r\nSSID: "+SSID.get(i);
//                            textView.setText(str);
                        }
                        break;
                    }

                }
                Intent intent = new Intent(scanresult.this,showresult.class);
                intent.putExtra("Check_result",str);
                startActivity(intent);
            }
        });



        //Button Save
        Button button_save = (Button)findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                String filename =date+".txt";
                String filecontent = date+"\r\n";
                for (int i=0;i<MAC.size();i++){
                    filecontent = filecontent+SSID.get(i)+" \t "+MAC.get(i)+"\r\n";
                }
                SDCardHelper sdcard_manager = new SDCardHelper();
                try {
                    sdcard_manager.saveFileToSDCardPrivateFilesDir(filecontent.getBytes(), null, filename,scanresult.this);
                    Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button Open
        //用于做测试
        Button button_open = (Button)findViewById(R.id.button_open);
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edittext = (EditText)findViewById(R.id.edit_text_1);
                String filename = edittext.getText().toString();
                TextView textview = (TextView)findViewById(R.id.textview_input_echo);
                textview.setText(filename);

                try {
                    String string = getSDCardPrivateFilesDir(scanresult.this,null);
                    System.out.println("*************************");
                    System.out.println(string);
                    System.out.println("*************************");
                    byte[] temp = loadFileFromSDCard(string+File.separator+filename);
                    string = new String(temp);
                    System.out.println("*************************");
                    System.out.println(string);
                    System.out.println("*************************");
                    Intent intent = new Intent(scanresult.this,showresult.class);
                    intent.putExtra("Check_result",string);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //Button upload
        //用socket来完成文件上传
        Button button_upload = (Button)findViewById(R.id.button_upload);
        button_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run(){
                        try{

                            try{
                                String filedir = getSDCardPrivateFilesDir(scanresult.this,null);
                                System.out.println("*************************");
                                System.out.println(filedir);
                                System.out.println("*************************");
                                filedir = filedir+File.separator+"123.txt";
                                System.out.println(filedir);
                                System.out.println("*************************");
                                FileUpload.fileupload(filedir);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }}.start();
            }
        });


    }
    public void rescan(){
        wifimanager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        list_scanresult = wifimanager.getScanResults();
        clear_data();
        if(list_scanresult.size()==0){
            finish();
        }else{
            for(int i=0;i<list_scanresult.size();i++){
                SSID.add(list_scanresult.get(i).SSID);
                MAC.add(list_scanresult.get(i).BSSID);
                Frequency.add(list_scanresult.get(i).frequency+"MHz");
                RSSI.add(list_scanresult.get(i).level+"dBm");
                CAP.add(list_scanresult.get(i).capabilities);
                Channel.add(getChannelByFrequency(list_scanresult.get(i).frequency)+"");
            }
        }

        ArrayList<HashMap<String,Object>> myscanlist = new ArrayList<>();
        for (int i=0;i<MAC.size();i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Entry_Name_TextView",SSID.get(i)+"("+MAC.get(i)+")");
            map.put("Entry_Frequence_TextView",Frequency.get(i));
            map.put("Entry_Strength_TextView",RSSI.get(i));
            map.put("Entry_Capabilities",CAP.get(i));
            map.put("Entry_Channel_TextView",Channel.get(i));

            map.put("Entry_Security_ImageView",getWIFIICON(list_scanresult.get(i).level,CAP.get(i)));
            myscanlist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(this,myscanlist,R.layout.list_entry,new String[]{"Entry_Name_TextView","Entry_Frequence_TextView","Entry_Strength_TextView","Entry_Capabilities","Entry_Channel_TextView","Entry_Security_ImageView"},new int[]{R.id.Entry_Name_TextView,R.id.Entry_Frequence_TextView,R.id.Entry_Strength_TextView,R.id.Entry_Capabilities,R.id.Entry_Channel_TextView,R.id.Entry_Security_ImageView});
        ListView list = (ListView)findViewById(R.id.listview1);
        list.setAdapter(mSchedule);

//        ArrayList<HashMap<String,String>> myscanlist = new ArrayList<>();
//        for (int i=0;i<MAC.size();i++){
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("Entry_Name_TextView",SSID.get(i)+"("+MAC.get(i)+")");
//            map.put("Entry_Frequence_TextView",Frequency.get(i));
//            map.put("Entry_Strength_TextView",RSSI.get(i));
//            map.put("Entry_Capabilities",CAP.get(i));
//            map.put("Entry_Channel_TextView",Channel.get(i));
//            myscanlist.add(map);
//        }
//        SimpleAdapter mSchedule = new SimpleAdapter(this,myscanlist,R.layout.list_entry,new String[]{"Entry_Name_TextView","Entry_Frequence_TextView","Entry_Strength_TextView","Entry_Capabilities","Entry_Channel_TextView"},new int[]{R.id.Entry_Name_TextView,R.id.Entry_Frequence_TextView,R.id.Entry_Strength_TextView,R.id.Entry_Capabilities,R.id.Entry_Channel_TextView});
//        ListView list = (ListView)findViewById(R.id.listview1);
//        list.setAdapter(mSchedule);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mylistitem,data);
//        ListView list = (ListView) findViewById(R.id.listview1);
//        list.setAdapter(adapter);
    }

    /**
     * 根据频率获得信道
     * @param frequency
     * @return
     */

    public static int getChannelByFrequency(int frequency) {
        int channel = -1;
        switch (frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return channel;
    }

    /**
     * 根据信号强度和网络加密情况获得wifi图标    /**
     * 根据频率获得信道
     * @param RSSI
     * @param CAP
     * @return
     */
    public static int getWIFIICON(int RSSI,String CAP) {
        int encry = -1;
        int icon=0;

        if (CAP.indexOf("WPA")!=-1){
            encry=1;
        }
       switch (encry){
           case 1:
               if (RSSI>=-40){
                   icon=R.drawable.nm_signal_100_secure;
               }else if (RSSI>=-55){
                   icon=R.drawable.nm_signal_75_secure;
               }else if (RSSI>=-70){
                   icon=R.drawable.nm_signal_50_secure;
               }else if (RSSI>=-85){
                   icon=R.drawable.nm_signal_25_secure;
               }else {
                   icon=R.drawable.nm_signal_0_secure;
               }
               break;
           case -1:
               if (RSSI>=-40){
                   icon=R.drawable.nm_signal_100;
               }else if (RSSI>=-55){
                   icon=R.drawable.nm_signal_75;
               }else if (RSSI>=-70){
                   icon=R.drawable.nm_signal_50;
               }else if (RSSI>=-85){
                   icon=R.drawable.nm_signal_25;
               }else {
                   icon=R.drawable.nm_signal_0;
               }
               break;
               default:
                   icon=R.drawable.icon1;
                   break;
       }
        return icon;
    }


    public void clear_data()
    {
        MAC.clear();
        SSID.clear();
        Frequency.clear();
        RSSI.clear();
        CAP.clear();
        Channel.clear();
    }

    public String readFromSD(String filename) throws IOException {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator + filename;

            System.out.println("****************************");
            System.out.println(filename);
            System.out.println("****************************");


            //打开文件输入流
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];
            int len = 0;
            //读取文件内容:
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            //关闭输入流
            input.close();
        }
        return sb.toString();
    }

}
