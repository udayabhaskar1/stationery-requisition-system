package com.example.logicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Button loginButton;
    EditText userName, passWord;
    boolean isUserFound;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        loginButton = (Button) findViewById(R.id.loginbutton);
        userName = (EditText) findViewById(R.id.logIn);
        passWord = (EditText) findViewById(R.id.Password);
        loginButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v){

        }

    } );


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                JSONObject jsonObj = new JSONObject();
                if (userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty()) {

                    return;
                }
                else
                {
                    isUserFound = jsonObj.(userName.getText().toString(),passWord.getText().toString());
                    if(isUserFound)
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        return;
                    }
                }
            }
        });
    }


}