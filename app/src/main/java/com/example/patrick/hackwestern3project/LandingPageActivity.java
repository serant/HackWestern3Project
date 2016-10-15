package com.example.patrick.hackwestern3project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LandingPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        ImageButton button = (ImageButton)findViewById(R.id.button);
       button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Button();
            }
        });

    }
    private void Button(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
