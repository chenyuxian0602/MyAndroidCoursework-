package com.example.ex7;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class FileActivity extends AppCompatActivity {

    private Button saveBtn, loadBtn, clearBtn;
    private EditText fileEntry;
    private TextView output;

    private static int MODE = MODE_PRIVATE;
    private final String PREFERANCE_NAME = "file_content";

    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        loadBtn = (Button)findViewById(R.id.loadBtn);
        clearBtn = (Button)findViewById(R.id.clearBtnF);
        fileEntry = (EditText)findViewById(R.id.fileEntry);
        output = (TextView)findViewById(R.id.output);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = null;
                String saveStr = fileEntry.getText().toString();
                if (saveStr.equals("")){
                    toast.makeText(FileActivity.this, "Fail to save", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("input", saveStr);
                    editor.commit();
                } catch (Exception e) {
                    toast.makeText(FileActivity.this, "Fail to save", Toast.LENGTH_SHORT).show();
                    return;
                }
                toast.makeText(FileActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = null;
                String loadStr = getLoad();
                if (loadStr.equals("-1") || loadStr.isEmpty()) {
                    toast = Toast.makeText(FileActivity.this, "Fail to load file", Toast.LENGTH_SHORT);
                } else {
                    output.setText(loadStr);
                    toast = Toast.makeText(FileActivity.this, "Load successfully", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output.setText("");
                fileEntry.setText("");
            }
        });
    }

    private String getLoad() {
        String loadStr = "-1";
        try {
            if (sharedPreferences == null)
                sharedPreferences = getSharedPreferences(PREFERANCE_NAME, MODE);
            loadStr = sharedPreferences.getString("input", "-1");
        } catch (Exception e) {
            return "-1";
        }
        return loadStr;
    }
}
