//Page to input name, then stores those names as arrays
package com.example.Jesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Main3Activity extends AppCompatActivity {


    Button playgame;
    Button addplayer;
    EditText playername;
    ListView listnames;
    //create dynamic array to add names to
   public static final ArrayList<String> nameArray = new ArrayList<String>();
   public static int numPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        addplayer = (Button) findViewById(R.id.addplayer);
        playername = (EditText) findViewById(R.id.playername);
        listnames = (ListView) findViewById(R.id.listnames);


        //Button to add a player
        addplayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // convert text input to string
                String getInput = playername.getText().toString();
                if (getInput != null) {
                    numPlayers++;
                    nameArray.add(getInput);
                    //adapt and add to list view
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main3Activity.this, android.R.layout.simple_expandable_list_item_1, nameArray);
                    listnames.setAdapter(adapter);
                    ((EditText) findViewById(R.id.playername)).setText("");
                }
            }

        });

        //Button to begin the game and send number of players
        playgame = (Button) findViewById(R.id.playgame);
        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main6Activity.class);
                intent.putStringArrayListExtra("list", nameArray);
                intent.putExtra("key", numPlayers);
                startActivity(intent);

            }
        });


    }
}