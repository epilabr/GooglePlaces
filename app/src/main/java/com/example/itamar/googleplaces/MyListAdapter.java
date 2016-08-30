package com.example.itamar.googleplaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Itamar on 8/18/2016.
 */
    public class MyListAdapter extends ArrayAdapter<Place> {

        List<Place> allPlaces;
        Context c;

        public MyListAdapter(Context context, int resource, List<Place> objects) {
            super(context, resource, objects);
            allPlaces=objects;
            c=context;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v= convertView;

            if(convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(c);
                v= inflater.inflate(R.layout.list_item, null);
            }

            TextView nameTV= (TextView) v.findViewById(R.id.textView4);
            TextView priceTV= (TextView) v.findViewById(R.id.textView5);
            ImageView picture= (ImageView) v.findViewById(R.id.imageView2);

            final Place tempPlace= allPlaces.get(position);
            String thisImage= "https://maps.googleapis.com/maps/api/place/photo?maxheight=80&maxwidth=80&photoreference="+tempPlace.image+"&key=AIzaSyDmDuBBI1JVwkKp8VtmyIwzhz4Nujl_Xvo";
            nameTV.setText(tempPlace.name);
            priceTV.setText(""+tempPlace.adress);
            Picasso.with(c).load(thisImage).fit().into(picture);

         //   ImageView placeImage= (ImageView) v.findViewById(R.id.imageView2);
         // placeImage.setImageResource(R.id.image);






            return v;

        }
    }