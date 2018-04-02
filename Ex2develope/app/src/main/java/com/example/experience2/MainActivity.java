package com.example.experience2;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View rootLayout = findViewById(R.id.activity_main);

        Button bb = (Button) findViewById(R.id.buttont);

        final TextInputLayout usernameText = (TextInputLayout) findViewById(R.id.userContainer1);
        final TextInputLayout passwordText = (TextInputLayout) findViewById(R.id.userContainer2);
        final EditText _userName = usernameText.getEditText();
        final EditText _key = passwordText.getEditText();

        bb.setOnClickListener(new OnClickListener() {
            String massage = "";

            @Override
            public void onClick(View v) {
                if (_userName.getText().toString().equals("Android") && _key.getText().toString().equals("123456")) {
                    massage = "登陆成功";
                } else if (!(_userName.getText().toString().equals("") || _key.getText().toString().equals(""))){
                    massage = "登陆失败";
                }
                if (!massage.equals("")) showSnackbar(rootLayout, massage);
                if (_userName.getText().toString().equals("")){
                    usernameText.setErrorEnabled(true);
                    usernameText.setError("请输入用户名");
                } else if (_key.getText().toString().equals("")) {
                    passwordText.setErrorEnabled(true);
                    passwordText.setError("请输入密码");
                }
                if (!_userName.getText().toString().equals("")){
                    usernameText.setErrorEnabled(false);
                }
                if (!_key.getText().toString().equals("")) {
                    passwordText.setErrorEnabled(false);
                }
            }
        });

        final RadioButton student = (RadioButton) findViewById(R.id.student);
        student.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(rootLayout, "学生身份被选中");
            }
        });
        final RadioButton teacher = (RadioButton) findViewById(R.id.teacher);
        teacher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(rootLayout, "教师身份被选中");
            }
        });
        final RadioButton club = (RadioButton) findViewById(R.id.club);
        club.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(rootLayout, "社团身份被选中");
            }
        });
        final RadioButton manager = (RadioButton) findViewById(R.id.manager);
        manager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { showSnackbar(rootLayout, "管理者身份被选中"); }});

        Button _sign = (Button) findViewById(R.id.sign);
        _sign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String flag = "";
                if (student.isChecked() == true) flag = "学生";
                else if (teacher.isChecked() == true) flag = "教师";
                else if (club.isChecked() == true) flag = "社团";
                else if (manager.isChecked() == true) flag = "管理者";
                showSnackbar(rootLayout, flag + "身份注册功能尚未开启");
            }
        });
    }


    public void showSnackbar(View rootLayout, String ma) {
        Snackbar.make(rootLayout, ma, Snackbar.LENGTH_SHORT)
                .setAction("按钮", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setDuration(5000)
                .show();
    }
}
