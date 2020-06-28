package com.cs360.ericagates.campsitelocator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;


public class HomescreenActivity extends AppCompatActivity {
    String email;
    private LoginButton login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        login = (LoginButton) findViewById(R.id.fb_login_button);


        //Get the intent that started this activity and extract the string
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");



    }

    /** Called when the user taps the Search button */
    public void startSearch(View view) {
        // Goes to Search Activity in response to  button
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    /** Called when the user taps the Favorites button */
    //TODO: create Favorites activity
    public void myFavorites(View view) {
        // Goes to Favorites Activity in response to  button
        Intent intent = new Intent(this, FavoritesActivity.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);

    }

    /** Called when the user taps the Add/Update button */
    public void addCampsite(View view) {
        // Goes to Add/Update Activity in response to button
        Intent intent = new Intent(this, AddCampsiteActivity.class);
        startActivity(intent);

    }

    /** Called when the user taps the Logout of Facebook button */
    public void logoutFacebook(View v){
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);

    }


}
