package com.example.itamar.googleplaces;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class List_Db_Frag extends Fragment {
    ArrayList<Place> jasonPlaces = new ArrayList<Place>();
    public List_Db_Frag() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        IntentFilter filter = new IntentFilter("com.example.itamar.googleplaces.FINISHED");

        FinishedReciever reciever = new FinishedReciever();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(  reciever , filter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list__db_, container, false);

    }

    class FinishedReciever extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {


            Toast.makeText(getActivity(), "Serviced is finished I'm running from the activity ...", Toast.LENGTH_SHORT).show();

            String json = intent.getExtras().getString("json");

            try {

                JSONObject mainObject = new JSONObject(json);

                JSONArray results = mainObject.getJSONArray("results");

                    for (int i=0; i<results.length();i++){

                            JSONObject location = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");;
                            String name = results.getJSONObject(i).optString("name");
                            String formatted_address = results.getJSONObject(i).optString("formatted_address");
                            double lati  = location.getDouble("lat");
                            double lngi  = location.getDouble("lng");
                            String picture=results.getJSONObject(i).optString("reference");
                            String sLati = ""+lati;
                            String sLng = "" + lngi;

                            Place tempPlace = new Place(name, sLati, sLng, formatted_address,picture);
                            jasonPlaces.add(tempPlace);

                    }


            } catch (JSONException e) {
                e.printStackTrace();

            }


            ListView placeslist= (ListView) getView().findViewById(R.id.listView2);
            MyListAdapter adapter = new MyListAdapter(getActivity(), R.id.textView4, jasonPlaces);

            placeslist.setAdapter(adapter);

            placeslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String name = jasonPlaces.get(i).name;
                    String adress = jasonPlaces.get(i).adress;
                    String lat = jasonPlaces.get(i).latitude;
                    String lng = jasonPlaces.get(i).longitude;

                    Place place = new Place(name,lat,lng,adress);

                    DBCommands commands = new DBCommands(getActivity());

                    commands.addPlace(place);

                    Toast.makeText(getActivity(), "Added a new Place!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    intent.putExtra("refreshList","refreshList");
                    intent.putExtra("adress",adress);
                    intent.putExtra("lat",lat);
                    intent.putExtra("lng",lng);
                    startActivity(intent);


                }
            });

        }
    }

}
