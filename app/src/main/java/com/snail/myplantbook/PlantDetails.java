package com.snail.myplantbook;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlantDetails extends Activity {

    MyDBHandler dbase;
    Plants plantDetailsPrint = new Plants();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        Bundle plantNameData = getIntent().getExtras();
        if(plantNameData==null){
            return;
        }

        dbase = new MyDBHandler(this, null, null, 3);

        String plant_i = plantNameData.getString("plant_i");
        //int p_id = Integer.parseInt(plant_i);
        Cursor plantDetailsCursor = dbase.databaseToPlantDetails(plant_i);
        String[] columnnames = plantDetailsCursor.getColumnNames();


        ImageView plantImageView = (ImageView) findViewById(R.id.plantImage);
        TextView plantNameView = (TextView) findViewById(R.id.plantName);
        TextView plantInfoView = (TextView) findViewById(R.id.plantInfo);

        plantDetailsCursor.moveToFirst();
        plantNameView.setText(plantDetailsCursor.getString(plantDetailsCursor.getColumnIndex(columnnames[1])));
        plantInfoView.setText(plantDetailsCursor.getString(plantDetailsCursor.getColumnIndex(columnnames[2])));

        String temps = plantDetailsCursor.getString(plantDetailsCursor.getColumnIndex(columnnames[3]));
        if(temps != null){
            Uri tempu = Uri.parse(temps);
            plantImageView.setImageURI(tempu);
        }else{
            plantImageView.setImageDrawable(getResources().getDrawable(R.drawable.rose));
        }


        //plantDetailsPrint.set_plantname(plantDetailsArray.get(1));
        //plantDetailsPrint.set_plantinfo(plantDetailsArray.get(2));
        //byte[] b = plantDetailsArray.get(3).getBytes();
        //plantDetailsPrint.set_plantimage(b);

        //plantNameView.setText(plantDetailsPrint.get_plantname());
        //plantInfoView.setText(plantDetailsPrint.get_plantinfo());

        /*byte[] plantDetailsImage = plantDetailsPrint.get_plantimage();
        if(plantDetailsImage != null){
            Bitmap plantImageBitmap = BitmapFactory.decodeByteArray(plantDetailsImage, 0, plantDetailsImage.length);
            plantImageView.setImageBitmap(plantImageBitmap);
        }else{
            plantImageView.setImageDrawable(getResources().getDrawable(R.drawable.rose));
        }*/

    }

}
