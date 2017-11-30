package com.example.kendra.tardiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kendra on 2017-11-19.
 */

public class ListViewAdapter extends ArrayAdapter<Event> {
    public ListViewAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        Event event = getItem(position);
        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        title.setText(event.title);
        return convertView;
    }
}
