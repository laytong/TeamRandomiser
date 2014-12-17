package com.example.laytongilbraith.teamrandomiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.SortedMap;

class StringBoolTreeMapAdapter extends BaseAdapter {
    private final ArrayList<SortedMap<String, Boolean>> list;
    private final Context context;
    private final LayoutInflater myInflater;

    public StringBoolTreeMapAdapter(ArrayList<SortedMap<String,Boolean>> list, Context context){
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
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.row, parent, false);
        }

        CheckedTextView player = (CheckedTextView) convertView.findViewById(R.id.text1);
        String name = list.get(position).firstKey();
        if(name == null){
            name = "";
        }
        player.setText(name);
        player.setChecked(list.get(position).get(name));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
