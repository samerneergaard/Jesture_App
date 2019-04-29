package com.example.Jesture;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;
import java.util.ArrayList;


public class Main4Activity extends AppCompatActivity implements SensorEventListener {

    private static final long Game_Time = 30000; //(milliseconds)
    private TextView mTextViewCountDown;
    private Button mButtonEscape;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeft = Game_Time;

    TextView xaccel;
    TextView feed;
    TextView question;
    String [] array1 = {"Donald Trump","Britney Spears","Bradley Cooper","Michelle Obama", "Prof Stringhini"};

    //next item
    Random rand = new Random();
    int i= rand.nextInt(array1.length);

    //number correct
    public static int c = 0;
    //number pass
    public static int p = 0;
    boolean next = true;

    //Sensor manager
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonEscape = findViewById(R.id.button_exit);

        mButtonEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //escape to new activity
                ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
                Intent intent = new Intent(Main4Activity.this, Main7Activity.class);
                //send num correct and num pass
                intent.putExtra("correct", c);
                intent.putExtra("pass", p);
                intent.putStringArrayListExtra("list", nameArray);
                startActivity(intent);
            }
        });

        StartTimer();

        xaccel = (TextView) findViewById(R.id.xaccel);
        feed = (TextView) findViewById(R.id.feed);
        question = (TextView) findViewById(R.id.question);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void StartTimer() {
           //reset p and c each time the timer starts
            p = 0;
            c = 0;
        mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                //escape to new activity
                ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
                Intent intent = new Intent(Main4Activity.this, Main7Activity.class);
                //send num correct and num pass
                intent.putExtra("correct", c);
                intent.putExtra("pass", p);
                intent.putStringArrayListExtra("list", nameArray);
                startActivity(intent);
            }
        }.start();
        mTimerRunning = true;
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeft / 1000) / 60;
        int seconds = (int) (mTimeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
            return;
        }
        ArrayList<Integer> previous = new ArrayList<>();
        previous.add(i);

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
            c++;
            if(i < array1.length-1){
                next = true;
                while (previous.contains(i)) {
                    i = rand.nextInt(array1.length);
                }
                previous.add(i);
            }
        }

        else if(z < -6 && !next){
            feed.setText("Pass");
            p++;
            if(i < array1.length-1){
                next = true;
                while (previous.contains(i)) {
                    i = rand.nextInt(array1.length);
                }
                previous.add(i);
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
//WHEN ADD NEXT ACTIVITY, NEED TO SEND P AND C TO SHOW SCORE, use keys: "correct" and "pass"
