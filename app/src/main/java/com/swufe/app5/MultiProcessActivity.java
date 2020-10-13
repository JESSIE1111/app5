package com.swufe.app5;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;


public class MultiProcessActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "MultiProcessActivity";

    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_process_layout);

        Thread t = new Thread(MultiProcessActivity.this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    String[][] storages = (String[][]) msg.obj;
                    for (String[] row:storages) {
                        if (row[0].equals("美元")) {
                            Log.i(TAG, "handleMessage: 美元汇率=" + row[1]);
                        }
                    }
                }
            }
        };
    }

    public static String inputStream2String (InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0) break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    public static String[][] getTable(Document document){
        //由于第一行没有相应的元素所以长度进行了手动调整
        int i = 0, j = 0;
        Elements trs = document.select("table").select("tr");
        //第一行元素没有td元素
        Elements tds = trs.get(1).select("td");

        String[][] storages = new String[trs.size()-1][2];
        String txtString = "";

        for (i = 0; i < trs.size()-1; i++){
            tds = trs.get(i+1).select("td");
            storages[i][0] = tds.get(0).text();
            storages[i][1] = tds.get(4).text();
            Log.i(TAG, "getTable: " + storages[i][0] + "" + storages[i][1]);
        }
        return storages;
    }


    @Override
    public void run() {
        //获得网络数据
        URL url = null;
        String urlString = "https://www.usd-cny.com";
        String html = "";
        try {
            url = new URL(urlString);
            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            html = inputStream2String(in);
            Log.i(TAG, "run: html = " + html);
        } catch (MalformedURLException e) {
            Log.i(TAG, "run: MalformedURLException " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(TAG, "run: IOException " + e);
            e.printStackTrace();
        }

//        使用JSOUP对超文本进行解析
        Document document = Jsoup.parse(html);
        String storage[][] = getTable(document);
//        对字符串进行切分获得storage数组
//        并将此对象放入obj传递给主程序
//        由主程序进行计算和存储到相应的xml文件中,理论上以实现
//        存在的问题:这一个工作是否应当交给主程序完成,还是子程序后台完成后传递给主程序信号
//        我倾向于后者,如此工作就后台程序进行,不用考虑参数的个数,主程序直接从myrate文件中取得
//        子程序只用获取最新的汇率,进行一系列操作,之后的处理与之无关
//        可扩展性高,适用于更多的汇率转换情况


        Message msg = handler.obtainMessage(5);
        msg.obj = storage;
        handler.sendMessage(msg);
    }
}