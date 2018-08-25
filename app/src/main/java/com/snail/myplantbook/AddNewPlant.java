package com.snail.myplantbook;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

public class AddNewPlant extends Activity {

    EditText newPlantName;
    EditText newPlantInfo;
    ImageView newPlantImage;
    MyDBHandler dbHandler;
    ListView plantList;
    int GALLERY_CODE = 1;
    Bitmap bitmap = null;
    //byte[] bitmapByteArray;
    String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);

        newPlantName = (EditText) findViewById(R.id.NewPlantNameUserInput);
        newPlantInfo = (EditText) findViewById(R.id.NewPlantInfoUserInput);
        newPlantImage = (ImageView) findViewById(R.id.plantImageAdd);
        dbHandler = new MyDBHandler(this, null, null, 3);

        printDatabaseList();
        plantList.setOnItemClickListener(onListClick);
        registerForContextMenu(plantList);
    }

    public void addPlantButtonClick(View view){
        Plants plant = new Plants(newPlantName.getText().toString(), newPlantInfo.getText().toString(), imageuri);
        dbHandler.addPlant(plant);
        //printDatabase();
        printDatabaseList();

        newPlantName.setText("");
        newPlantInfo.setText("");
    }

    /*Bucky's code-
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        updatedList.setText(dbString);
        newPlantName.setText("");
        newPlantInfo.setText("");
    }*/

    public void printDatabaseList() {

        Cursor cursor = dbHandler.plantTableReturn();

        String[] fromColumns = {dbHandler.COLUMN_ID, dbHandler.COLUMN_PLANTNAME, dbHandler.COLUMN_PLANTINFO, dbHandler.COLUMN_PLANTIMAGE};
        int[] toViews = {R.id.plantDatabaseId, R.id.listPlantTitle, R.id.listPlantInfo, R.id.plantListImage};

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.custom_row, cursor, fromColumns, toViews, 0);

        plantList = (ListView) findViewById(R.id.customPlantList);

        listAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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
                        return true;
                    }
                }else{
                    return false;
                }
            }
        });

        plantList.setAdapter(listAdapter);
    }

     private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String plantID = ((TextView) view.findViewById(R.id.plantDatabaseId)).getText().toString();

            Intent I = new Intent(AddNewPlant.this, PlantDetails.class);
            I.putExtra("plant_i", plantID);
            startActivity(I);
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        String plantID = ((TextView) v.findViewById(R.id.plantDatabaseId)).getText().toString();
        int pID = Integer.parseInt(plantID);
        menu.add(0, pID, 0, "Edit");
        menu.add(0, pID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Edit") {
            Toast.makeText(getApplicationContext(), "Edit Clicked", Toast.LENGTH_LONG).show();
        }else if(item.getTitle()=="Delete") {
            String pl_id = String.valueOf(item.getItemId());
            dbHandler.deletePlant(pl_id);
            printDatabaseList();
        }
        else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public void uploadImageButtonClick(View view){
        Intent imagePick = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePick.setType("image/*");
        startActivityForResult(imagePick, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK){

            Uri targetUri = data.getData();
            imageuri = targetUri.toString();

            try{
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                newPlantImage.setImageBitmap(bitmap);
                //insertPlantImage(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

        }else {
            Toast.makeText(AddNewPlant.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    /*public void insertPlantImage(Bitmap bm){
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, outStream);
        bitmapByteArray = outStream.toByteArray();
    }*/
}

