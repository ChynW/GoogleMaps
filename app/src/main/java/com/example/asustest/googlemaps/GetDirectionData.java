package com.example.asustest.googlemaps;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Asus on 9/28/2017.
 */

public class GetDirectionData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;


    @Override
    protected String doInBackground(Object... params) {

        mMap = (GoogleMap)params[0];
        url = (String)params[1];
        latLng = (LatLng)params[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        String[] directionList;
        DataParser parser = new DataParser();
        directionList = parser.parseDirection(s);
        displayDirection(directionList);

       // duration = directionList.get("duration");
        //distance = directionList.get("distance");
        //mMap.clear();
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        //markerOptions.draggable(true);
        //markerOptions.title("Duration="+duration);
       // markerOptions.snippet("Distance="+distance);

        //mMap.addMarker(markerOptions);





    }

    public void displayDirection(String[] directionList)
    {

        int count = directionList.length;

        for (int i=0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionList[i]));
            mMap.addPolyline(options);
        }

    }
}
