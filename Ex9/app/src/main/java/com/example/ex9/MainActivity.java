package com.example.ex9;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_OUT = -1;
    private List<info> infos = null;
    private ListView listView;
    private MyListViewAdapter adapter = null;
    private static final String url =
            "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
    private Button btn;
    private EditText city;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private ArrayList<Weather> weathers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (infos == null) infos = new ArrayList<>();
        if (weathers == null) weathers = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        btn = (Button)findViewById(R.id.searchBtn);
        city = (EditText)findViewById(R.id.CityName);
        linearLayout = (LinearLayout) findViewById(R.id.info_container);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("response", "click");
                if (testConnectivityManager()) {
                    sendRequestWithHttpURLConnetction();
                    adapter = new MyListViewAdapter(MainActivity.this, infos);
                    listView.setAdapter(adapter);
                }
            }
        });
        //for (int i = 0; i < 15; i++) {
        //    infos.add(new info("炜宝炜宝~", "陈猪宪好想你哦~"));
        //}

    }

    public boolean testConnectivityManager() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.w("response", "check");
        if (networkInfo == null || !networkInfo.isAvailable()) {
            Toast.makeText(MainActivity.this, "当前网络不可用",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setAdapter() {
        if (adapter == null) adapter = new MyListViewAdapter(this, infos);
        listView.setAdapter(adapter);
    }

    private void sendRequestWithHttpURLConnetction() {
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                Log.d("myDebug", "successfully run thread");
                try {
                    // Create a connection use url;
                    connection = (HttpURLConnection)((new URL(url.toString()).openConnection()));
                    // Set method
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    // Write into outputStream
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    // use post method to post our data
                    String request = city.getText().toString();
                    request = URLEncoder.encode(request, "utf-8");
                    out.writeBytes("theCityCode=" + request + "&theUserID=");
                    // get response data
                    InputStream in = null;
                    if (connection.getResponseCode() == 200) {
                        in = connection.getInputStream();
                    } else {
                        in = connection.getErrorStream();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    Message message = new Message();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    ArrayList<String> t = parseXMLWithPull(response.toString());
                    if (t.size() < 6)
                        message.what = -1;
                    else message.what = 1;
                    for (int i = 0; i < t.size(); i++)
                        Log.w("tttttt", i + " : " + t.get(i));
                    message.obj = t;
                    handler.sendMessage(message);
                } catch (java.net.SocketTimeoutException e) {
                    Message message = new Message();
                    message.what = TIME_OUT;
                    handler.sendMessage(message);
                    e.printStackTrace();
                    Log.d("myDebug", "Timeout to get the data");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("myDebug", "Can't not connect to the service");
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
        workThread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == -1) {
                List<String> a = (List<String>)message.obj;
                Log.w("Err 2", a.get(0));
                if (a.get(0).equals(
                        "发现错误：免费用户不能使用高速访问。http://www.webxml.com.cn/"))
                    Toast.makeText(MainActivity.this,
                        "您的点击速度过快，二次查询间隔小于600ms",
                        Toast.LENGTH_SHORT).show();
                else if (a.get(0).equals(
                        "发现错误：免费用户24小时内访问超过规定数量。http://www.webxml.com.cn/"))
                    Toast.makeText(MainActivity.this,
                            "免费用户24小时内访问超过规定数量50次",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,
                            "当前城市不存在，请重新输入",
                            Toast.LENGTH_SHORT).show();
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                List<String> array = (List<String>)message.obj;
                TextView tv = (TextView) findViewById(R.id.location);
                tv.setText(array.get(1));
                tv = (TextView)findViewById(R.id.updateTime);
                String t = array.get(3).substring(11) + " 更新";
                tv.setText(t);
                String line6 = array.get(4), out = "";
                Log.w("line6", line6);
                int j = 0;
                //今日天气实况：气温：11℃；风向/风力：南风 1级；湿度：53%
                for (int i = 7; i < line6.length(); i++) {
                    if (line6.charAt(i) == '；' || i == line6.length()-1) j++;
                    Log.w("j", j + "  " + out);
                    if (line6.charAt(i) != '；')out += line6.charAt(i);
                    if (j == 1) {
                        tv = (TextView)findViewById(R.id.temperature_big);
                        tv.setText(out.substring(3));
                        out = "";
                        j++;
                    }
                    if (j == 3) {
                        tv = (TextView) findViewById(R.id.windDir);
                        tv.setText(out.substring(6));
                        out = "";
                        j++;
                    }
                    if (j == 5) {
                        tv = (TextView) findViewById(R.id.humidity);
                        tv.setText(out);
                        out = "";
                        j++;
                    }
                }
                tv = (TextView)findViewById(R.id.air);
                String line5 = array.get(5), line5S = "";
                for (int i = 0, tmp = 0; i< line5.length(); i++) {
                    if (line5.charAt(i) == '。') {
                        tmp++;
                        continue;
                    }
                    if (tmp == 1) line5S += line5.charAt(i);
                }
                tv.setText(line5S);
                tv = (TextView) findViewById(R.id.temperature_region);
                tv.setText(array.get(8));
//紫外线指数：弱，辐射较弱，涂擦SPF12-15、PA+护肤品。感冒指数：较易发，天较凉，增加衣服，注意防护。穿衣指数：较冷，建议着厚外套加毛衣等服装。洗车指数：较适宜，无雨且风力较小，易保持清洁度。运动指数：较适宜，气温较低，推荐您进行室内运动。空气污染指数：中，易感人群应适当减少室外活动。
                String line8 = array.get(6), title = "", detail = "";
                out = "";
                infos.clear();
                for (int i = 0; i < line8.length(); i++) {
                    if (line8.charAt(i) == '：') {
                        title = out;
                        out = "";
                        continue;
                    }
                    if (line8.charAt(i) == '。') {
                        detail = out;
                        out = "";
                        infos.add(new info(title, detail));
                        continue;
                    }
                    out += line8.charAt(i);
                }
                List<String> a = new ArrayList<>();
                a.add(array.get(7));
                a.add(array.get(8));
                a.add(array.get(12));
                a.add(array.get(13));
                a.add(array.get(17));
                a.add(array.get(28));
                a.add(array.get(22));
                a.add(array.get(23));
                a.add(array.get(27));
                a.add(array.get(28));

                weathers.clear();
                String date = "", forecast = "", temperature_for = "";
                for (int i = 0; i < a.size(); i++) {
                    out = "";
                    if (i % 2 == 1) {
                        temperature_for = a.get(i);
                        weathers.add(new Weather(date, forecast, temperature_for));
                    } else {
                        for (int k = 0; k < a.get(i).length(); k++) {
                            out += a.get(i).charAt(k);
                            if (a.get(i).charAt(k) == '日') {
                                date = out;
                                forecast = a.get(i).substring(k+2);
                                break;
                            }
                        }
                    }
                }
                MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this, weathers);
                recyclerView.setAdapter(myRecyclerAdapter);
            }
        }
    };

    private ArrayList<String> parseXMLWithPull(String xml) {
        ArrayList<String> str = new ArrayList<>();
        try {
            // use pull to parse xml
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("string".equals(parser.getName())) {
                            str.add(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("myDebug", "can not parse the xml");
        }
        return str;
    }
}
