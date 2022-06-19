package com.firstapp.fa_adarshbhatia_c0852822_android;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterTest  extends ArrayAdapter
{
    Activity activity;
    List<Bean> addressBeanList;

    public AdapterTest(Activity activity, List<Bean> addressBeanList)
    {
        super(activity, R.layout.custom, addressBeanList);

        this.activity=activity;
        this.addressBeanList=addressBeanList;
    }


    public void remove(int position) {
        addressBeanList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.custom,null, true);

        TextView tv_adname=v.findViewById(R.id.tv_adname);
        TextView tv_address=v.findViewById(R.id.tv_address);
        TextView tv_createdOn=v.findViewById(R.id.tv_createdOn);
        TextView tv_visited=v.findViewById(R.id.tv_visited);


        Bean b = addressBeanList.get(position);

        tv_adname.setText(b.getPlacename());
        tv_address.setText(b.getAddress());
        tv_createdOn.setText(b.getCreatedon());
        tv_visited.setText(b.getVisited());

        return v;
    }
}
