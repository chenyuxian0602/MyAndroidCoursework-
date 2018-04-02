package com.example.experience3_basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈昱宪 on 2016/10/16.
 */


public class My_adapter extends BaseAdapter {
    private Context context;
    private List<my_people> list;

    public My_adapter(Context _context, List<my_people> _list) {
        context = _context;
        this.list = _list;
    }

    @Override
    public int getCount() {
        if (list == null) return 0;
        else return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        if (this.list == null) return null;
        else return this.list.get(i);
    }

    @Override
    public long getItemId (int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder._log = (TextView)convertView.findViewById(R.id.log);
            viewHolder._name = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder._log.setText(list.get(i).getName().substring(0, 1));
        viewHolder._name.setText(list.get(i).getName());
        return convertView;
    }

    private class ViewHolder {
        public TextView _log;
        public TextView _name;
    }
}
