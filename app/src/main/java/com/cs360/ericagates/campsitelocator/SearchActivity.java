package com.cs360.ericagates.campsitelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH_QUERY = "com.cs360.ericagates.campsitelocator.MESSAGE";
    public static final String SEARCH_FILTER = "com.cs360.ericagates.campsitelocator.FILTER";

    RadioGroup radioGroup;
    RadioButton radioButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);



    }

    /** Called when the user taps the Search button */
    public void search(View view){
        //do something in response to the Search button
        Intent intent = new Intent(this, SearchResultsActivity.class);

        //first get which radio button is chosen for the search parameters
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        //then get the search term
        EditText editText = (EditText) findViewById(R.id.editText);

        //save search terms and parameter into intent to pass to the next activity
        CharSequence filter = radioButton.getText();
        String searchParameter = filter.toString();
        String search = editText.getText().toString();


        intent.putExtra(SEARCH_QUERY, search);
        intent.putExtra(SEARCH_FILTER, searchParameter);
        startActivity(intent);


    }

    /** Called when the user taps the Search For Campsites Near Me button */
    public void mapView(View view) {
        // starts maps activity response to search button
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
}
