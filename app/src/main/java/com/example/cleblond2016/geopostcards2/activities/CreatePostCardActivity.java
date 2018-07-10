package com.example.cleblond2016.geopostcards2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.cleblond2016.geopostcards2.R;

public class CreatePostCardActivity extends AppCompatActivity {

    public static final String EXTRA_LATITUDE = "lat";
    public static final String EXTRA_LONGITUDE = "long";
    private static final String TAG = "CreatePostCardActivity" ;

    private EditText edtLatitude;
    private EditText edtLongitude;
    private EditText edtTitle;
    private EditText edtDescriptionImage;
    private EditText edtDescriptionSon;
    private EditText edtDescriptionVideo;

    private double actualLatitude;
    private double actualLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_card);

        edtLatitude = findViewById(R.id.create_post_card_latitude);
        edtLongitude = findViewById(R.id.create_post_card_longitude);
        edtTitle = findViewById(R.id.create_post_card_title);
        edtDescriptionImage = findViewById(R.id.create_post_card_description_image);
        edtDescriptionSon = findViewById(R.id.create_post_card_description_son);
        edtDescriptionVideo = findViewById(R.id.create_post_card_description_video);

        Intent intentRecu = getIntent();
        if(intentRecu != null) {
            double defaultValue = 0.00;
            actualLatitude = intentRecu.getDoubleExtra(EXTRA_LATITUDE, defaultValue);
            actualLongitude = intentRecu.getDoubleExtra(EXTRA_LONGITUDE, defaultValue);
            Log.i(TAG, "actualLatitude :" + actualLatitude);
            Log.i(TAG, "actualLongitude :" + actualLongitude);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(actualLatitude != 0 && actualLongitude != 0) {
            edtLatitude.setText(Double.toString(actualLatitude));
            edtLongitude.setText(Double.toString(actualLongitude));
        }
    }
}
