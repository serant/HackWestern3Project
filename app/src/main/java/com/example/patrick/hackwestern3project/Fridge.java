package com.example.patrick.hackwestern3project;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Fridge extends ListActivity {
    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;
    DatabaseHelper myDb;
    ArrayList<String> grocery_names = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        myDb = new DatabaseHelper(this);
        final Cursor res = myDb.getAllData();

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                res.moveToPosition(position);
                Toast.makeText(getApplicationContext(), "INDEX: " + res.getString(0) + "REMOVED: " + res.getString(1), Toast.LENGTH_SHORT).show();
                myDb.deleteData(String.valueOf(position));
                listItems.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        String current_date = "Oct 15";
        if (res.getCount() == 0 ) {
            Log.d("SHOPPING", "ERROR: NO DATA FOUND FOR GROCERIES");
        }
        else
        {
            while (res.moveToNext()) {
                adapter.add(res.getString(1) + "\t" + current_date);
                grocery_names.add(res.getString(1));
            }
            adapter.notifyDataSetChanged();
        }
    }
}
