package com.example.Jesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

//shows which player is up next
public class Main6Activity extends AppCompatActivity {

    TextView nextplayer;
    Button startbtn;
    static int count;
    ArrayList<String> nameArray = null;


    @Override
    protected void onResume() {
        super.onResume();
        //receive names and number of players
        Bundle b = getIntent().getExtras();



        if(nameArray == null) {
            nameArray = getIntent().getExtras().getStringArrayList("list");
        }

        String flag = getIntent().getStringExtra("flag");


        //if flag is zero, then first time opening activity
        if(flag == null){
            count = -1;
        }
        //else coming from later activity and want to incremement current count
        count++;

        //need to reset to first player if decide to play more rounds than number of people playing
        if (count == nameArray.size()) {
            count = 0;
        }

        //display which player is up next
        nextplayer = (TextView) findViewById(R.id.name1);
        nextplayer.setText(nameArray.get(count));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        //start game
        startbtn = (Button) findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            String gameflag = getIntent().getStringExtra("gameflag");
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Main6Activity.this, Main4Activity.class);
               intent.putStringArrayListExtra("list", nameArray);
               intent.putExtra("gameflag", gameflag);
               startActivity(intent);

                }
        });


    }


}