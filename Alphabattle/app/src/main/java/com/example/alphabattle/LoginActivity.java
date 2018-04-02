package com.example.alphabattle;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private user_DB dbHelper = new user_DB(this);
    private Button login_btn = null;
    private EditText username = null, password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = (Button)findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _user = username.getText().toString(),
                        _pass = password.getText().toString();
                Cursor c = dbHelper.query();
                while(c.moveToNext()) {
                    if (c.getString(c.getColumnIndex("user")).equals(_user)) {
                        if (c.getString(c.getColumnIndex("password")).equals(_pass)) {
                            jumpActivity(_user, c.getString(c.getColumnIndex("score")));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Wrong password, please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                        c.close();
                        return;
                    }
                }
                if (checkEmpty(_user, _pass)) {
                    dbHelper.insert(_user, _pass);
                    jumpActivity(_user, "0");
                }
                c.close();
            }
        });
    }

    private void jumpActivity(String user, String score) {
        String BATTLE = "com.example.alphabattle";
        Intent intent = new Intent(LoginActivity.this, BattleActivity.class);
        Bundle bd = new Bundle();
        bd.putString("username", user);
        bd.putString("score", score);
        intent.putExtras(bd);
        Intent broadInt = new Intent(BATTLE);
        broadInt.putExtras(bd);
        sendBroadcast(broadInt);
        startActivity(intent);
    }

    private boolean checkEmpty(String _u, String _p) {
        if (_u.isEmpty()){
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (_p.isEmpty()){
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
