package com.example.android.quakereport;

/**
 * Created by shubham on 6/17/17.
 */

public class EarthQuake {
    private String mPlace;
    private double mMagnitude;
    private long mDate;

    public EarthQuake(String str,double mag,long date)
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
    public long getDate()
    {
        return mDate;
    }
}
