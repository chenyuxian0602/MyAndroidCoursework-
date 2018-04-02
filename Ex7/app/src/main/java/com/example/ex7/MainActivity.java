package com.example.ex7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int MODE = MODE_PRIVATE;
    public static final String PREFERANCE_NAME = "saved_password";
    private EditText password, confire;
    private Button okBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPassword().equals("-1") || getPassword().isEmpty()) {
            setContentView(R.layout.activity_main);
            okBtn = (Button)findViewById(R.id.okBtn);
            clearBtn = (Button)findViewById(R.id.clearBtn);
            confire = (EditText)findViewById(R.id.confire);
            password = (EditText)findViewById(R.id.password);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (canCreate()) {
                        createPassword(password.getText().toString());
                    }
                }
            });
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password.setText("");
                    confire.setText("");
                }
            });
        } else {
            setContentView(R.layout.acticity_second);
            okBtn = (Button)findViewById(R.id.okBtnS);
            clearBtn = (Button)findViewById(R.id.clearBtnS);
            final EditText ed = (EditText)findViewById(R.id.password_log);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ed.getText().toString().equals(getPassword())) {
                        Intent jump = new Intent(MainActivity.this, FileActivity.class);
                        startActivity(jump);
                        finish();
                    }
                }
            });
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ed.setText("");
                }
            });
        }
    }

    private boolean canCreate() {
        String _password = password.getText().toString(), _confire = confire.getText().toString();
        if (_confire.equals(_password) && !_confire.isEmpty() && !_password.isEmpty()) {
            return true;
        }
        Toast toast = null;
        if (password.getText().toString().isEmpty() || confire.getText().toString().isEmpty()) {
            toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString() != confire.getText().toString()) {
            toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public String getPassword() {
        String password = "-1";
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERANCE_NAME, MODE);
            password = sharedPreferences.getString("password", "-1");
        } catch (Exception e) {
            return "-1";
        }
        return password;
    }

    public void createPassword(String _password) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERANCE_NAME, MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", _password);
        editor.commit();
    }
}
