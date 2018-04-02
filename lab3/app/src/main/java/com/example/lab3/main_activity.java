package com.example.lab3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class main_activity extends AppCompatActivity {

    public class contactItem implements Serializable {

        private String Name;
        private String Phone;
        private String Type;
        private String Location;
        private int Background;

        public contactItem(String N, String P, String T,
                           String L, int B) {
            this.Name = N;
            this.Phone = P;
            this.Type = T;
            this.Location = L;
            this.Background = B;
        }

        public String getName() { return Name; }
        public String getPhone() { return Phone; }
        public String getType() { return Type; }
        public String getLocation() { return Location; }
        public int getBackground() { return Background; }

        public void setName(final String N) { this.Name = N; }
        public void setPhone(final String P) { this.Phone = P; }
        public void setType(final String T) { this.Type = T; }
        public void setLocation(final String L) { this.Location = L; }
        //public void setBackground(final String B) { this. Background = B; }
    }


    public class MyAdapter extends BaseAdapter {

        private Context context;
        private List<String> nameList;
        private List<contactItem> list;

        public MyAdapter(Context context, List<contactItem> list) {
            this.context = context;
            for(int i = 0; i < list.size(); i++) {
                contactItem tmp = list.get(i);
                contactItem item = new contactItem(tmp.getName(), tmp.getPhone(),
                        tmp.getType(), tmp.getLocation(), tmp.getBackground());
                this.list.add(item);
                this.nameList.add(tmp.getName());
            }
        }


        @Override
        public int getCount() {
            if(list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            if(list == null) {
                return null;
            }
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View convertView;
            ViewHolder viewHolder;

            if(view == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.nameInfo = (TextView) convertView.findViewById(R.id.nameInfo);
                convertView.setTag(viewHolder);
            } else {
                convertView = view;
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(list.get(i).getName());
            viewHolder.nameInfo.setText(list.get(i).getName().charAt(0));

            return convertView;
        }

        private class ViewHolder {
            public TextView name;
            public TextView nameInfo;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final List<contactItem> data = new ArrayList<>();
        final List<Map<String, Object>> dataInfo = new ArrayList<>();


        String[] Names = new String[]{"Aaron","Elvis","David","Edwin","Frank","Joshua","Ivan","Mark","Joseph","Phoebe"};
        String[] Phones = new String[]{"17715523654","18825653224","15052116654","18854211875","13955188541","13621574410","15684122771","17765213579","13315466578","17895466428"};
        String[] Types = new String[]{"手机","手机","手机","手机","手机","手机","手机","手机","手机","手机"};
        String[] Locations = new String[]{"江苏苏州电信","广东揭阳移动","江苏无锡移动","山东青岛移动","安徽合肥移动","江苏苏州移动","山东烟台联通","广东珠海电信","河北石家庄电信","山东东营移动"};
        int[] Backgrounds = new int[]{0xffBB4C3B,0xffc48d30,0xff4469b0,0xff20A17B,0xffBB4C3B,0xffc48d30,0xff4469b0,0xff20A17B,0xffBB4C3B,0xffc48d30};


        for(int i = 0; i < 10; i++) {
            contactItem tmp = new contactItem(Names[i], Phones[i], Types[i],
                    Locations[i], Backgrounds[i]);
            data.add(tmp);

            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("nameInfo",Names[i].toString().charAt(0));
            temp.put("name",Names[i].toString());
            dataInfo.add(temp);
        }

        ListView contactList = (ListView)findViewById(R.id.contacts_list);
        //MyAdapter myAdapter = new MyAdapter(this,data);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,dataInfo,R.layout.item,
                new String[]{"nameInfo","name"},new int[]{R.id.nameInfo,R.id.name});
        contactList.setAdapter(simpleAdapter);

        // 点击事件
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Intent intent = new Intent(main_activity.this,new_activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",data.get(i).getName());
                bundle.putString("phone",data.get(i).getPhone());
                bundle.putString("type",data.get(i).getType());
                bundle.putString("location",data.get(i).getLocation());
                bundle.putInt("background",data.get(i).getBackground());

                intent.putExtras(bundle);  //  附带上额外的数据
                intent.setAction("detailAction");
                intent.addCategory("detailCategory");
                startActivity(intent);
            }
        });

        // 长按事件
        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,
                                           final int i, long l) {
                new AlertDialog.Builder(main_activity.this)
                        .setTitle("删除联系人")
                        .setMessage("确定删除联系人" + dataInfo.get(i).get("name"))
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataInfo.remove(i);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });

    } // onCreate

}
