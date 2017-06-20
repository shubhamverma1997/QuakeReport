/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity  implements LoaderCallbacks<ArrayList<EarthQuake>>{

    private static final int earthquakeLoaderId=1;
    private EarthQuakeAdapter mAdapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private String USGS_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=0&limit=100";

    /*
    private class EarthquakeAsyncTask extends AsyncTask<String ,Void,ArrayList<EarthQuake>>
    {
        @Override
        protected ArrayList<EarthQuake> doInBackground(String... params) {
            if(params.length<1 || params[0]==null)
                return null;

            return QueryUtils.fetchData(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuake> earthQuakes) {
            mAdapter.clear();
            if(earthQuakes!=null && !earthQuakes.isEmpty())
            {
                mAdapter.addAll(earthQuakes);
            }
        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v("Main Activity"," onCreate started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        //previously when using only AsyncTask
        //new EarthquakeAsyncTask().execute(USGS_URL);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mAdapter.getItem(position).getUrl()));
                if(intent.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(intent);
                }
            }
        });

        mAdapter=new EarthQuakeAdapter(this,new ArrayList<EarthQuake>());

        earthquakeListView.setAdapter(mAdapter);

        LoaderManager loaderManager=getLoaderManager();
        Log.v("Main Activity","LoaderManager");
        loaderManager.initLoader(earthquakeLoaderId,null,this);
        Log.v("Main Activity","initLoader executed");
    }

    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int id, Bundle args) {
        Log.v("Main Activity","in onCreateLoader");
        return new EarthQuakeLoader(this,USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> data) {
       mAdapter.clear();

        if(data!=null && !data.isEmpty())
        mAdapter.addAll(data);
        Log.v("Main Ac. onLoadFinished","New data set to adapter");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthQuake>> loader) {
        mAdapter.clear();
        Log.v("Main Ac. onLoaderReset"," adapter cleared");
    }
}
