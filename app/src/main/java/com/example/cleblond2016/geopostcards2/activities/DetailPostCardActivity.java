package com.example.cleblond2016.geopostcards2.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleblond2016.geopostcards2.R;

public class DetailPostCardActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtMessage;
    private TextView txtDescriptionImage;
    private TextView txtDescriptionSon;
    private TextView txtDescriptionVideo;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private ImageView imageView;

    private String resTitle;
    private String resMessage;
    private String resDescriptionImage;
    private String resDescriptionSon;
    private String resDescriptionVideo;
    private String urlImage;
    private String urlSon;
    private String urlVideo;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post_card);

        txtTitle = findViewById(R.id.detail_post_card_res_title);
        txtMessage = findViewById(R.id.detail_post_card_res_message);
        txtDescriptionImage = findViewById(R.id.detail_post_card_res_description_image);
        txtDescriptionSon = findViewById(R.id.detail_post_card_res_description_son);
        txtDescriptionVideo = findViewById(R.id.detail_post_card_res_description_video);
        imageView = findViewById(R.id.detail_post_card_image_view);
        txtLatitude = findViewById(R.id.detail_post_card_latitude);
        txtLongitude = findViewById(R.id.detail_post_card_longitude);

        Intent intentRecu = getIntent();
        resTitle = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_TITLE);
        resMessage = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_MESSAGE);
        resDescriptionImage = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_DESCRIPTION_IMAGE);
        resDescriptionSon = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_DESCRIPTION_SON);
        resDescriptionVideo = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_DESCRIPTION_VIDEO);
        urlImage = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_URL_IMAGE);
        urlSon = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_URL_SON);
        urlVideo = intentRecu.getStringExtra(MainActivity.EXTRA_POSTCARD_URL_VIDEO);
        latitude = intentRecu.getStringExtra(CreatePostCardActivity.EXTRA_LATITUDE);
        longitude = intentRecu.getStringExtra(CreatePostCardActivity.EXTRA_LONGITUDE);


        try {
            Uri uriImage = Uri.parse(urlImage);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
        }catch (Exception ex){
            Log.e("DetailPostCardActivity","L'image n'a pas pu être chargé");
        }

        txtTitle.setText(resTitle);
        txtMessage.setText(resMessage);
        txtDescriptionImage.setText(resDescriptionImage);
        txtDescriptionSon.setText(resDescriptionSon);
        txtDescriptionVideo.setText(resDescriptionVideo);
        txtLatitude.setText(latitude);
        txtLongitude.setText(longitude);
    }
}
