package com.swufe.app5;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.logging.Handler;

public class RateListActivity {

    private String[] list_data = {"one","tow","three","four"};
    int msgWhat = 3;
    Handler handler;

    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list_data);
    setListAdapter(adapter);

    @Override
    public void run() {
        Log.i("thread","run.....");
        List<String> rateList = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();

            Elements tbs = doc.getElementsByClass("tableDataTable");
            Element table = tbs.get(0);

            Elements tds = table.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i+=5) {
                Element td = tds.get(i);
                Element td2 = tds.get(i+3);

                String tdStr = td.text();
                String pStr = td2.text();
                rateList.add(tdStr + "=>" + pStr);

                Log.i("td",tdStr + "=>" + pStr);
            }
        } catch (MalformedURLException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(5);

        msg.obj = rateList;
        handler.sendMessage(msg);

        Log.i("thread","sendMessage.....");
    }


    修改主线程的handleMessage处理接收子线程返回的数据
            handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 5){
                List<String> retList = (List<String>) msg.obj;
                ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,retList);
                setListAdapter(adapter);
                Log.i("handler","reset list...");
            }
            super.handleMessage(msg);
        }
    };



}
