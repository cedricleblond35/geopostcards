package com.example.cleblond2016.geopostcards2.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
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

import com.example.cleblond2016.geopostcards2.BO.PostCard;
import com.example.cleblond2016.geopostcards2.R;
import com.example.cleblond2016.geopostcards2.services.PostCardService;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {


    private String TAG = "MainActivity";

    //géolocalisation
    LocationManager lm;
    public static final int REQUEST_CODE = 1234;
    public static final int DEFAULT_ID_USER = 0;
    public static final String EXTRA_ID_USER = "idUser";
    private double latitudeUser = 0.00;
    private double longitudeUser = 0.00;
    private int idUser;

    //carte
    private MapView map;
    private GeoPoint userPoint;
    private IMapController mapController;
    private Marker startMarker;


    //cards
    private List<PostCard> carts;
    private PostCardService p;
    private Location location = null;
    private Polyline lineCircle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //barre de menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //intent
        Intent intentRecu = getIntent();
        idUser = intentRecu.getIntExtra(EXTRA_ID_USER, DEFAULT_ID_USER);
        Log.i(TAG, "----------------------------------- idUser : "+idUser);

        //Bouton rose
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePostCardActivity.class);
                intent.putExtra(CreatePostCardActivity.EXTRA_LATITUDE, latitudeUser);
                intent.putExtra(CreatePostCardActivity.EXTRA_LONGITUDE, longitudeUser);
                intent.putExtra(EXTRA_ID_USER, idUser);
                startActivity(intent);
            }
        });

        //Chargement des carte postal
        p = PostCardService.getInstance();
        //carts = p.selectAllPostCardswhithLimit(this);

        //Démarrer gps
        init();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPoint.setLatitude(latitudeUser);
        userPoint.setLongitude(longitudeUser);
        mapController.setCenter(userPoint);


        //Chargement des carte postal
        p = PostCardService.getInstance();
        carts = p.selectAllPostCardswhithLimit(this, latitudeUser, longitudeUser, 1.00);
        if(this.location != null) printCarts(this.location);



    }

    @Override
    protected void onPause() {
        super.onPause();

        lm.removeUpdates(this);


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /***************************
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
       printCarts(location);
       this.location = location;
        userPoint.setLatitude(latitudeUser);
        userPoint.setLongitude(longitudeUser);
        mapController.setCenter(userPoint);

       //supprimer le cercle existant
       if(lineCircle != null)
           map.getOverlayManager().remove(lineCircle);

        //ajout des points du nouveau cercle
        List<GeoPoint> geoPoints = getGeodeticPoints();
        lineCircle = new Polyline();   //see note below!
        lineCircle.setPoints(geoPoints);

        map.getOverlayManager().add(lineCircle);

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
        //String msg = String.format(getResources().getString(R.string.provider_enabled), provider);
        if(provider.equals("network")) {
            init();
            Toast.makeText(this, "GPS activivé, localisation en cours ... ", Toast.LENGTH_SHORT).show();
        }


    }

    /**********************
     * Cette méthode est appelée quand une source de localisation est désactivée(GPS, 3G..etc).
     * L’argument est le nom de la source désactivée. Vous pouvez par exemple vous désabonner à la
     * mise à jour de localisation via cette source.
     * @param
     */
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "GPS désactivé, merci de l'activer", Toast.LENGTH_SHORT).show();
    }

    private void printCarts(Location location) {
        latitudeUser = location.getLatitude();
        longitudeUser = location.getLongitude();

        carts = p.selectAllPostCardswhithLimit(this, latitudeUser, longitudeUser, 1.00);

        Log.i(TAG, "latitude: " + latitudeUser + " long : " + longitudeUser );

        //carte
        userPoint.setLatitude(latitudeUser);
        userPoint.setLongitude(longitudeUser);
        mapController.setCenter(userPoint);

        //marker
        startMarker.setPosition(userPoint);
        map.getOverlays().add(startMarker);

        //Afficher l'ensemble des cartes
        for(PostCard postCard : carts)
        {
            GeoPoint pointCart = new GeoPoint(postCard.getLatitude(), postCard.getLongitude());

            Marker markerCart = new Marker(map);
            markerCart.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            markerCart.setIcon(getResources().getDrawable(R.mipmap.ic_local));
            markerCart.setPosition(pointCart);
            markerCart.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    Log.i(TAG, "mapView :"+ mapView);
                    Log.i(TAG, "marker :"+ marker);
                    Intent intent = new Intent(MainActivity.this, DetailPostCardActivity.class);
                    intent.putExtra(CreatePostCardActivity.EXTRA_LATITUDE, latitudeUser);
                    intent.putExtra(CreatePostCardActivity.EXTRA_LONGITUDE, longitudeUser);
                    startActivity(intent);

                    return false;
                }
            });

            map.getOverlays().add(markerCart);
        }
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

    /**********************
     *
     * Affichage de la carte & affichage de l'utilisateur
     *
     */
    private void init() {
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                )
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
                    0,
                    0, this);

            //carte
            map = (MapView) findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);
            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
            userPoint = new GeoPoint(latitudeUser, longitudeUser);
            mapController = map.getController();
            mapController.setZoom(14);
            mapController.setCenter(userPoint);

            // Marker de la personne
            startMarker = new Marker(map);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setIcon(getResources().getDrawable(R.mipmap.ic_personn_round));
            map.getOverlays().add(startMarker);



        }
    }


    /**
     * Localisation des points du cercle
     * @return List<GeoPoint>
     */
    public List<GeoPoint> getGeodeticPoints() {
        final double radius = 1000;
        ArrayList<GeoPoint> circlePoints = new ArrayList<GeoPoint>();
        for (float f = 0; f < 360; f += 1){
            circlePoints.add(new GeoPoint(latitudeUser , longitudeUser ).destinationPoint(radius, f));
        }
        return circlePoints;

    }

}
