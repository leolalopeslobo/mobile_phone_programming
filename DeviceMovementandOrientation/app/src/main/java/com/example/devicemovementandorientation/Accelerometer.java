package com.example.devicemovementandorientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
public class Accelerometer {
    /* Implementing a design pattern called the observer to decouple the sensor notifications from the main activity
    Creating an interface called listener, having one method and 3 arguments, which are the translation along the x, y and z
   axes
    */
    public interface Listener{
        void onTranslation(float tx, float ty, float tz);
    }
    private Listener listener; //adding the instance of the listener itself
    //method to set the listener
    public void setListener(Listener l){
        listener = l;
    }
    //to implement this class, 3 objects are required
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerEventListener;
    //Construct that will take Context as an argument
    Accelerometer (Context context){
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);//instance of
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//creating the sensor
        if (accelerometerSensor == null) {
            Toast.makeText(context, "No accelerometer present", Toast.LENGTH_SHORT).show();
        }
        accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener != null) {
                    listener.onTranslation(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }
    //creating a method
    //to register for sensor notifications
    public void register(){
        sensorManager.registerListener(accelerometerEventListener, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    //method to unregister from sensor notifications
    public void unregister(){
        sensorManager.unregisterListener(accelerometerEventListener);
    }
}

