package com.dkv.applocker.controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dkv.applocker.R;
import com.dkv.applocker.model.AppDisplayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends BaseAdapter {

    private Activity aContext;
    private List<AppDisplayer> listApp;
    private LayoutInflater layoutInflater;

    public AppListAdapter(Activity aContext, List<AppDisplayer> listApp){
        this.aContext = aContext;
        this.listApp = listApp;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listApp.size();
    }

    @Override
    public Object getItem(int position) {
        return listApp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //Tham chiếu đến layout

        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.activity_app_list_adapter, null);
            convertView = LayoutInflater.from(aContext).inflate(R.layout.activity_app_list_adapter,null);
            holder = new ViewHolder();
            holder.appIC = convertView.findViewById(R.id.imgAppIC);
            holder.appName = convertView.findViewById(R.id.txtAppName);
//            holder.appStatus = (Switch) convertView.findViewById(R.id.btnStatus);

            //gắn vào convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.appIC.setImageDrawable(listApp.get(position).getIcon());
        holder.appName.setText(listApp.get(position).getPackageName());
//        holder.appStatus;
        return convertView;
    }
    static class ViewHolder {
        ImageView appIC;
        TextView appName;
//        Switch appStatus;

    }
}
