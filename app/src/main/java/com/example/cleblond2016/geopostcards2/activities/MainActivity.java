package com.example.cleblond2016.geopostcards2.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cleblond2016.geopostcards2.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity implements LocationListener {


    private String TAG = "MainActivity";

    //géolocalisation
    LocationManager lm;
    public static final int REQUEST_CODE = 1234;
    private double latitude;
    private double longitude;

    //carte
    private MapView map;
    private GeoPoint startPoint;
    private IMapController mapController;
    private Marker startMarker;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 3 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Bouton rose
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePostCardActivity.class);
                intent.putExtra(CreatePostCardActivity.EXTRA_LATITUDE, latitude);
                intent.putExtra(CreatePostCardActivity.EXTRA_LONGITUDE, longitude);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        //Démarrer gps
        init();

    }

    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(this);

        startPoint.setLatitude(latitude);
        startPoint.setLongitude(longitude);
        mapController.setCenter(startPoint);
    }

    /***********
     *
     * Menu
     * @param menu
     * @return
     */
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
        //accuracy = location.getAccuracy(); //Obtenez la précision horizontale estimée de cet emplacement, radial, en mètres.


        Log.i(TAG, "latitude: " + latitude + " long : " + longitude );

        startPoint.setLatitude(latitude);
        startPoint.setLongitude(longitude);
        mapController.setCenter(startPoint);

        startMarker.setPosition(startPoint);
        map.getOverlays().add(startMarker);
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
        Toast.makeText(getApplicationContext(), "GPS", Toast.LENGTH_LONG).show();
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
        //String msg = String.format(getResources().getString(R.string.provider_disabled), provider, newStatus);
        Toast.makeText(this, newStatus, Toast.LENGTH_SHORT).show();

    }


    /**
     * Cette méthode est appelée quand une source de localisation est activée (GPS, 3G..etc).
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_enabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Cette méthode est appelée quand une source de localisation est désactivée(GPS, 3G..etc).
     * L’argument est le nom de la source désactivée. Vous pouvez par exemple vous désabonner à la
     * mise à jour de localisation via cette source.
     * @param
     */
    @Override
    public void onProviderDisabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /*********************
     *
     * Permission
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                }
                break;
        }
    }

    private void init() {
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        } else {
            /**
             * requestLocationUpdates:
             * -----------------------
             * String: le nom du fournisseur avec lequel s'inscrire
             * long: intervalle de temps minimum entre les mises à jour d'emplacement, en millisecondes
             * float: distance minimale entre les mises à jour de l'emplacement, en mètres
             * LocationListener: un LocationListener dont la méthode LocationListener.onLocationChanged (Location) sera appelée pour chaque mise à jour d'emplacement
             */
            lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            //carte
            map = (MapView) findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);

            Log.i(TAG, "---------------------------------------------------");
            Log.i(TAG, "latitude :"+latitude + " longitude:"+longitude);
            Log.i(TAG, "---------------------------------------------------");
            startPoint = new GeoPoint(latitude, longitude);
            mapController = map.getController();
            mapController.setZoom(14);
            mapController.setCenter(startPoint);

            startMarker = new Marker(map);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setIcon(getResources().getDrawable(R.mipmap.ic_local));
        }


    }


}
