package com.example.experience2basic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logon = (Button) findViewById(R.id.logonB);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                     if (username.getText().toString().equals("Android") && password.getText().toString().equals("123456"))
                         showTheDialog("登录成功");
                    else
                         showTheDialog("登录失败");
                }
            }
        });

        final RadioButton student = (RadioButton) findViewById(R.id.studentB);
        final RadioButton teacher = (RadioButton) findViewById(R.id.teacherB);
        final RadioButton club = (RadioButton) findViewById(R.id.clubB);
        final RadioButton manager = (RadioButton) findViewById(R.id.managerB);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.isChecked() == true) {
                    Toast.makeText(MainActivity.this, "学生身份被选中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teacher.isChecked() == true) {
                    Toast.makeText(MainActivity.this, "教师身份被选中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (club.isChecked() == true) {
                    Toast.makeText(MainActivity.this, "社团身份被选中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.isChecked() == true) {
                    Toast.makeText(MainActivity.this, "管理者身份被选中", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button sign = (Button) findViewById(R.id.signB);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = "";
                if (student.isChecked() == true) ma = "学生";
                else if (teacher.isChecked() == true) ma = "教师";
                else if (club.isChecked() == true) ma = "社团";
                else if (manager.isChecked() == true) ma = "管理者";
                Toast.makeText(MainActivity.this, ma + "身份注册功能未开启", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showTheDialog(String ma) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框\"确定\"按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框\"取消\"按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                })
                .setMessage(ma)
                .show();
    }
}
