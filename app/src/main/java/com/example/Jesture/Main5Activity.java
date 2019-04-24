package com.example.Jesture;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//standard charade game
public class Main5Activity extends AppCompatActivity implements SensorEventListener {

    TextView xaccel;
    TextView feed;
    TextView question;
    String [] array1 = {"Skiing","Swimming","Eating Spaghetti","Baseball", "Ballet", "Typewriter", "Trampoline"};
    int i=0;
    boolean next = true;

    //Sensor manager
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        xaccel = (TextView) findViewById(R.id.xaccel);
        feed = (TextView) findViewById(R.id.feed);
        question = (TextView) findViewById(R.id.question);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
            return;
        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        //double angle = (Math.atan2(y, Math.sqrt(x*x+z*z))/ (Math.PI / 180));
        StringBuilder ab = new StringBuilder().append("x:").append(x).append("\n");
        //ab.append("y:").append(y).append("\n");
        //ab.append("z:").append(z).append("\n");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                question.setText(array1[i]);
            }
        }, 2000 );//time in milisecond

        if(z > 6 && !next){
            feed.setText("Correct");
            if(i < array1.length-1){
                next = true;
                i++;

            }
        }

        else if(z < -6 && !next){
            feed.setText("Pass");
            if(i < array1.length-1){
                next = true;
                i++;

            }
        }
        else if(z > -6 && z < 6){
            next = false;
            feed.setText("???");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
