package com.example.experience4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈昱宪 on 2016/10/24.
 */

public class FruitAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Fruit> fruits;

    public FruitAdapter(Context _context, List<Fruit> _fruits) {
        mcontext = _context;
        fruits = _fruits;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder._img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder._name = (TextView)convertView.findViewById(R.id.fruitName);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder._img.setBackgroundResource(fruits.get(position).getImg());
        viewHolder._name.setText(fruits.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        public ImageView _img;
        public TextView _name;
    }
}
