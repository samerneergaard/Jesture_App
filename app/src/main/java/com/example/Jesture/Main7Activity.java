package com.example.Jesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main7Activity extends AppCompatActivity {
//Show score - next player - new game

    Button againbtn;
    Button stopbtn;
    TextView correcttxt;
    TextView passtxt;
   //can't do whos next variable - would have to carry it throughout the game, easier to increment at Main6Activity
    // public static int whosnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        //final ArrayList<String> nameArray = getIntent().getExtras().getStringArrayList("list");
        //whosnext = 0;

        //receive correct and pass score
        int c = 5;
        int p = 5;
        //int c = getIntent().getIntExtra("correct", 0);
        //int p = getIntent().getIntExtra("pass", 0);

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
                //whosnext++;
                Intent intent = new Intent(Main7Activity.this, Main6Activity.class);
                //intent.putStringArrayListExtra("list", nameArray);
                //intent.putExtra("next", whosnext);
                startActivity(intent);

            }
        });
        //send back to choose game mode
        stopbtn = (Button) findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whosnext++;
                Intent intent = new Intent(Main7Activity.this, Main2Activity.class);
                //intent.putExtra("next", whosnext);
                startActivity(intent);
            }
        });


    }
}
