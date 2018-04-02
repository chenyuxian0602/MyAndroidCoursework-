package com.example.experience4;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DynamicActivity extends AppCompatActivity {
    private static final String DYNAMICACTION = "com.example.experience.dynamicreceiver";
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        final DynamicReceiver dynamicReceiver = new DynamicReceiver();
        Button btn = (Button) findViewById(R.id.registerBtn);
        final Button finalBtn = btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DynamicReceiver dynamicReceiver =  new DynamicReceiver();
                if (flag) {
                    IntentFilter dynamic_filter = new IntentFilter();
                    dynamic_filter.addAction(DYNAMICACTION);
                    registerReceiver(dynamicReceiver, dynamic_filter);
                    finalBtn.setText("UNREGISTER BROADCAST");
                } else {
                    unregisterReceiver(dynamicReceiver);
                    finalBtn.setText("REGISTER BROADCAST");
                }
                finalBtn.setAllCaps(false);
                flag = !flag;
            }
        });

        btn = (Button) findViewById(R.id.sendBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    Intent intent = new Intent(DYNAMICACTION);
                    Bundle bd = new Bundle();
                    EditText et = (EditText)findViewById(R.id.input);
                    String st = et.getText().toString();
                    bd.putString("input", st);
                    intent.putExtras(bd);
                    sendBroadcast(intent);
                }
            }
        });
    }
}
