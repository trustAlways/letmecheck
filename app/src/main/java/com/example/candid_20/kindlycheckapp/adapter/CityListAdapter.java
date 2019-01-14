
package com.example.candid_20.kindlycheckapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



;

import com.example.candid_20.kindlycheckapp.R;
import com.example.candid_20.kindlycheckapp.bean.for_service_interested.For_Service_Interested_Bean;
import com.example.candid_20.kindlycheckapp.bean.for_service_interested.For_Service_LXinterested_Bean;

import java.util.List;



public class CityListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<For_Service_LXinterested_Bean> cityLIsts;
    private Context context;

    public CityListAdapter(Context context, List<For_Service_LXinterested_Bean> cityLIsts) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cityLIsts = cityLIsts;
    }


    @Override
    public int getCount() {
        return cityLIsts.size();
    }

    @Override
    public Object getItem(int position) {
        return cityLIsts.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.select_services_adp, parent, false);
            spinnerHolder.cuisineslistitem = (TextView) convertView.findViewById(R.id.cuisineslistitem);
            convertView.setTag(spinnerHolder);
        }
        else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }
        String service_name=cityLIsts.get(position).getServicesName();


        String upperString_service_name = service_name.substring(0,1).toUpperCase() + service_name.substring(1);


        spinnerHolder.cuisineslistitem.setText(upperString_service_name);

        return convertView;
    }

    class ViewHolder {
        TextView cuisineslistitem, cuisineslistitemid;
    }
}
