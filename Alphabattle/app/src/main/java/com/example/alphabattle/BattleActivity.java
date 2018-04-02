package com.example.alphabattle;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import static java.lang.Integer.numberOfLeadingZeros;
import static java.lang.Integer.parseInt;

public class BattleActivity extends AppCompatActivity {
    private TextView table = null;
    private static TextView score = null;
    private static TextView note = null;
    private String _user;
    private static String _score;
    private Button btn = null, backbtn = null, applyBtn = null;
    public static final String url =
            "http://fanyi.youdao.com/openapi.do?keyfrom=youdao111&key=60638690&type=data&doctype=xml&version=1.1&q=";

    int btn_id[] = new int[] {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
                    R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10, R.id.b11,
                    R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16,};
    Vector<Integer> btn_state = new Vector<>();
    final String BATTLE = "com.example.alphabattle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        _user = bd.getString("username");
        _score = bd.getString("score");
        table  = (TextView) findViewById(R.id.table);
        for (int i = 0; i < btn_id.length; i++) {
            btn = (Button)findViewById(btn_id[i]);
            btn.setOnClickListener(alphaBtnclick);
        }
        resetAlphabet();
        note = (TextView) findViewById(R.id.note);
        backbtn = (Button) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(backBtnclick);
        applyBtn = (Button) findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(applyBtnclick);
        score = (TextView) findViewById(R.id.score);
        score.setText(_score);
        Intent broadInt = new Intent(BATTLE);
        broadInt.putExtras(bd);
        sendBroadcast(broadInt);
    }

    public boolean testConnectivityManager() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            Toast.makeText(BattleActivity.this, "当前网络不可用",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void searchWord(String word){
        if (!testConnectivityManager())return;
        note.setText("");
        sendRequestWithHttpURLConnetction(word);
    }

    private View.OnClickListener alphaBtnclick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn_state.contains(btn.getId()))return;
            btn_state.add(btn.getId());
            btn.setTextColor(getResources().getColor(R.color.clicked));
            table.setText(table.getText().toString()+btn.getText());
        }
    };

    private void resetAlphabet() {
        for (int i = 0; i < btn_id.length; i++) {
            btn = (Button)findViewById(btn_id[i]);
            int cha = (int)(Math.random()*100%26);
            String t = ""+(char)(65 + cha);
            btn.setText(t);
        }
    }

    private View.OnClickListener backBtnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String a = table.getText().toString();
            if (a.isEmpty())return ;
            int length = a.length();
            table.setText(a.substring(0, length-1));
            int b = btn_state.lastElement();
            btn_state.remove(btn_state.lastElement());
            btn = (Button) findViewById(b);
            btn.setTextColor(getResources().getColor(R.color.unclicked));
        }
    };

    private View.OnClickListener applyBtnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchWord(table.getText().toString());
            init();
            Intent broudInt = new Intent(BATTLE);
            Bundle bd = new Bundle();
            bd.putString("username", _user);
            bd.putString("score", _score);
            resetAlphabet();
            broudInt.putExtras(bd);
            sendBroadcast(broudInt);
        }
    };

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            List<String> str = (List<String>) message.obj;
            if (str.isEmpty()) {
                note.setText("拼写错误");
                return;
            }
            String ss = "";
            for (int i = 0; i < str.size(); i++)
                ss = ss + str.get(i) + "\n";
            new AlertDialog.Builder(BattleActivity.this)
                    .setTitle("")
                    .setMessage(ss)
                    .setPositiveButton("确定", null)
                    .show();
            int t = parseInt(score.getText().toString());
            t = t + 1;
            _score = "" + t;
            score.setText(_score);
        }
    };

    private void init() {
        table.setText("");
        for (int i = btn_state.size()-1; i >= 0; i--) {
            btn = (Button) findViewById(btn_state.get(i));
            btn.setTextColor(getResources().getColor(R.color.unclicked));
            btn_state.remove(i);
        }
        btn_state.clear();
    }
    public void sendRequestWithHttpURLConnetction(final String word) {
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection)
                            ((new URL((BattleActivity.url+word).toString()).openConnection()));
                    Log.w("mydede", "openConnection");
//                    Log.w("mydede", "");
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    Log.w("mydede", "setConnection");
                    InputStream in = null;
                    if (connection.getResponseCode() == 200) {
                        in = connection.getInputStream();
                    } else {
                        in = connection.getErrorStream();
                    }
                    Log.w("mydede", "getInput");
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
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
        workThread.start();
    }

    private static ArrayList<String> parseXMLWithPull(String xml) {
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
                        if ("ex".equals(parser.getName())) {
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

    @Override
    protected void onDestroy() {
        user_DB dbHelper = new user_DB(this);
        dbHelper.update(_user, _score);
        super.onDestroy();
    }
}