package com.example.Jesture;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    //ArrayList<String> arrayList;
    //ArrayAdapter<String> arrayAdapter;

    Button playnow;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playnow = (Button) findViewById(R.id.playnow);
        //can change button images for types of games
        models = new ArrayList<>();
        models.add(new Model(R.drawable.defaultgame, "Standard Game", "Standard charades where friends guess the celebrity"));
        models.add(new Model(R.drawable.accentgame, "Accents", "Try and guess the accent!"));
        models.add(new Model(R.drawable.customgame, "Custom Game", "Create your own questions"));
        models.add(new Model(R.drawable.newgame, "Names Game", "Use team members names and act them out in the given situation"));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount() -1) && position < (colors.length -1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position+1]));
                }
                else {
                    viewPager.setBackgroundColor(colors[colors.length -1]);
                }

                pos = position;

                playnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pos ==0 || pos == 1) {
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        }
                        else if(pos == 3){
                            startActivity(new Intent(Main2Activity.this, Main3Activity.class));
                        }
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //ConstraintLayout constraintLayout = findViewById(R.id.layout);
        //AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        //animationDrawable.setEnterFadeDuration(2000);
        //animationDrawable.setExitFadeDuration(4000);
        //animationDrawable.start();
}}
