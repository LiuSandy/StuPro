package com.example.lius.myapplication;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lius on 2018/2/10.
 */
public class HttpData extends AsyncTask<String, Void, String> {

    private String url;
    private String key;
    private String info;
    private HttpGetDataListen listen;


    public HttpData(String url, HttpGetDataListen listen, String key, String info) {
        this.url = url;
        this.listen = listen;
        this.key = key;
        this.info = info;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // 发送POST请求必须设置如下两行
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            //设置头文件内容
            String data = "key=" + this.key + "&info=" + this.info;
            //获得连接的输出流
            OutputStream outputStream = urlConnection.getOutputStream();
            //把头文件内容写入流中
            outputStream.write(data.getBytes());

            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");

        } catch (Exception e) {
            // TODO
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listen.getDataUrl(s);
        super.onPostExecute(s);
    }
}
