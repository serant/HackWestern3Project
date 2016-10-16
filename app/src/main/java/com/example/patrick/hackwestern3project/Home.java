package com.example.patrick.hackwestern3project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {
    public static final String TAG = "HomeActivity";
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDb = new DatabaseHelper(this);

        Button fridgeButton = (Button) findViewById(R.id.fridgeButton);
        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFridge();
            }
        });

        Button myrecipesButton = (Button) findViewById(R.id.myrecipesButton);
        myrecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyRecipes();
            }
        });

        Button shoppingButton = (Button) findViewById(R.id.shoppingButton);
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToShopping();
            }
        });

        Button receiptButton = (Button) findViewById(R.id.receiptButton);
        receiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReceipt(v);
            }
        });

        Button resultButton = (Button) findViewById(R.id.resultButton);
        resultButton.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v) {
                goToResult(v);
            }
        });
    }

    private void goToFridge() {
        Intent intent = new Intent(this, Fridge.class);
        startActivity(intent);
    }

    private void goToMyRecipes() {
        Intent intent = new Intent(this, myRecipes.class);
        startActivity(intent);
    }

    private void goToShopping() {
        Intent intent = new Intent(this, Shopping.class);
        startActivity(intent);
    }

    private void goToReceipt(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(OrientationCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code on your receipt");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    private void goToResult(View view) {
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(TAG, "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "Scanned");
                List<String> receiptList = Arrays.asList(result.getContents().split(","));
                for (int i=0; i<receiptList.size(); i++){
                    boolean isInserted = myDb.insertData(receiptList.get(i));
                    if (isInserted) {
                        Log.d(TAG, "Successfully entered " + receiptList.get(i) + " in DB");
                        if (i == (receiptList.size()-1)){
                            Toast.makeText(this, "Updated your fridge!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Log.d(TAG, "ERROR: DB WRITE FAILURE FOR: " + receiptList.get(i));
                        if (i == (receiptList.size()-1)){
                            Toast.makeText(this, "ERROR: FAILED TO WRITE TO DB", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}