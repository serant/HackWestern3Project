package com.example.patrick.hackwestern3project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button fridgeButton = (Button)findViewById(R.id.fridgeButton);
        fridgeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToFridge();
            }
        });

        Button myrecipesButton = (Button)findViewById(R.id.myrecipesButton);
        myrecipesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMyRecipes();
            }
        });

        Button shoppingButton = (Button)findViewById(R.id.shoppingButton);
        shoppingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToShopping();
            }
        });

        Button receiptButton = (Button)findViewById(R.id.receiptButton);
        receiptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToReceipt();
            }
        });
    }

    private void goToFridge(){
        Intent intent = new Intent(this, Fridge.class);
        startActivity(intent);
    }

    private void goToMyRecipes(){
        Intent intent = new Intent(this, myRecipes.class);
        startActivity(intent);
    }

    private void goToShopping(){
        Intent intent = new Intent(this, Shopping.class);
        startActivity(intent);
    }

    private void goToReceipt(){
        Intent intent = new Intent(this, Receipt.class);
        startActivity(intent);
    }
}
