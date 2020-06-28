package com.cs360.ericagates.campsitelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText confirmPassword;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);




        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.password2);
        loginButton = (Button)findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public void registerUser (View view) {
        CampsiteDBHandler dbHandler = new CampsiteDBHandler(this, null, null, 1);
        String nameText = username.getText().toString().trim();
        String passwordText =  password.getText().toString().trim();
        String password2Text =  confirmPassword.getText().toString().trim();


        if (TextUtils.isEmpty(nameText) || !Patterns.EMAIL_ADDRESS.matcher(nameText).matches()){
            username.setError("Please Enter a Valid Email Address");
            return;
        }

        if (TextUtils.isEmpty(passwordText)){
            password.setError("Please Enter a password");
            return;
        }

        if (TextUtils.isEmpty(password2Text)){
            confirmPassword.setError("Please confirm password");
            return;
        }

        if (!passwordText.equals(password2Text)){
            confirmPassword.setError("Passwords do not match");
            return;
        }

        User user = new User(nameText, passwordText);
        //call check User and start Homescreen Activity if true (users doesn't already exist)
        if (dbHandler.checkUser(nameText)){
            dbHandler.addUser(user);
            //Confirmation Message
            Toast.makeText(CreateAccountActivity.this,"Successfully Created!",Toast.LENGTH_LONG).show();
        } else {
            //Rejection Message
            Toast.makeText(CreateAccountActivity.this,"Email already in use. Try Again!",Toast.LENGTH_LONG).show();
            username.setText(null);
            password.setText(null);
            confirmPassword.setText(null);
        }






    }
}
