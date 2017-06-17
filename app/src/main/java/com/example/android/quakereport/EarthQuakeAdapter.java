package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shubham on 6/17/17.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
    public EarthQuakeAdapter(Context context, ArrayList<EarthQuake> data)
    {
        super(context,0,data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listview=convertView;
        if(listview==null)
        {
            listview= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        EarthQuake variable=getItem(position);

        TextView magnitude=(TextView) listview.findViewById(R.id.mag);
        magnitude.setText(""+variable.getMagnitude());

        TextView place=(TextView) listview.findViewById(R.id.place);
        place.setText(variable.getPlace());

        TextView date=(TextView) listview.findViewById(R.id.date);
        date.setText(variable.getDate());

        return listview;
    }
}
