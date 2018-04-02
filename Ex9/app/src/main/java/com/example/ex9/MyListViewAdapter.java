package com.example.ex9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈昱宪 on 2016/11/26.
 */

public class MyListViewAdapter extends BaseAdapter {
    private List<info> infos;
    private Context context;

    MyListViewAdapter(Context mcontext, List<info> _infos) {
        infos = _infos;
        context = mcontext;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ListViewViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            viewHolder = new ListViewViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.detail = (TextView) view.findViewById(R.id.detail);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ListViewViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(infos.get(position).getTitle());
        viewHolder.detail.setText(infos.get(position).getDetail());

        return view;
    }

    class ListViewViewHolder {
        public TextView title, detail;
    }
}

class info {
    private String title, detail;

    info() {
        title = "Exception";
        detail = "Exception";
    }
    info(String _t, String _d) {
        title = _t;
        detail = _d;
    }

    public String getTitle() {return title;}
    public String getDetail() {return detail;}

    public void setTitle(String _t) {title = _t;}
    public void setDetail(String _d) {detail = _d;}
}