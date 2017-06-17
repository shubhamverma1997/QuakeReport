package com.example.android.quakereport;

/**
 * Created by shubham on 6/17/17.
 */

public class EarthQuake {
    private String mPlace;
    private double mMagnitude;
    private String mDate;

    public EarthQuake(String str,double mag,String date)
    {
        mPlace=str;
        mMagnitude=mag;
        mDate=date;
    }

    public String getPlace()
    {
        return mPlace;
    }
    public double getMagnitude()
    {
        return mMagnitude;
    }
    public String getDate()
    {
        return mDate;
    }
}
