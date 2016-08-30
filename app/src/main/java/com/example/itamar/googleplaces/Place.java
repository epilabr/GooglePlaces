package com.example.itamar.googleplaces;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Itamar on 8/10/2016.
 */
public class Place {

    String name;
    String adress;
    String latitude;
    String longitude;
    String image;

    public Place(String name, String longitude, String latitude, String adress, String image) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adress = adress;
        this.image = image;
    }
    public Place(String name, String latitude, String longitude, String address) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adress = address;

    }

    @Override
    public String toString() {
        return this.name;
    }
}
