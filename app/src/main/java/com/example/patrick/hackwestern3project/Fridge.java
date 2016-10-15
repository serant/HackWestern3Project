package com.example.patrick.hackwestern3project;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Fridge extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fridge);
        init();
    }

    public void goToFridge(){
    }

    public void init() {
        myDb = new DatabaseHelper(this);
        List<String> groceryNames = new ArrayList<String>();
        List<String> dates = new ArrayList<String>();
        String current_date = "Oct 15";
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0 ) {
            Toast.makeText(this, "CANNOT GET CONTENTS FROM DB", Toast.LENGTH_LONG).show();
        }
        else
        {
            while (res.moveToNext()) {
                groceryNames.add(res.getString(1));
            }
        }

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv1 = new TextView(this);
        tv1.setText(" Product ");
        tv1.setTextColor(Color.WHITE);
        tv1.setTextSize(24);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Date Purchased ");
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(24);
        tbrow0.addView(tv2);
        stk.addView(tbrow0);

        for (int i = 0; i < groceryNames.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(groceryNames.get(i));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(18);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(current_date);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(18);
            tbrow.addView(t2v);
            stk.addView(tbrow);
        }
    }
}
