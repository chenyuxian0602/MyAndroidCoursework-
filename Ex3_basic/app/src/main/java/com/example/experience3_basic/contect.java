package com.example.experience3_basic;

/**
 * Created by 陈昱宪 on 2016/10/18.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class contect extends AppCompatActivity {
    boolean starflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contect);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contect.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        final ImageView star = (ImageView) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (starflag) {
                    star.setBackgroundResource(R.drawable.full_star);
                } else {
                    star.setBackgroundResource(R.drawable.empty_star);
                }
                starflag = !starflag;
            }
        });

        Bundle bd = this.getIntent().getExtras();

        TextView textView = (TextView) findViewById(R.id.nametext);
        textView.setText(bd.get("name").toString());
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top);
        top.setBackgroundColor(bd.getInt("bgc"));
        textView = (TextView) findViewById(R.id.phoneD);
        textView.setText(bd.get("phone").toString());
        textView = (TextView) findViewById(R.id.fromD);
        textView.setText(bd.get("type").toString() + "  " + bd.get("from"));

        ListView listViewC = (ListView) findViewById(R.id.listC);
        String[] ss = new String[] {"编辑联系人","分享联系人","加入联系人","删除联系人"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ss);
        listViewC.setAdapter(arrayAdapter);
    }
}
