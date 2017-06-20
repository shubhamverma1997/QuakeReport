package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shubham on 6/21/17.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {

    private String mUrl;

    public EarthQuakeLoader(Context context,String url)
    {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        Log.v("EarthQuakeLoader","forceLoad to be called in onStartLoading");
        forceLoad();
    }

    @Override
    public ArrayList<EarthQuake> loadInBackground() {

        Log.v("EarthQuakeLoader","in before loadInBackground");
        if(mUrl==null)
            return null;

        ArrayList<EarthQuake> earthQuakes=QueryUtils.fetchData(mUrl);
        return earthQuakes;
    }
}
