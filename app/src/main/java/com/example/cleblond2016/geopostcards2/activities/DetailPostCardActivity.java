package com.example.cleblond2016.geopostcards2.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleblond2016.geopostcards2.R;

public class DetailPostCardActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post_card);

    }
}
