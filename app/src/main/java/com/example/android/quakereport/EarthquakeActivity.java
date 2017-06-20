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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    private EarthQuakeAdapter mAdapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private String USGS_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=0&limit=100";

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        new EarthquakeAsyncTask().execute(USGS_URL);

        // Create a fake list of earthquake locations.
        //final ArrayList<EarthQuake> earthquakes =QueryUtils.extractEarthquakes(USGS_URL);

        // Find a reference to the {@link ListView} in the layout
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
    }


}
