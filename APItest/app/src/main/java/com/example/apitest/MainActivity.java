package com.example.apitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvShow;
    private HttpURLConnection connection;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private int GET_DATA_SUCCESS=101;//获取数据成功的标志

    Handler mHandler = new Handler(new Handler.Callback()  {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what==GET_DATA_SUCCESS) {
                String data = msg.getData().getString("data");
                //Log.i("MainActivity",data);
                mTvShow.setText(data);
            }
            return false;
        }
    });


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initUI();
        //初始化数据
        initData();
    }

    private void initUI() {
        //获取文本
        mTvShow = findViewById(R.id.tv_show);
        //获取按钮并且绑定监听对象
        findViewById(R.id.btn).setOnClickListener(this );
    }

    @Override
    public void onClick(View v) {
        initData();
    }
//初始化数据
    private void initData() {

        //请求网络必须在子线程
        new  Thread(new Runnable() {
            @Override
            public void run() {
                String httpUrl = "http://api.tianapi.com/txapi/caipu/index?key=d5ab920c102f3066881ec1aa918db465&page=1";
                String data = request(httpUrl);
                System.out.println(data);
                Log.i("MainActivity",":获取数据为:"+data);
                //创建信息对象
                Message message = Message.obtain();
                Bundle bundle =new Bundle();
                bundle.putString("data",data);
                message.setData(bundle);
                message.what = GET_DATA_SUCCESS;
                //像主线程发信息
                mHandler.sendMessage(message);
            }
        }).start();
    }
//从服务器获取数据

    private String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl ;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
