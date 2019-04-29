package com.example.Jesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Main7Activity extends AppCompatActivity {
//Show score - next player - new game

    Button againbtn;
    Button stopbtn;
    TextView correcttxt;
    TextView passtxt;
    //will know if coming from this page for the purpose of iterating through player names
    public static String flag;
    //keep track of which game you are on
    public static String gameflag;

    @Override
    protected void onResume() {
        super.onResume();
        flag = "Again";


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);



        //receive correct and pass score
        //int c = 5;
        //int p = 5;
        int c = getIntent().getIntExtra("correct", 0);
        int p = getIntent().getIntExtra("pass", 0);

        //display number correct
        correcttxt = (TextView) findViewById(R.id.correcttxt);
        correcttxt.setText(String.valueOf(c));
        //display number passed
        passtxt = (TextView) findViewById(R.id.passtxt);
        passtxt.setText(String.valueOf(p));

        //send back to show who's up next
        againbtn = (Button) findViewById(R.id.againbtn);
        againbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
               String gameflag = getIntent().getStringExtra("gameflag");
                Intent intent = new Intent(Main7Activity.this, Main6Activity.class);
                intent.putStringArrayListExtra("list", nameArray);
                intent.putExtra("flag", flag);
                intent.putExtra("gameflag", gameflag);
                startActivity(intent);

            }
        });
        //send back to choose game mode
        stopbtn = (Button) findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
                Intent intent = new Intent(Main7Activity.this, Main2Activity.class);
                intent.putStringArrayListExtra("list", nameArray);
                startActivity(intent);
            }
        });


    }
}
