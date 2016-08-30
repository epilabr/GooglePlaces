package com.example.itamar.googleplaces;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity {
    double latitude;
    double longitude;
    String name;
    String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final int placeID = getIntent().getIntExtra("id",-1);


        DBCommands commands= new DBCommands(MapActivity.this);
        Cursor resultCursor= commands.getDataFromDBAsCursor(placeID);

        if(resultCursor.moveToNext()) {
            name = resultCursor.getString(resultCursor.getColumnIndex(DBConstants.placeName));
            adress = resultCursor.getString(resultCursor.getColumnIndex(DBConstants.placeAdress));
         //   String link = resultCursor.getString(resultCursor.getColumnIndex(DBConstants.placePic));
            String lat = resultCursor.getString(resultCursor.getColumnIndex(DBConstants.placeLat));
            latitude = Double.parseDouble(lat);
            String lng = resultCursor.getString(resultCursor.getColumnIndex(DBConstants.placeLng));
            longitude = Double.parseDouble(lng);
        }
        TextView placeNameET = (TextView) findViewById(R.id.placeName);
        TextView placeAdressET = (TextView) findViewById(R.id.placeAdress);

        placeNameET.setText(name);
        placeAdressET.setText(adress);

        FragmentManager manager= getFragmentManager();
        MapFragment mapFragment= (MapFragment) manager.findFragmentById(R.id.mapfragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    // setup map position and zoom
                    LatLng position = new LatLng(latitude,longitude);
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(name));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
                    googleMap.moveCamera(update);


                    //  googleMap



            }
        });

    }
}
