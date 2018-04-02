package com.example.ex9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 陈昱宪 on 2016/11/27.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Weather> weather_list;
    private LayoutInflater mInflater;

    MyRecyclerAdapter(Context context, ArrayList<Weather> items) {
        super();
        weather_list = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_item, viewGroup, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        holder.date = (TextView)view.findViewById(R.id.date);
        holder.forecast =(TextView)view.findViewById(R.id.forecast);
        holder.temperature_forecast = (TextView)view.findViewById(R.id.temperature_forecast);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.date.setText(weather_list.get(i).getDate());
        recyclerViewHolder.forecast.setText(weather_list.get(i).getForecast());
        recyclerViewHolder.temperature_forecast.setText(weather_list.get(i).getTemperature_forecast());
    }

    @Override
    public int getItemCount() {
        return weather_list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView forecast;
        TextView temperature_forecast;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
class Weather {
    private String date, forecast, temperature_forecast;
    Weather(String _d, String _f, String _t) {
        date = _d;
        forecast = _f;
        temperature_forecast = _t;
    }
    String getDate() {return date;}
    String getForecast() {return forecast;}
    String getTemperature_forecast() {return temperature_forecast;}
}
