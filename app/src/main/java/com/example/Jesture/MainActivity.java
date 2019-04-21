package com.example.Jesture;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button sub;
    Animation frombottom, fromtop, fadein;
    ImageView logojest;
    TextView textdescription;
    MediaPlayer themesong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themesong=MediaPlayer.create(MainActivity.this,R.raw.themesong);
        themesong.start();


        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        sub = (Button) findViewById(R.id.getbusyBtn);
        logojest = (ImageView) findViewById(R.id.logojest);
        textdescription = (TextView) findViewById(R.id.textdescription);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
        sub.setAnimation(frombottom);
        logojest.setAnimation(fromtop);
        textdescription.setAnimation(fadein);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

    }
}
