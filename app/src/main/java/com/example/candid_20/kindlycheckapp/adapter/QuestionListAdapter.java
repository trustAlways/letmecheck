package com.example.candid_20.kindlycheckapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.candid_20.kindlycheckapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<String> question_list3;
    private Context context;

    public QuestionListAdapter(Context context, ArrayList<String> question_list2) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.question_list3 = question_list2;
    }


    @Override
    public int getCount() {
        return question_list3.size();
    }

    @Override
    public Object getItem(int position) {
        return question_list3.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionListAdapter.ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new QuestionListAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.select_services_adp, parent, false);
            spinnerHolder.cuisineslistitem = (TextView) convertView.findViewById(R.id.cuisineslistitem);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (QuestionListAdapter.ViewHolder) convertView.getTag();
        }
        String service_name=question_list3.get(position);


        String upperString_service_name = service_name.substring(0,1).toUpperCase() + service_name.substring(1);


        spinnerHolder.cuisineslistitem.setText(upperString_service_name);

        return convertView;
    }

    class ViewHolder {
        TextView cuisineslistitem, cuisineslistitemid;
    }
}
