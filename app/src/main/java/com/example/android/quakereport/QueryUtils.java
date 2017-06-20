package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

// For parsing JSON string and return it as an ArrayList
public final class QueryUtils {

    private QueryUtils() {
    }

    public static URL createURL(String urlString)
    {
        if(urlString==null||urlString=="")
            return null;
        URL url=null;
        try{
            url=new URL(urlString);
        } catch (MalformedURLException e)
        {
            Log.v("QueryUtils","Malformed Url",e);
        }
        return url;
    }

    public static ArrayList<EarthQuake> fetchData(String urlString)
    {
        URL url=createURL(urlString);
        String JSONResponse=null;
        try{
            JSONResponse=makeRequestOverInternet(url);
        } catch (IOException e)
        {
            Log.e("QueryUtils","Error closing Input Stream");
        }

        return extractEarthquakes(JSONResponse);
    }

    //Returns JSON response
    public static String makeRequestOverInternet(URL url) throws IOException
    {
        String JSONResponse="";
        if(url==null)
            return null;

        HttpURLConnection UrlConnection=null;
        InputStream inputStream=null;

        try{
            UrlConnection=(HttpURLConnection) url.openConnection();
            UrlConnection.setRequestMethod("GET");
            UrlConnection.setConnectTimeout(10000);
            UrlConnection.setReadTimeout(15000);
            UrlConnection.connect();

            if(UrlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                inputStream=UrlConnection.getInputStream();
                JSONResponse=convertToJsonString(inputStream);
            }
            else
            {
                Log.v("QueryUtils","Error in opening Url");
            }

        } catch(IOException e){
            Log.e("QueryUtils","Problem in getting input stream. ",e);
        }
        finally {
            if(UrlConnection!=null)
                UrlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();

        }
        return JSONResponse;

    }

    public static String convertToJsonString(InputStream inputStream) throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null)
            {
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<EarthQuake> extractEarthquakes(String JSONResponse) {

        String place;
        double magnitude;
        long time;
        String url;

        if(JSONResponse==null)
            return null;

        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
        try {
            JSONObject obj=new JSONObject(JSONResponse);
            JSONArray arr=obj.getJSONArray("features");

            for(int i=0;i<arr.length();i++)
            {
                JSONObject quakeinstance=arr.getJSONObject(i);
                JSONObject properties=quakeinstance.getJSONObject("properties");
                place=properties.getString("place");
                magnitude=properties.getDouble("mag");
                time=properties.getLong("time");
                url=properties.getString("url");

                /*Date d=new Date(time);
                SimpleDateFormat sdf=new SimpleDateFormat("MMM d, yyyy");
                String date=sdf.format(d);
                */
                earthquakes.add(new EarthQuake(place,magnitude,time,url));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}