package com.example.itamar.googleplaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Meital on 25/07/2016.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper {


    public MySqlOpenHelper(Context context) {
        super(context, DBConstants.databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String command="CREATE TABLE  "+DBConstants.tableName+" ( "+DBConstants.idColumn+"  INTEGER PRIMARY KEY AUTOINCREMENT," +
                " "+ DBConstants.placeName +" TEXT," +
                " "+ DBConstants.placeAdress +" TEXT," +
                " "+ DBConstants.placeLat +" TEXT," +
                " "+ DBConstants.placeLng+" TEXT );";
        db.execSQL(command);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
