package com.example.karl.wifi_scanv2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by KarL on 2018/1/9.
 */

public class FileUpload {
    public static final String ipaddress = "222.205.110.51";
    public static final int portnumber = 12345;

    public static void filenameupload(String filename) throws  IOException {

        DataOutputStream dos =null;

        Socket socket = null;
        try{
            socket=new Socket("10.13.86.73",FileUpload.portnumber);
            //向台式机的5018端口发出客户请求

            dos = new DataOutputStream(socket.getOutputStream());
            byte[] sendBytes = filename.getBytes();

            dos.write(sendBytes, 0, sendBytes.length);
            dos.flush();

        }catch(Exception e) {
            System.out.println("文件名传输异常");
            e.printStackTrace();
        } finally{
            if (dos != null)
                dos.close();
            if (socket != null)
                socket.close();
        }

    }



    public static void fileupload(String filedir) throws IOException{
        int len = 0;
        double sumL=0;
        boolean bool = false;
        DataOutputStream dos =null;
        FileInputStream fis = null;
        Socket socket = null;
        System.out.println("State upload this file");
        try{
            socket=new Socket("10.13.86.73",12345);
            System.out.println("build a socket");
            //向台式机的5018端口发出客户请求
            File dataFile = new File(filedir);
            System.out.println(filedir);
            if (dataFile.exists() && dataFile.isFile()){
                System.out.println("Read this file...");
            } else {
                System.out.println("This file does not exist");
                return;
            }
            dos = new DataOutputStream(socket.getOutputStream());
            fis = new FileInputStream(dataFile);
            byte[] sendBytes = new byte[1024];
            long l = dataFile.length();

            //读取文件内容:
            while ((len = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                sumL += len;
                System.out.println("已传输："+((sumL/l)*100)+"%");
                dos.write(sendBytes, 0, len);
                dos.flush();
            }
            //虽然数据类型不同，但JAVA会自动转换成相同数据类型后在做比较
            if(sumL==l){
                bool = true;
            }

        }catch(Exception e) {
            System.out.println("客户端文件传输异常");
            bool = false;
            System.out.println("Error"+e); //出错，则打印出错信息
        } finally{
            if (dos != null)
                dos.close();
            if (fis != null)
                fis.close();
            if (socket != null)
                socket.close();
        }
        System.out.println(bool?"成功":"失败");
    }
}
