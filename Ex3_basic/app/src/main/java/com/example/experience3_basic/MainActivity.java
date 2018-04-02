package com.example.experience3_basic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] name = new String[]{"Aaron", "Elvis", "David", "Edwin", "Frank", "Joshua", "Ivan", "Mark", "Joseph", "Phoebe"};
    String[] phone = new String[]{"17715523654", "18825653224", "15052116654", "18854211875", "13955188541", "13621574410", "15684122771", "17765213579", "13315466578", "17895466428"};
    String[] type = new String[]{"手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机"};
    String[] from = new String[]{"江苏苏州电信", "广东揭阳移动","江苏无锡移动", "山东青岛移动", "安徽合肥移动", "江苏苏州移动", "山东烟台联通", "广东珠海电信", "河北石家庄电信", "山东东营移动"};
    int[] bgc = new int[]{0xffBB4C3B, 0xffc48d30, 0xff4469b0, 0xff20A17B, 0xffBB4C3B, 0xffc48d30, 0xff4469b0, 0xff20A17B, 0xffBB4C3B, 0xffc48d30};

    final List<my_people> contects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (contects.size() == 0)
            for(int i = 0; i < 10; i++) {
                contects.add(new my_people(name[i], phone[i], type[i], from[i], bgc[i]));
            }
        ListView listView = (ListView) findViewById(R.id.list_container);
        final My_adapter adapter = new My_adapter(this, contects);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, contect.class);
                Bundle bd = new Bundle();
                bd.putString("name", contects.get(position).getName());
                bd.putString("phone", contects.get(position).getPhone());
                bd.putString("type", contects.get(position).getType());
                bd.putString("from", contects.get(position).getFrom());
                bd.putInt("bgc", contects.get(position).getBgc());
                intent.putExtras(bd);
                intent.setAction("detail_action");
                intent.addCategory("detail_category");
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除联系人")
                        .setMessage("确定删除联系人" + contects.get(position).getName())
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contects.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });
    }
}

