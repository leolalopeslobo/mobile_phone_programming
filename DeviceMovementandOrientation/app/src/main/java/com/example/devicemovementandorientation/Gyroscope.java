package com.example.devicemovementandorientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
public class Gyroscope {
    public interface Listener{
        void onRotation(float rx, float ry, float rz);
    }
    private Listener listener; //adding the instance of the listener itself
    //method to set the listener
    public void setListener(Listener l){
        listener = l;
    }
    //to implement this class, 3 objects are required
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    //Construct that will take Context as an argument
    Gyroscope (Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);//instance of
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);//creating the sensor
        if (gyroscopeSensor == null) {
            Toast.makeText(context, "No gyroscope present", Toast.LENGTH_SHORT).show();
        }
        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener != null) {
                    listener.onRotation(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
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
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    //method to unregister from sensor notifications
    public void unregister(){
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
}