package com.example.lab4_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈昱宪 on 2016/10/23.
 */

public class MyAdapter extends BaseAdapter {
    private Context _context;
    private List<MyFruit> fruitList = new ArrayList<>();

    public MyAdapter(Context _c, List<MyFruit> _l) {
        _context = _c;
        fruitList = _l;
    }

    @Override
    public int getCount() {
        return this.fruitList.size();
    }

    @Override
    public Object getItem(int position) {
        if (this.fruitList.get(position) != null)
            return this.fruitList.get(position);
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder._img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder._name = (TextView)convertView.findViewById(R.id.fruitName);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder._img.setBackgroundResource(fruitList.get(position).getId());
        viewHolder._name.setText(fruitList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        public ImageView _img;
        public TextView _name;
    }
}

