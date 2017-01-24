package com.hakber.dietgo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
public class walkActivity extends AppCompatActivity implements OnMapReadyCallback {
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    private GoogleMap mMap;
    boolean firstLocation;
    protected static final String TAG = "location-updates-sample";
    public static Boolean mRequestingLocationUpdates;
    private boolean mapReady=false;


    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public Handler handler = null;
    public static Runnable runnable = null;
    protected TextView distanceTextView;
    protected TextView durationTextView;
    Chronometer simpleChronometer;

    public walkActivity(Context context) {
        this.context = context;
    }
    public walkActivity() {

    }
    public static Boolean getmRequestingLocationUpdates() {
        return mRequestingLocationUpdates;
    }

    protected String distanceLabel;


    public static final String LOCAL_NOTIFICATION_STRING = "my-local-notification";
    private static walkActivity INSTANCE;
    private Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        simpleChronometer=null;



        startService(new Intent(this, walkActivityService.class));

        firstLocation=false;
        mRequestingLocationUpdates=false;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        distanceLabel = getResources().getString(R.string.distance_label);
        // mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);
        distanceTextView = (TextView) findViewById(R.id.distance_text);
        //durationTextView=(TextView)findViewById(R.id.duration_text);
        updateUI();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {


                if(mapReady) {
                    setGoogleMap();
                }
                updateUI();
                if(mRequestingLocationUpdates) {
                    if(simpleChronometer==null) {
                        simpleChronometer= (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
                        resetChoronometer();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putFloat("distance_prefs", 0.0f);
                        editor.commit();
                    }

                    simpleChronometer.start(); // start a chronometer
                }
                else {
                    if (simpleChronometer != null) {
                        simpleChronometer.stop(); // stop a chronometer
                        simpleChronometer=null;
                    }
                }
                handler.postDelayed(runnable, 1000);
            }
        };

        handler.postDelayed(runnable,100);


    }
    public void resetChoronometer(){

        simpleChronometer.setBase(SystemClock.elapsedRealtime()); // set base time for a chronometer

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mapReady=true;
        firstLocation=true;
        setGoogleMap();


    }

    public void updateUI() {


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        float total_distance = prefs.getFloat("distance_prefs", (float) 0.0);
        distanceTextView.setText(String.format("%.2f km",
                total_distance / 1000));

        // distanceTextView.setText(intent.getExtras().getString("extra"););

    }
    public static walkActivity getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new walkActivity(context);
        }
        return INSTANCE;
    }
    public void setGoogleMap(){


        // Add a marker in Sydney and move the camera
        if(walkActivityService.getlongValue()!=0.0 && walkActivityService.gettempLatValue()!=0.0) {
            if(firstLocation) {
                LatLng loc = new LatLng(walkActivityService.getlatValue(), walkActivityService.getlongValue());

                mMap.addMarker(new MarkerOptions().position(loc).title("Buradan Başlandı"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                mMap.setMyLocationEnabled(true);

                firstLocation=false;

            }
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(walkActivityService.getlatValue(), walkActivityService.getlongValue()), new LatLng(walkActivityService.gettempLatValue(),
                            walkActivityService.getLongValue()))
                    .width(5)
                    .color(Color.BLUE));
        }
    }
    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), walkActivityService.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), walkActivityService.class));
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
        }
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates. Does nothing if
     * updates were not previously requested.
     */
    public void stopUpdatesButtonHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            //stopLocationUpdates();
        }
    }
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    @Override
    protected void onStop() {


        super.onStop();
     /*   if(!getmRequestingLocationUpdates())
    }*/


    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
        stopService();
        mRequestingLocationUpdates=false;

    }
}

