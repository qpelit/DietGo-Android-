package com.hakber.dietgo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Restoranbul extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoranbul);

        //startService(new Intent(this, testService.class));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng loc = new LatLng(41.031483, 28.976315);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        mMap.addMarker(new MarkerOptions().position(new LatLng(41.043476, 29.004596)).title("Balkan Lokantası"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.992014, 28.715094)).title("Tavuk Dünyası"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.976664, 28.720525)).title("Sanat Evim Cafe"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.991170, 28.714568)).title("WestMix"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.010567, 28.658157)).title("LunchBox"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.009644, 28.652631)).title("Van Kahvaltı Sofrası"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.016352, 28.653152)).title("Erse Mantı Evi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.044761, 29.016583)).title("Gazebo Lounge Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.042487, 29.001461)).title("Vogue Restaurant & Bar"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.037202, 28.993448)).title("Topaz Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.036601, 28.982702)).title("Zencefil Vegan Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.029751, 28.975263)).title("Markiz Yemek Kulübü"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.031483, 28.976315)).title("Midpoint"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.033668, 28.977055)).title("Hard Rock Cafe Istanbul"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.032972, 28.981218)).title("No 19 Dining"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.027287, 28.974647)).title("Sinas Galata"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.028177, 28.972733)).title("Big Chefs"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.027557, 28.973607)).title("BAK-KAL"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.005817, 28.980672)).title("Babylonia Garden"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.002952, 28.975508)).title("Mostra Fish Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.079196, 29.031258)).title("China Stix & Sushi Etiler"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.080465, 29.033469)).title("Nusr-Et Steakhouse"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.079999, 29.033306)).title("P.f. Chang's"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.047491, 28.994026)).title("La Petite Maison"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.986024, 28.784537)).title("Kınacı İşkembe Salonu"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.962876, 28.799572)).title("Kaşıbeyaz Akvaryum"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.960088, 28.809000)).title("Florya Sosyal Tesisi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.005015, 28.975364)).title("Mostra Fish Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.010150, 28.657362)).title("Hasırlı Konya Mutfak˝"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.023151, 28.626948)).title("Summit Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.014880, 28.562219)).title("Balık Osman"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.971676, 29.060652)).title("Vino Steak House"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.965464, 29.071735)).title("Kirpi Cafe"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.966858, 29.067662)).title("Happy Moon's"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.976586, 29.047860)).title("Palaimon Balık Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.979562, 29.044694)).title("Moshonis Balık Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.983154, 28.728678)).title("BG Ev Yemekleri"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.982961, 28.729213)).title("Tostçu Enes"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.971602, 28.722790)).title("Tropicano"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.972278, 28.726985)).title("Evita Mangalba˛˝"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.972324, 28.723192)).title("Amindos Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.978370, 28.720126)).title("Fabrika Cafe & Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.977725, 28.719628)).title("Lara Cafe & Restaurant"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.011496, 28.713037)).title("Firuzköy Sosyal Tesisleri"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.008126, 28.716982)).title("Evita Firuzköy"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.012553, 28.703923)).title("Lord's Pub"));


    }
   /* public void setMap(){
        // Add a marker in Sydney and move the camera
        if(testService.getlongValue()!=0.0 && testService.gettempLatValue()!=0.0) {

            LatLng loc = new LatLng(testService.getlatValue(), testService.getlongValue());
            mMap.addMarker(new MarkerOptions().position(loc).title("Burdasin"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }*/
    public void startService(View view) {
        startService(new Intent(getBaseContext(), testService.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), testService.class));
    }

}

