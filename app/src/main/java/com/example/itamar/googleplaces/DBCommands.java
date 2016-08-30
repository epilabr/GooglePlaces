package com.example.itamar.googleplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Meital on 25/07/2016.
 */
public class DBCommands {

    Context context;
    MySqlOpenHelper helper;

    public DBCommands(Context c) {

        context = c;
        helper = new MySqlOpenHelper(context);
    }

    public  void addPlace(Place place)
    {
        ContentValues cv= new ContentValues();
        cv.put(DBConstants.placeName, place.name);
        cv.put(DBConstants.placeAdress, place.adress);
        cv.put(DBConstants.placeLat, place.latitude);
        cv.put(DBConstants.placeLng, place.longitude);

        helper.getWritableDatabase().insert(DBConstants.tableName, null,cv );
    }


    public Cursor getDataFromDBAsCursor()
    {
        Cursor tempTableDataCursor=   helper.getReadableDatabase().rawQuery("SELECT * FROM "+DBConstants.tableName, null);

        return  tempTableDataCursor;
    }

    public Cursor getDataFromDBAsCursor(int placeID)
    {
        Cursor tempTableDataCursor=   helper.getReadableDatabase().rawQuery("SELECT * FROM "+DBConstants.tableName+" WHERE _id="+placeID , null);

        return  tempTableDataCursor;
    }

    public void deletePlacesDB() {

        helper.getWritableDatabase().delete(DBConstants.tableName, null, null);
    }

    public ArrayList<Place> getAllPlaces()
    {
        ArrayList<Place> allPlaces= new ArrayList<Place>();

        Cursor cursor= helper.getReadableDatabase().query("placesTable ",null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int nameColumn= cursor.getColumnIndex(DBConstants.placeName);
            String placeName= cursor.getString(nameColumn);

            int addressColumn= cursor.getColumnIndexOrThrow(DBConstants.placeAdress);
            String placeaddress= cursor.getString(addressColumn);

            int latColumn= cursor.getColumnIndexOrThrow(DBConstants.placeLat);
            String placeLat= cursor.getString(latColumn);

            int lngColumn= cursor.getColumnIndexOrThrow(DBConstants.placeLng);
            String placeLng= cursor.getString(lngColumn);

           /* int picColumn= cursor.getColumnIndexOrThrow(DBConstants.placePic);
            String placePic= cursor.getString(picColumn);*/

            Place placeTemp= new Place(placeName,placeLng,placeLat,placeaddress);
            allPlaces.add(placeTemp);

        }
        return allPlaces;
    }
}
