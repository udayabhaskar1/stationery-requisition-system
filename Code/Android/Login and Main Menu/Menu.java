package com.example.logicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button appointBt =findViewById(R.id.appoint);
        if (appointBt!= null)
            appointBt.setOnClickListener(this);

        Button collectionBt = findViewById(R.id.collection);
        if(collectionBt!=null)
            collectionBt.setOnClickListener(this);

        Button authoriseBt = findViewById(R.id.authorise);
        if(authoriseBt!= null)
            authoriseBt.setOnClickListener(this);

    }

    @Override
    public  void onClick(View v)
    {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.appoint:
                intent = new Intent(Menu.this, ); // add in the page name that is going to be shown

            case R.id.authorise:
                intent = new intent(Menu.this,); // add in the page name that is going to be shown

            case R.id.collection:
                intent = new intent(Menu.this, ); // add in the page name that is going to be shown
        }
    }

}
