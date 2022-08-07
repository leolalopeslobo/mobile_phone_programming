package com.example.locationbasedservices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;

    Button getlocationBtn;
    TextView showLocationText;

    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add Permission
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocationText = findViewById(R.id.show_location);
        getlocationBtn = findViewById(R.id.getLocation);


        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is enabled or not
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //function to enable GPS
                    OnGPS();
                }
                else {
                    //GPS is already ON then
                    getLocation();
                }
            }
        });
    }

    private void getLocation() {
        //Check permission again
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            Location LocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //  Location LocationNetworks = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //  Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGPS != null) {
                double lat = LocationGPS.getLatitude();
                double lon = LocationGPS.getLongitude();

                System.out.println("Latitude: "+lat);
                System.out.println("Longitude: "+lon);

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);

                showLocationText.setText("Your Location:"+"\n"+"Latitude = "+latitude+"\n"+"Longitude = "+longitude);


                if (lat <= 15.595403684262525 && lat <= 15.595127894116695 && lat >= 15.594078806647204 && lat >= 15.594383126048028) {
                    if (lon >=73.79409938628291 && lon <=73.79627213693949 && lon <=73.79628897996783 && lon >=73.79405980958552 ){
                        Toast.makeText(this, "You Are In", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Latitude is INCORRECT", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "You Are Out", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

