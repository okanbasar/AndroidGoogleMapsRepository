package com.okanbasar.googlemapsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText titleText, latitudeText, longitudeText;
    Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (EditText) findViewById(R.id.titleText);
        latitudeText = (EditText) findViewById(R.id.latitudeText);
        longitudeText = (EditText) findViewById(R.id.longitudeText);
        showButton = (Button) findViewById(R.id.showButton);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,MapsActivity.class);
                i.putExtra("title",titleText.getText().toString().trim());
                i.putExtra("latitude",latitudeText.getText().toString().trim());
                i.putExtra("longitude",longitudeText.getText().toString().trim());
                startActivity(i);
            }
        });


    }
}
