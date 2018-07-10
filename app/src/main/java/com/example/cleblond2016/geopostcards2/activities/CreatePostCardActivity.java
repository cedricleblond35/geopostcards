package com.example.cleblond2016.geopostcards2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.cleblond2016.geopostcards2.R;

public class CreatePostCardActivity extends AppCompatActivity {

    private EditText edtLatitude;
    private EditText edtLongitude;
    private EditText edtTitle;
    private EditText edtDescription;

    private double actualLatitude;
    private double actualLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_card);

        edtLatitude = findViewById(R.id.create_post_card_latitude);
        edtLongitude = findViewById(R.id.create_post_card_longitude);
        edtTitle = findViewById(R.id.create_post_card_title);
        edtDescription = findViewById(R.id.create_post_card_description);

        Intent intentRecu = getIntent();
        if(intentRecu != null) {
            actualLatitude = intentRecu.getDoubleExtra("Latitude");
            actualLongitude = intentRecu.getDoubleExtra("Longitude");
        }
    }
}
