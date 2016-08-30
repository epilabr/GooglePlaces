package com.example.itamar.googleplaces;


import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class List_Frag extends Fragment {
    ListView lv;
    SimpleCursorAdapter adapter;
    public List_Frag() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_, container, false);



    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        refreshList();

    }

    public  void  refreshList()
    {

        ListView lv= (ListView)getView().findViewById(R.id.listView);
        DBCommands commands= new DBCommands(getActivity());
        final Cursor tempTableDataCursor =commands.getDataFromDBAsCursor();

        String[] from={DBConstants.placeName, DBConstants.placeAdress};
        int[] to= { R.id.textView4, R.id.textView5};

        adapter= new SimpleCursorAdapter(getActivity(), R.layout.list_item ,tempTableDataCursor, from, to );
        lv.setAdapter(adapter);

        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(tempTableDataCursor.moveToPosition(position))
                {
                    int placeID= tempTableDataCursor.getInt(tempTableDataCursor.getColumnIndex("_id"));

                    Intent intent= new Intent(getActivity(), MapActivity.class);
                    intent.putExtra("id", placeID);
                    startActivity(intent);

                }

            }
        });

    }
}
