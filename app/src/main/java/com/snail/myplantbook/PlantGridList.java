package com.snail.myplantbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlantGridList extends Activity {

    MyDBHandler dbHandler;
    GridView gridPlantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_grid_list);
        dbHandler = new MyDBHandler(this, null, null, 1);
        gridPrintDatabaseList();
        gridPlantList.setOnItemClickListener(onGridItemClick);
    }

    public void gridPrintDatabaseList() {

        Cursor cursor = dbHandler.plantTableReturn();

        String[] fromColumns = {dbHandler.COLUMN_ID, dbHandler.COLUMN_PLANTNAME};
        int[] toViews = {R.id.gridPlantDatabaseId, R.id.gridListPlantName};

        SimpleCursorAdapter gridAdapter = new SimpleCursorAdapter(this, R.layout.custom_grid_item, cursor, fromColumns, toViews, 0);

        gridPlantList = (GridView) findViewById(R.id.plantListGridView);
        gridPlantList.setAdapter(gridAdapter);
    }

    private AdapterView.OnItemClickListener onGridItemClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String plantID = ((TextView) view.findViewById(R.id.gridPlantDatabaseId)).getText().toString();

            Intent I = new Intent(PlantGridList.this, PlantDetails.class);
            I.putExtra("plant_i", plantID);
            startActivity(I);
        }
    };
}
