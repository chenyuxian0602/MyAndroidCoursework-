package com.example.lab4_code;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈昱宪 on 2016/10/23.
 */

public class NewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        final List<MyFruit> list = new ArrayList<>();
        String[] name = new String[]{"apple", "banana", "cherry", "coco", "kiwi", "orange", "pear", "strawberry", "watermelon"};
        int[] id = new int[]{R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry, R.mipmap.coco, R.mipmap.kiwi, R.mipmap.orange, R.mipmap.pear, R.mipmap.strawberry, R.mipmap.watermelon};
        for (int i = 0; i < 9; i++) {
            list.add(new MyFruit(name[i], id[i]));
        }
        MyAdapter myAdapter = new MyAdapter(this, list);
        final ListView fruit = (ListView) findViewById(R.id.fruitList);
        fruit.setAdapter(myAdapter);

        fruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public static final String STATICACTION = "com.example.Lab4_code.staticreceiver";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.w("click", "click");
                Intent intent = new Intent(STATICACTION);
                Bundle bd = new Bundle();
                bd.putSerializable("item",list.get(position));
                intent.putExtras(bd);
                sendBroadcast(intent);
            }
        });
    }
}
