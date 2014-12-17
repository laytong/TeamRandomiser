package com.example.laytongilbraith.teamrandomiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class StringArrayListAdapter extends BaseAdapter {
    private final ArrayList<String> list;
    private final Context context;
    private final LayoutInflater myInflater;

    public StringArrayListAdapter(ArrayList<String> list, Context context) {
        this.context = context;
        this.list = list;
        myInflater = LayoutInflater.from(context);
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
        if (convertView == null) {
            convertView = myInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(list.get(position));
        return convertView;
    }
}
