package com.example.patrick.hackwestern3project;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONObject;


public class Shopping extends YouTubeBaseActivity
{
    Button b;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view);
        onInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("4TIdmcQHNRQ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                youTubePlayerView.initialize("AIzaSyDg91ZbSY1cmLNgi0LqZVdrzjBjRUj1Sko", onInitializedListener);

            }
        });


        //oncreate class
     final   TextView mView=(TextView) findViewById(R.id.description);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.googleapis.com/youtube/v3/videos?id=QgmGeDcnzl4&key=AIzaSyDg91ZbSY1cmLNgi0LqZVdrzjBjRUj1Sko%20&part=snippet,contentDetails,statistics,status";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonInformation = new JSONObject(response);
                            String description = jsonInformation.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description");
                            mView.setText("Response is: "+ description);
                            Log.d("SHOPPING", jsonInformation.toString());
                        } catch (Throwable t) {
                            Log.e("SHOPPING", "Could not parse malformed JSON" + response);
                        }
                   //    mView.setText("Response is: "+ response.substring(0,4000));
                              //mView.setText("Response is: "+ response.substring(0,4000));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        }
    }

