package com.example.lius.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListen, View.OnClickListener {

    private HttpData httpData;
    private List<ListData> list;
    private ListView lv;
    private EditText sendText;
    private Button sendBtn;
    private String contenctStr;
    private TextAdapter adapter;
    private String[] welcomeArr;
    private double currentTime, oldTIme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getDataUrl(String data) {
        parseText(data);
    }

    private void initView() {
        lv = findViewById(R.id.lv);
        sendText = findViewById(R.id.sendText);
        sendBtn = findViewById(R.id.sendBtn);
        list = new ArrayList<ListData>();

//        sendBtn.setOnClickListener(this);
//        sendBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //新建一个Intent(当前Activity, SecondActivity)=====显示Intent
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //启动
                startActivity(intent);
            }
        } );


        adapter = new TextAdapter(list, this);
        lv.setAdapter(adapter);
        ListData listData = new ListData(getRangdomWelcomeTips(), ListData.RECEIVER, getTime());
        list.add(listData);
    }

    public void parseText(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            ListData listData = new ListData(jsonObject.getString("text"), ListData.RECEIVER, getTime());
            System.out.println(listData.getContent());
            list.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        contenctStr = sendText.getText().toString();
        sendText.setText("");
        // 对输入的内容进行格式化，去掉空格，回车等
        String dropk = contenctStr.replace(" ", "");
        String droph = dropk.replace("\n", "");

        ListData listData = new ListData(contenctStr, ListData.SEND, getTime());
        list.add(listData);

        if (list.size() > 30) {
            for (int i = 0; i < list.size(); i++) {
                list.remove(i);
            }
        }
        adapter.notifyDataSetChanged();//刷新
        // 发送请求
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api", this, "8aff015ea0bf40d5b6b18e8c7c5579ae", droph).execute();
    }

    private String getRangdomWelcomeTips() {
        getTime();
        String welcomeTip = null;
        welcomeArr = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * (welcomeArr.length - 1));
        welcomeTip = welcomeArr[index];
        return welcomeTip;
    }

    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        System.out.println(format);
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTIme >= 5 * 60 * 1000) {
            oldTIme = currentTime;
            return str;
        } else {
            return "";
        }
    }
}
