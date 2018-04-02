package com.example.ex8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈昱宪 on 2016/11/20.
 */

public class MyAdapter extends BaseAdapter {
    private Context mcontext;
    private List<info> list;

    MyAdapter (Context context, List<info> _lise) {
        mcontext = context;
        list = _lise;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            viewHolder.birthday = (TextView)view.findViewById(R.id.birthday);
            viewHolder.gift = (TextView)view.findViewById(R.id.gift);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(list.get(position).getName());
        viewHolder.birthday.setText(list.get(position).getBirthday());
        viewHolder.gift.setText(list.get(position).getGift());
        return view;
    }

    public class ViewHolder {
        public TextView name, birthday, gift;
    }
}
class info {
    String name, birthday, gift;
    info(String _name,String _birthday, String _gift) {
        name = _name;
        birthday = _birthday;
        gift = _gift;
    }
    public String getName() {return name;}
    public String getBirthday() {return birthday;}
    public String getGift() {return gift;}
    public void setBirthday(String _birthday) { birthday = _birthday;}
    public void setGift(String _gift) { gift = _gift;}
}

