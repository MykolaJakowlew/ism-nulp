package com.nulp.ism.turismapplication;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        int time = 10; // який мінімальний час має пройти, що б дані по геолокації оновилися
        int offset = 10;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * time, offset, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * time, offset, locationListener);

    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            //тут писати код, який виконується, коли обєкт змістився на визначену дистанцію
        }

        @Override
        public void onProviderDisabled(String provider) {
            // код який виконується при виключенні провайдера (GPS або WiFI)
        }

        @Override
        public void onProviderEnabled(String provider) {
            // код який виконується при включенні провайдера (GPS або WiFI)
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };
    private void showLocation(Location location) {
        if (location == null)
            return;
        String str_1,str_2;
        //str_1 - в ній зберігається  текстовому форматі широта і довгота і час за GPS
        //str_2 - в ній зберігається  текстовому форматі широта і довгота і час за WiFi
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            str_1 = formatLocation(location);
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            str_2 = formatLocation(location);
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
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
}
