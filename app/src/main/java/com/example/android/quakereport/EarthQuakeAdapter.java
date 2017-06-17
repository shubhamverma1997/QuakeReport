package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        magnitude.setText(formattedmag(variable.getMagnitude()));

        String[] parts=parts=variable.getPlace().split("of");;
        String part1,part2;
        //Splitting thew string
        if(variable.getPlace().contains("of")==true)
        {
            part1=parts[0]+" of";
            part2=parts[1];
        }
        else
        {
            part1="Near to";
            part2=parts[0];
        }

        TextView place=(TextView) listview.findViewById(R.id.place);
        place.setText(part2);

        TextView near=(TextView) listview.findViewById(R.id.nearby);
        near.setText(part1);

        Date d=new Date(variable.getDate());
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MMM dd , yyyy");
        String dateString=simpleDateFormat.format(d);
;
        TextView date=(TextView) listview.findViewById(R.id.date);
        date.setText(dateString);

        SimpleDateFormat simpletime=new SimpleDateFormat("HH:mm");
        String timeString=simpletime.format(d);

        TextView time=(TextView) listview.findViewById(R.id.time);
        time.setText(timeString);

        return listview;
    }

    public String formattedmag(double magnitude)
    {
        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }
}
