package com.cs360.ericagates.campsitelocator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCampsiteActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;

    EditText name;
    EditText address;
    EditText cityState;
    EditText zip;
    EditText feature;
    EditText phone;
    EditText details;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campsite);

        //check permissions and version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        name = (EditText)findViewById(R.id.siteName);
        address = (EditText)findViewById(R.id.Address);
        cityState = (EditText)findViewById(R.id.cityState);
        zip = (EditText)findViewById(R.id.zip);
        feature = (EditText)findViewById(R.id.feature);
        phone = (EditText)findViewById(R.id.phone);
        details = (EditText)findViewById(R.id.details);
        imageView = (ImageView)findViewById(R.id.imageView2);



    }

    public void addCampsite (View view) {
        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);
        String nameText = name.getText().toString().trim();
        String addressText =  address.getText().toString().trim();
        String cityStateText =  cityState.getText().toString().trim();
        String zipText =  zip.getText().toString().trim();
        String featureText =  feature.getText().toString().trim();
        String phoneText =  phone.getText().toString().trim();
        String detailsText =  details.getText().toString().trim();

        if (TextUtils.isEmpty(nameText)){
            name.setError("Please Enter a Campsite Name");
            return;
        }
        if (TextUtils.isEmpty(addressText)){
            address.setError("Please Enter a Campsite address");
            return;
        }
        if (TextUtils.isEmpty(cityStateText)){
            cityState.setError("Please Enter a Campsite City/State");
            return;
        }
        if (TextUtils.isEmpty(zipText)){
            zip.setError("Please Enter a Campsite zip code");
            return;
        }
        if (TextUtils.isEmpty(featureText)){
            feature.setError("Please Enter a Campsite feature");
            return;
        }
        if (TextUtils.isEmpty(phoneText)){
            phone.setError("Please Enter a Campsite phone number");
            return;
        }
        if (TextUtils.isEmpty(detailsText)){
            details.setError("Please Enter a Campsite details");
            return;
        }




        Campsite campsite = new Campsite(nameText, addressText,cityStateText, zipText, featureText, phoneText, detailsText);
        if (dbHandler.checkCampsite(nameText)){
            dbHandler.addCampsite(campsite);
            //Confirmation Message
            Toast.makeText(AddCampsiteActivity.this,"Successfully Added!",Toast.LENGTH_LONG).show();
        }
        else {
            //Error Message
            Toast.makeText(AddCampsiteActivity.this, "Already in Database!", Toast.LENGTH_LONG).show();
        }

    }

    public void updateCampsite (View view) {
        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);

        String nameText = name.getText().toString().trim();
        String addressText =  address.getText().toString().trim();
        String cityStateText =  cityState.getText().toString().trim();
        String zipText =  zip.getText().toString().trim();
        String featureText =  feature.getText().toString().trim();
        String phoneText =  phone.getText().toString().trim();
        String detailsText =  details.getText().toString().trim();

        if (TextUtils.isEmpty(nameText)){
            name.setError("Please Enter a Campsite Name");
            return;
        }
        if (TextUtils.isEmpty(addressText)){
            address.setError("Please Enter a Campsite address");
            return;
        }
        if (TextUtils.isEmpty(cityStateText)){
            cityState.setError("Please Enter a Campsite City/State");
            return;
        }
        if (TextUtils.isEmpty(zipText)){
            zip.setError("Please Enter a Campsite zip code");
            return;
        }
        if (TextUtils.isEmpty(featureText)){
            feature.setError("Please Enter a Campsite feature");
            return;
        }
        if (TextUtils.isEmpty(phoneText)){
            phone.setError("Please Enter a Campsite phone number");
            return;
        }
        if (TextUtils.isEmpty(detailsText)){
            details.setError("Please Enter a Campsite details");
            return;
        }

        Campsite campsite = new Campsite(nameText, addressText, cityStateText, zipText, featureText, phoneText, detailsText);

        int result = dbHandler.updateCampsite(campsite);

        if (result != 0){
            //Confirmation Message
            Toast.makeText(AddCampsiteActivity.this,"Campsite: " + nameText + " Update!",Toast.LENGTH_LONG).show();
            name.setText("");
            address.setText("");
            cityState.setText("");
            zip.setText("");
            feature.setText("");
            phone.setText("");
            details.setText("");
        } else {
            //Confirmation Message
            Toast.makeText(AddCampsiteActivity.this,"Campsite not found!",Toast.LENGTH_LONG).show();


        }





    }

    public void deleteCampsite (View view) {
        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);
        String nameText = name.getText().toString().trim();

        if (TextUtils.isEmpty(nameText)){
            name.setError("Please Enter a Campsite Name");
            return;
        }

        boolean result = dbHandler.deleteCampsite(nameText);
        if (result) {
            //Confirmation Message
            Toast.makeText(AddCampsiteActivity.this,"Campsite: " + nameText + " Deleted!",Toast.LENGTH_LONG).show();
            name.setText("");
            address.setText("");
            cityState.setText("");
            zip.setText("");
            feature.setText("");
            phone.setText("");
            details.setText("");
        } else {
            //Confirmation Message
            Toast.makeText(AddCampsiteActivity.this,"Campsite not found!",Toast.LENGTH_LONG).show();

        }

    }

    //method called when upload photo is clicked
    public void uploadPhoto(View view){
        //allows gallery to be opened
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE); //start Intent and get result
    }

    //method that is called when a user has selected a picture from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check if Gallery called this method,  result is ok, and data is not null
        if ((requestCode == RESULT_LOAD_IMAGE) && (resultCode == RESULT_OK) && (data != null)){
            //get address of image that has been selected
            Uri selectedImage = data.getData();
            //display it in imageView2
            imageView.setImageURI(selectedImage);


        }
    }
}
