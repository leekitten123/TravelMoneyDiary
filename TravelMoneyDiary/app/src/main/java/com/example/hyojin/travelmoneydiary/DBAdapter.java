package com.example.hyojin.travelmoneydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DBAdapter extends BaseAdapter {
    Context context;
    ArrayList<UsageList> ul;
    int layout;
    LayoutInflater inf;

    public DBAdapter (Context context, ArrayList<UsageList> ul, int layout) {
        this.context = context;
        this.ul = ul;
        this.layout = layout;
        inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount () {
        return ul.size();
    }

    @Override
    public Object getItem (int position) {
        return ul.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inf.inflate(layout, null);
        }

        TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView tv3 = (TextView) convertView.findViewById(R.id.textView3);

        UsageList usageList = ul.get(position);

        String date1 = Integer.toString(usageList.date);
        tv1.setText(date1.substring(0,4) + "년 " + date1.substring(4,6) + "월 " + date1.substring(6) + "일");
        tv2.setText(usageList.content);
        tv3.setText(Integer.toString(usageList.price) + " 원");

        return convertView;
    }
}
