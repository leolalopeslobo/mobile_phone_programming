package com.example.devicemovementandorientation;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    TextView text;
    Random random = new Random(); //creating a random object called random
    //adding the instances of the accelerometer and gyroscope
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.random_number); //accessing the text element
        //instantiating the accelerometer and gyroscope
        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                if (tz > 0.3f) { //anticlockwise
                    change_number();
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(144,238,144));
                } else if (tz < -0.3f) { //clockwise
                    change_number();
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 204, 203));
                }
            }
        });
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (rz > 0.8f) { //anticlockwise
                    change_number();
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(255,255,153));
                } else if (rz < -0.8f) { //clockwise
                    change_number();
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(173, 216, 230));
                }
            }
        });
    }
    private void change_number() {
        //generating a random number and saving inside integer
        int rad_val = random.nextInt(7 - 1) + 1;
        text.setText(String.valueOf(rad_val));
    }
    //to register a listener for sensor manager for the gyroscope sensor
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Resumed!", Toast.LENGTH_SHORT).show();
        accelerometer.register();
        gyroscope.register();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
        accelerometer.unregister();
        gyroscope.unregister();
    }
}


