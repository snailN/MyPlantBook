package com.snail.myplantbook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void addPlantIconClick(View view) {

        Intent I = new Intent(this, AddNewPlant.class);
        startActivity(I);
    }

    public void searchPlantIconClick(View view) {

        Intent I = new Intent(this, PlantSearch.class);
        startActivity(I);
    }

    public void listPlantIconClick(View view) {

        Intent I = new Intent(this, PlantGridList.class);
        startActivity(I);
    }

}
