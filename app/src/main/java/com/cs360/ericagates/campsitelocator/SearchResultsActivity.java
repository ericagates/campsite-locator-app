package com.cs360.ericagates.campsitelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView mResults;
    private CampsiteAdapter mCampsiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_campsites);
        mResults = (TextView)findViewById(R.id.tv_num_results);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /*
         * The CampsiteAdapter is responsible for linking our campsite data with the Views that
         * will display the campsite data.
         */
        mRecyclerView.setLayoutManager(layoutManager);



        //Get the intent that started this activity and extract the strings
        Intent intent = getIntent();
        String searchTerms = intent.getStringExtra(SearchActivity.SEARCH_QUERY);
        String searchType = intent.getStringExtra(SearchActivity.SEARCH_FILTER);


        //Initialize campsite database handler and array list to store campsite information
        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);
        List<Campsite> campsites = new ArrayList<Campsite>();

        /* Call method to search the database of campsites based on the search type and search terms
         * from Search Activity and return in campsites array list
         */
        try {
            campsites = dbHandler.searchCampsite(searchTerms, searchType);

            // sorts arraylist alphabetically
            Collections.sort(campsites, new Comparator<Campsite>() {
                @Override
                public int compare(Campsite o1, Campsite o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        /*
         * The CampsiteAdapter is responsible for linking the campsite data with the Views that
         * will end up displaying the retrieved campsite data.
         */
        mResults.setText(String.valueOf(campsites.size()));
        CampsiteAdapter mCampsiteAdapter = new CampsiteAdapter(campsites);

        /* Setting the adapter attaches it to the RecyclerView in the layout. */
        mRecyclerView.setAdapter(mCampsiteAdapter);



    }

    /** Called when the user taps the Map button */
    public void mapView(View view) {
        // Login in response to start button
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
}
