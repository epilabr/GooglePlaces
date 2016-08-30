package com.example.itamar.googleplaces;

import android.*;
import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>

 */
public class SearchService extends IntentService {
    String ifchecked;
    String searchRadius;
    String lat;
    String lng;


    public SearchService() {
        super("SearchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String searchKey = intent.getStringExtra("searchKey");
        ifchecked = intent.getStringExtra("ifChecked");
        searchRadius = intent.getStringExtra("searchRadius");
        lat= intent.getStringExtra("lat");
        lng= intent.getStringExtra("lng");

        new Thread() {
            public void run() {

                String json = downloadJson(searchKey.replaceAll(" ", "%20"));
                Log.d("text", json);
                Intent broadcastMessage = new Intent("com.example.itamar.googleplaces.FINISHED");
                broadcastMessage.putExtra("json", json);
                LocalBroadcastManager.getInstance(SearchService.this).sendBroadcast(broadcastMessage);
            }
        }.start();


        return START_STICKY;
    }

    private String downloadJson(String searchkey) {


        BufferedReader input = null;
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            if (ifchecked.equals("1")) {
                URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius=" + searchRadius + "&name=" + searchkey + "&key=AIzaSyDmDuBBI1JVwkKp8VtmyIwzhz4Nujl_Xvo");
                connection = (HttpURLConnection) url.openConnection();

                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));


                String line = "";
                while ((line = input.readLine()) != null) {

                    response.append(line + "\n");
                }
            } else {


                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + searchkey + "&key=AIzaSyDmDuBBI1JVwkKp8VtmyIwzhz4Nujl_Xvo");


                connection = (HttpURLConnection) url.openConnection();

                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));


                String line = "";
                while ((line = input.readLine()) != null) {

                    response.append(line + "\n");
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {

                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {

                connection.disconnect();
            }

        }
        return response.toString();

    }

}
