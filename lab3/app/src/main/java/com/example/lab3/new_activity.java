package com.example.lab3;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class new_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Bundle bundle = this.getIntent().getExtras();

        TextView textView = (TextView) findViewById(R.id.dname);
        textView.setText(bundle.get("name").toString());

        textView = (TextView) findViewById(R.id.phoneNum);
        textView.setText(bundle.get("phone").toString());

        textView = (TextView) findViewById(R.id.from);
        textView.setText(bundle.get("type").toString()+"  "+bundle.get("location").toString());


        RelativeLayout top = (RelativeLayout) findViewById(R.id.toptop);
        top.setBackgroundColor(bundle.getInt("background"));

    }
}
