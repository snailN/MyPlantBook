package com.snail.myplantbook;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlantSearch  extends Activity {

    EditText searchPlantName;
    MyDBHandler dbHandler;
    ListView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_search);

        searchPlantName = (EditText) findViewById(R.id.searchPlantUserInput);
        dbHandler = new MyDBHandler(this, null, null, 2);

    }

    public void searchClick(View view) {

        String pName = searchPlantName.getText().toString();
        printSearchList(pName);
    }

    public void printSearchList(String p) {

        Cursor cursor = dbHandler.searchPlantFromDatabase(p);

        String[] fromColumns = {dbHandler.COLUMN_ID, dbHandler.COLUMN_PLANTNAME, dbHandler.COLUMN_PLANTINFO, dbHandler.COLUMN_PLANTIMAGE,};
        int[] toViews = {R.id.plantDatabaseId, R.id.listPlantTitle, R.id.listPlantInfo, R.id.plantListImage,};

        SimpleCursorAdapter searchListAdapter = new SimpleCursorAdapter(this, R.layout.custom_row, cursor, fromColumns, toViews, 0);

        searchListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(view.getId() == R.id.plantListImage){
                    String s = cursor.getString(columnIndex);
                    //byte[] customListImage = s.getBytes(Charset.forName("UTF-8")); //cursor.getBlob(columnIndex);
                    if(s != null){
                        Uri u = Uri.parse(s);
                        //Bitmap customListBitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                        //Bitmap customListBitmap = BitmapFactory.decodeByteArray(customListImage, 0, customListImage.length);
                        ImageView plantCustomListImage = (ImageView) view;
                        plantCustomListImage.setImageURI(u);
                        return true;
                    }else{
                        ImageView plantCustomListImage = (ImageView) view;
                        plantCustomListImage.setImageDrawable(getResources().getDrawable(R.drawable.rose));
                        return true;                    }
                }else{
                    return false;
                }
            }
        });

        searchList = (ListView) findViewById(R.id.searchPlantList);
        searchList.setAdapter(searchListAdapter);
        searchList.setOnItemClickListener(onSListClick);
    }

    private AdapterView.OnItemClickListener onSListClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String plantID = ((TextView) view.findViewById(R.id.plantDatabaseId)).getText().toString();

            //dbHandler.databaseToStringList(plantID);

            Intent I = new Intent(PlantSearch.this, PlantDetails.class);
            I.putExtra("plant_i", plantID);
            startActivity(I);
        }
    };
}


