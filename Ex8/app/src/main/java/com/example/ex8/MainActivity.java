package com.example.ex8;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btn;
    public static MyAdapter myAdapter;
    public static List<info> infos = new ArrayList<>();

    myDB dbHelper = new myDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.btn);

        initData();
        myAdapter = new MyAdapter(this, infos);
        listView.setAdapter(myAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("是否删除？")
                        .setNegativeButton("否", null)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 更新listview 更新DB
                                dbHelper.delete(infos.get(position));
                                infos.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                builder.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View dialogView = factory.inflate(R.layout.log, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                String phone = "";

                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                while(cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String _id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    if (name.equals(infos.get(position).getName())) {
                        Cursor phoneCursor = contentResolver.query(
                                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+_id, null, null);
                        while(phoneCursor.moveToNext()) {
                            phone = phone + "   " +phoneCursor.getString(
                                    phoneCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phoneCursor.close();
                    }
                }
                cursor.close();


                TextView name = (TextView) dialogView.findViewById(R.id.log_name);
                final EditText birthday = (EditText) dialogView.findViewById(R.id.log_birthday);
                final EditText gift = (EditText) dialogView.findViewById(R.id.log_gift);
                final TextView phoneT = (TextView) dialogView.findViewById(R.id.phoneNum);
                name.setText(infos.get(position).getName());
                phoneT.setText(phone);
                //if (infos.get(position).getName().equals("asd"))
                    //phone.setText("123123123");
                builder.setView(dialogView);
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        infos.get(position).setBirthday(birthday.getText().toString());
                        infos.get(position).setGift(gift.getText().toString());
                        myAdapter.notifyDataSetChanged();
                        //update DB
                        dbHelper.update(infos.get(position));
                    }
                });
                builder.setNegativeButton("放弃修改", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("result", ""+resultCode);
        Log.w("result", "123");
        if(resultCode == 1){
            Log.w("result", "go in");
            Bundle bd = data.getExtras();
            String _name = bd.getString("name");
            String _birth = bd.getString("birth");
            String _gift = bd.getString("gift");
            dbHelper.insert(new info(_name, _birth, _gift));
            myAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initData() {
        Cursor c = dbHelper.query();
        while (c.moveToNext()) {
            infos.add(new info(c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("birth")), c.getString(c.getColumnIndex("gift"))));
        }
    }
}
