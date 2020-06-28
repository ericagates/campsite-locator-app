package com.cs360.ericagates.campsitelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class UserLoginActivity extends AppCompatActivity {
    EditText usernameText, passwordText;
    private LoginButton login;
    private TextView displayName;
    CallbackManager callbackManager;
    private CampsiteDBHandler dbHandler;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Password = "passKey";
    public static final String EMAIL = "email";

    SharedPreferences sharedPreferences = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);

        try{
            dbHandler.createDataBase();
        }catch(Exception e){
            e.printStackTrace();
        }

        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    MyPREFERENCES,
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Initialize views and preferences
        usernameText=(EditText)findViewById(R.id.username);
        passwordText=(EditText)findViewById(R.id.password);
        //Restore Preferences
        String username = sharedPreferences.getString(Name, null);
        usernameText.setText(username);
        String password = sharedPreferences.getString(Password, null);
        passwordText.setText(password);

        //Initialize view for Facebook login
        displayName = findViewById(R.id.display_name);
        login = (LoginButton) findViewById(R.id.fb_login_button);
        login.setPermissions(Arrays.asList("email", "public_profile"));


        //Facebook Login creating Callback managers
        callbackManager = CallbackManager.Factory.create();

        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Retrieving access token using the LoginResult
                        AccessToken accessToken = loginResult.getAccessToken();
                        //call method to display details once the user has successfully loggedin
                        useLoginInformation(accessToken);

                        //Confirmation Message
                        Toast.makeText(UserLoginActivity.this,"Successful Login.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UserLoginActivity.this, HomescreenActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onCancel() {
                        //cancel Message
                        Toast.makeText(UserLoginActivity.this,"Canceled. Try again.",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //error Message
                        Toast.makeText(UserLoginActivity.this,"Error. Try again.",Toast.LENGTH_LONG).show();

                    }
                });

    }

    //called when the user presses the Save info button
    public void saveInfo(View view){
        //temporarily store username and password
        String name  = usernameText.getText().toString();
        String pass  = passwordText.getText().toString();

        if (TextUtils.isEmpty(name)){
            usernameText.setError("Please Enter your email address");
            return;
        }
        if (TextUtils.isEmpty(pass)){
            passwordText.setError("Please Enter your password");
            return;
        }

        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    MyPREFERENCES,
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save in local SharedPrefernces
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, name);
        editor.putString(Password, pass);
        editor.commit();

        //Confirmation Message
        Toast.makeText(UserLoginActivity.this,"Saved",Toast.LENGTH_LONG).show();

    }


    /** Called when the user taps the LogIn button */
    public void startHomescreen(View view) {
        //temporarily store username and password
        String name  = usernameText.getText().toString();
        String pass  = passwordText.getText().toString();

        dbHandler = new CampsiteDBHandler(this, null, null, 1);

        if (TextUtils.isEmpty(name)){
            usernameText.setError("Please Enter your email address");
            return;
        }
        if (TextUtils.isEmpty(pass)){
            passwordText.setError("Please Enter your password");
            return;
        }

        //call check User and Password and start Homescreen Activity if true
        if (dbHandler.checkUser(name,pass)){
            // Homescreen in response to login button
            Toast.makeText(UserLoginActivity.this,"Successful Login.",3000).show();
            Intent intent = new Intent(this, HomescreenActivity.class);
            intent.putExtra("EMAIL", name);
            startActivity(intent);
        } else {
            //Rejection Message
            Toast.makeText(UserLoginActivity.this,"Invalid Email/Password",Toast.LENGTH_LONG).show();
            usernameText.setText(null);
            passwordText.setText(null);
        }




    }

    /** Called when the user taps the Create One button */
    public void createAccount(View view) {
        // Create Account Screen in response to create button
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);

    }


    /*Pass the login results to the FacebookSDK LoginManager via callbackManager.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    /* Called on  login to upload info from facebook to login page. Retrieved from: https://androidclarified.com/android-facebook-login-example/ */
    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    displayName.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    /*Check facebook login status on start and display info if loggedin - go to next screen*/
    public void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            useLoginInformation(accessToken);
            //Confirmation Message
            Toast.makeText(UserLoginActivity.this,"Successful Login.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserLoginActivity.this, HomescreenActivity.class);
            startActivity(intent);

        } else {
            displayName.setText("Log In With Facebook");
        }
    }
}
