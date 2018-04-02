package com.example.lab4_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sta = (Button) findViewById(R.id.static_btn);
        sta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, Static_eas.class);
                startActivity(new Intent(MainActivity.this, NewActivity.class));
            }
        });

        Button dyn = (Button) findViewById(R.id.dynamic_btn);
        dyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_dyn = new Intent(MainActivity.this, NewActivityD.class);
                startActivity(intent_dyn);
            }
        });
    }
}
