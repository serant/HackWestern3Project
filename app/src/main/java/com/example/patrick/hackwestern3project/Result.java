package com.example.patrick.hackwestern3project;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Result extends ListActivity {
    final ArrayList<String> descriptionList = new ArrayList<>();
    final ArrayList<String> titleList = new ArrayList<>();
    final ArrayList<String> matching = new ArrayList<>();
    ArrayAdapter<String> adapter;
    final String TAG = "ResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RequestQueue queue = Volley.newRequestQueue(this);

        final ArrayList<String> videoIds = new ArrayList<String>();
        // Get database from storage
        final DatabaseHelper myDb;
        myDb = new DatabaseHelper(this);

        videoIds.add("e1Vh9vv2CSU");
        videoIds.add("pWuQVJU2J0k");
        videoIds.add("_SYt-m5uTj8");
        videoIds.add("8nHfQYLQ8Xs");
        videoIds.add("FOv_YBDH6eg");
        videoIds.add("NJ47O2vg2XA");
        videoIds.add("3_EgNjK3hqQ");
        videoIds.add("RN7es4ia2OA");

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                matching);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ON CLICK ACTION
                goToShopping(videoIds.get(position));
            }
        });

        for (int i = 0; i < videoIds.size(); i++) {
            final String requestString = "https://www.googleapis.com/youtube/v3/videos?id=" +
                    videoIds.get(i).substring(videoIds.lastIndexOf("=") + 1) +
                    "&key=AIzaSyDg91ZbSY1cmLNgi0LqZVdrzjBjRUj1Sko%20&part=snippet,contentDetails,statistics,status";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    requestString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonInformation = new JSONObject(response);
                                descriptionList.add(
                                        jsonInformation.getJSONArray("items")
                                                .getJSONObject(0)
                                                .getJSONObject("snippet")
                                                .getString("description"));

                                titleList.add(
                                        jsonInformation.getJSONArray("items")
                                            .getJSONObject(0)
                                            .getJSONObject("snippet")
                                            .getString("title"));

//                                UNCOMMENT TO UI DEBUG
//                                Toast.makeText(getApplicationContext(),
//                                        "result: " + descriptionList.get(descriptionList.size()-1),
//                                        Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "Added title: " + titleList.get(titleList.size()-1));
//                                Log.d(TAG, "Added description: " + descriptionList.get(descriptionList.size()-1));
                            } catch (Throwable t) {
                                Log.e(TAG, "Could not parse malformed JSON\n" +
                                        "Request URL: " + requestString + "\n" +
                                        "Response: " + response);
                            }

                            final Cursor res = myDb.getAllData();

                            while (res.moveToNext()) {
//                                Toast.makeText(getApplicationContext(), "Starting search...\n" +
//                                        "Item: " + res.getString(1), Toast.LENGTH_SHORT).show();
                                if (descriptionList.get(descriptionList.size()-1).contains(res.getString(1))) {
//                                    Toast.makeText(getApplicationContext(), "Found matching!", Toast.LENGTH_SHORT).show();
                                    matching.add(titleList.get(descriptionList.size()-1));
                                    adapter.notifyDataSetChanged();
//                                    adapter.add(titleList.get(descriptionList.size()-1));

                                }
                            }

                            Log.d(TAG, "Matching titles: " + matching.toString());

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "That didn't work!");
                }
            });
            queue.add(stringRequest);
        }
    }

    private void goToShopping(String recipeId) {
        Intent intent = new Intent(this, Shopping.class);
        intent.putExtra("ID", recipeId);
        startActivity(intent);
    }
}


