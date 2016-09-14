package com.example.hwhong.usergps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final String GPS = LocationManager.GPS_PROVIDER;
    public final String NETWORK = LocationManager.NETWORK_PROVIDER;

    private Button button;
    private TextView textView;

    private LocationManager lm;
    private LocationListener ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.info);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new MyLocationListener();
        turnOnGPS(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOnGPS(getApplicationContext());
            }
        });
    }

    public void turnOnGPS(Context context) {

        boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Toast.makeText(context, "GPS : " + enabled + ", Network : " + network, Toast.LENGTH_SHORT).show();

        //check if both are present

        if (enabled | network)
            return;
        else {
            Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsIntent);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            textView.setText("Your Location Information are Listed Below: " + "\n" +
                    "Latitude: " + location.getLatitude() + "\n" +
                    "Longitude: " + location.getLongitude() + "\n" +
                    "Accuracy: " + location.getAccuracy() + "\n" +
                    "Altitude: " + location.getAltitude() + "\n" +
                    "Time: " + location.getTime() + "\n" +
                    "Speed: " + location.getSpeed() + "\n" +
                    "Bearing: " + location.getBearing());
            setTitle("Location Information Displayed");

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

            switch (i) {
                case LocationProvider.AVAILABLE:
                    setTitle("AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    setTitle("OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    setTitle("TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }


    @Override
    protected void onResume() {

        if (lm == null) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            ll = new MyLocationListener();
        }


        lm.requestLocationUpdates(GPS, 0, 0, ll);
        lm.requestLocationUpdates(NETWORK, 0, 0, ll);

        setTitle("onResume...");
        super.onResume();
    }


    @Override
    protected void onPause() {
        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.removeUpdates(ll);
            lm = null;
        }
        setTitle("onPause...");
        super.onPause();
    }
}
