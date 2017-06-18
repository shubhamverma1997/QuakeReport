package com.example.android.quakereport;

/**
 * Created by shubham on 6/17/17.
 */

public class EarthQuake {
    private String mPlace;
    private double mMagnitude;
    private long mDate;
    private String mUrl;

    public EarthQuake(String str,double mag,long date,String url)
    {
        mPlace=str;
        mMagnitude=mag;
        mDate=date;
        mUrl=url;
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
    public String getUrl()
    {
        return mUrl;
    }
}
