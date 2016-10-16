package com.example.patrick.hackwestern3project;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Result extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String TAG = "ResultsActivity";
        final ArrayList<String> descriptionList = new ArrayList<String>();
        ArrayList<String> videoIds = new ArrayList<String>();
        DatabaseHelper myDb;

        myDb = new DatabaseHelper(this);

        videoIds.add("e1Vh9vv2CSU");
        videoIds.add("pWuQVJU2J0k");
        videoIds.add("_SYt-m5uTj8");
        videoIds.add("8nHfQYLQ8Xs");
        videoIds.add("FOv_YBDH6eg");
        videoIds.add("NJ47O2vg2XA");
        videoIds.add("3_EgNjK3hqQ");
        videoIds.add("RN7es4ia2OA");


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

//                                UNCOMMENT TO UI DEBUG
//                                Toast.makeText(getApplicationContext(),
//                                        "result: " + descriptionList.get(descriptionList.size()-1),
//                                        Toast.LENGTH_SHORT).show();

                                Log.d(TAG, descriptionList.get(descriptionList.size()-1));
                            } catch (Throwable t) {
                                Log.e(TAG, "Could not parse malformed JSON\n" +
                                        "Request URL: " + requestString + "\n" +
                                        "Response: " + response);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "That didn't work!");
                }
            });
            queue.add(stringRequest);
        }

        // Get database from storage
        myDb.getAllData();
    }
}


