package com.example.ex8;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.ex8.MainActivity.infos;

public class AddActivity extends AppCompatActivity {

    private Button btn;
    private EditText _name, _birthday, _gift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn = (Button) findViewById(R.id.add_btn);
        _name = (EditText)findViewById(R.id.add_name);
        _birthday = (EditText)findViewById(R.id.add_birthday);
        _gift = (EditText)findViewById(R.id.add_gift);

        final Toast toast = null;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = _name.getText().toString();
                Log.w("nameStr", nameStr);
                if (nameStr.isEmpty()) {
                    toast.makeText(AddActivity.this, "名字为空,请完善", Toast.LENGTH_SHORT).show();
                    return ;
                } else if (!canAdd(nameStr)) {
                    toast.makeText(AddActivity.this, "名字重复,请核查", Toast.LENGTH_SHORT).show();
                    return;
                }
                // update DB and listview
                infos.add(new info(nameStr, _birthday.getText().toString(), _gift.getText().toString()));
                MainActivity.myAdapter.notifyDataSetChanged();

                Intent intent = new Intent();
                Bundle bd = new Bundle();
                bd.putString("name", nameStr);
                bd.putString("birthday", _birthday.getText().toString());
                bd.putString("gift", _gift.getText().toString());
                intent.putExtras(bd);
                setResult(1, intent);
                finish();
            }
        });
    }

    public boolean canAdd(String _name) {
        for (int i = 0; i < infos.size(); i++)
            if (infos.get(i).getName().equals(_name))
                return false;
        return true;
    }
}
