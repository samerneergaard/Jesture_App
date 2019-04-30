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

//game mode with timer
public class Main4Activity extends AppCompatActivity implements SensorEventListener {

    private static final long Game_Time = 30000; //(milliseconds)
    private TextView mTextViewCountDown;
    private Button mButtonEscape;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeft = Game_Time;
    public static String gameflag;
    private static String [] array1;


    TextView xaccel;
    TextView feed;
    TextView question;


    //next item
    Random rand = new Random();
    int i;
    //array of already used indexes
    ArrayList<Integer> previous = new ArrayList<>();

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


        xaccel = (TextView) findViewById(R.id.xaccel);
        feed = (TextView) findViewById(R.id.feed);
        question = (TextView) findViewById(R.id.question);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        StartTimer();

        String gameflag = getIntent().getStringExtra("gameflag");

            if (gameflag.equals("celeb")) {
                array1 = new String[]{"Donald Trump", "Marilyn Monroe", "Ellen Degeneres", "Beyonce", "Morgan Freeman", "Jimmy Fallon", "Michael Jackson", "Justin Bieber", "Mac Miller", "Kim Kardashian", "Miley Cyrus", "Emma Watson", "Jennifer Anniston", "Elvis Presley", "Barrack Obama", "Steve Jobs", "Elon Musk", "George Clooney", "Mark Zuckerberg", "Princess Diana", "Kanye West", "Britney Spears", "Bradley Cooper", "Michelle Obama", "Prof Stringhini"};
            }
            else if (gameflag.equals("actions")) {
                array1 = new String[]{"Playing Hopscotch", "Calling a Taxi", "Building a Snowman", "Building a Campfire", "Skiing", "Swimming", "Eating Spaghetti", "Baseball", "Ballet", "Flipping Pancakes", "Jumping on a Trampoline", "Ice Skating", "Yo-yo", "Fishing"};
            }
            else if (gameflag.equals("accents")) {
                array1 = new String[]{"Italian", "British", "Spanish", "Australian", "Japanese", "French", "Old English", "German", "Russian", "Irish", "Indian", "Boston", "Chicago", "New York", "Baltimore", "Canadian", "Southern"};
            }


       i = rand.nextInt(array1.length);


        //leave game before timer is up
        mButtonEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //escape to new activity
                String gameflag = getIntent().getStringExtra("gameflag");
                ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
                Intent intent = new Intent(Main4Activity.this, Main7Activity.class);
                //send num correct and num pass
                intent.putExtra("correct", c);
                intent.putExtra("pass", p);
                intent.putExtra("gameflag", gameflag);
                intent.putStringArrayListExtra("list", nameArray);
                startActivity(intent);
            }
        });

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
                String gameflag = getIntent().getStringExtra("gameflag");
                ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
                //escape to new activity
                Intent intent = new Intent(Main4Activity.this, Main7Activity.class);
                //send num correct/pass and which game you are on
                intent.putExtra("correct", c);
                intent.putExtra("pass", p);
                intent.putExtra("gameflag", gameflag);
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
        //first term presented
        if(previous.size() == 0) {
            previous.add(i);
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
        }, 2000 );//time in millisecond

        if(z > 6 && !next){
            feed.setText("Pass");
            if(i <= array1.length){
                next = true;
                p++;
                while (previous.contains(i)) {
                    i = rand.nextInt(array1.length);
                }
                previous.add(i);
            }
        }
        //!next in
        else if(z < -6 && !next){
            feed.setText("Correct");
            //if random index is less than available indexes (always should be), enter
            //next = true means won't enter this elseif again until orientation resets
            if(i <= array1.length){
                next = true;
                c++;
                //won't contain i the first time, index has already been called, then create new random index
                while (previous.contains(i)) {
                    i = rand.nextInt(array1.length);
                }
                //add index to array so doesn't repeat
                previous.add(i);
            }
        }
        else if(z > -6 && z < 6){
            //orientation reset
            next = false;
            feed.setText("???");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

