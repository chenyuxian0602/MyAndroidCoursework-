package com.example.ex5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        final List<Fruit> fruits = new ArrayList<>();
        String[] name = new String[]{"apple", "banana", "cherry", "coco", "kiwi", "orange", "pear", "strawberry", "watermelon"};
        int[] id = new int[]{R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry, R.mipmap.coco, R.mipmap.kiwi, R.mipmap.orange, R.mipmap.pear, R.mipmap.strawberry, R.mipmap.watermelon};
        for (int i = 0; i < 9; i++) {
            fruits.add(new Fruit(id[i], name[i]));
        }
        FruitAdapter myAdapter = new FruitAdapter(this, fruits);
        final ListView fruit = (ListView) findViewById(R.id.fruitList);
        fruit.setAdapter(myAdapter);

        fruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public static final String STATICACTION = "com.example.ex5.staticreceiver";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.w("click", "click");
                Intent intent = new Intent(STATICACTION);
                Bundle bd = new Bundle();
                bd.putSerializable("item",fruits.get(position));
                intent.putExtras(bd);
                sendBroadcast(intent);
            }
        });
    }
}
