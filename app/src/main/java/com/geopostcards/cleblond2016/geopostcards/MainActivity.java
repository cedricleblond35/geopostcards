package com.geopostcards.cleblond2016.geopostcards;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager lm;
    private String TAG = "MainActivity";
    public static final int REQUEST_CODE = 1234;
    private double latitude;
    private double longitude;
    private float accuracy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (checkAndRequestMultiplePermissions()) {
            init();
        } else {
            Toast.makeText(this, "Pour pouvoir utiliser l'appli, vous devez autoriser les permissions", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    /**
     *
     * @return
     */
    private boolean checkAndRequestMultiplePermissions() {
        String[] permissionsNeeeded = new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String p : permissionsNeeeded) {
            int check = ContextCompat.checkSelfPermission(this, p);
            if(check != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if(!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_CODE);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                }
                break;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /************************************************************
     * Location
     *
     */

    /**
     * Cette méthode est appelée quand une source de localisation est activée (GPS, 3G..etc).
     * L’argument représente le nom de la source activée. Vous pouvez par exemple vous abonner à la
     * mise à jour de localisation via cette source
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        accuracy = location.getAccuracy(); //Obtenez la précision horizontale estimée de cet emplacement, radial, en mètres.


        Log.i(TAG, "latitude: "+ latitude + " long : " + longitude + " accuracy :" + accuracy);
    }

    /**
     * * Appeler quand le status d’une source change.
     * Il existe 3 statuts (OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE, AVAILABLE).
     * @param provider : the name of the location provider associated with this update.
     * @param status : int: LocationProvider.OUT_OF_SERVICE if the provider is out of service, and this is not expected to change in the near future; LocationProvider.TEMPORARILY_UNAVAILABLE if the provider is temporarily unavailable but is expected to be available shortly; and LocationProvider.AVAILABLE if the provider is currently available.
     * @param extras : Bundle: an optional Bundle which will contain provider specific status variables.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                Log.v(TAG, "Status Changed: Out of Service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                Log.v(TAG, "Status Changed: Temporarily Unavailable");
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                Log.v(TAG, "Status Changed: Available");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    /**
     * Cette méthode est appelée quand une source de localisation est désactivée(GPS, 3G..etc).
     * L’argument est le nom de la source désactivée. Vous pouvez par exemple vous désabonner à la
     * mise à jour de localisation via cette source.
     * @param s
     */
    @Override
    public void onProviderDisabled(String s) {

    }
}
