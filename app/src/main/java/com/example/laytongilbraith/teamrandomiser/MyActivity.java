package com.example.laytongilbraith.teamrandomiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;


public class MyActivity extends Activity {

    static final int NEW_TEAM_LIST = 1;
    ArrayList<SortedMap<String, Boolean>> players;
    ArrayList<String> generatedTeam;
    StringBoolTreeMapAdapter currentPlayerListAdapter;
    StringArrayListAdapter generatedPlayerListAdapter;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        random = new Random();

        players = new ArrayList<SortedMap<String, Boolean>>();

        // Set adapter for current player list
        currentPlayerListAdapter = new StringBoolTreeMapAdapter(players, this);
        ListView currentPlayerView = (ListView) findViewById(R.id.current_players);
        currentPlayerView.setAdapter(currentPlayerListAdapter);

        // Set adapter for generated team
        generatedTeam = new ArrayList<String>();
        generatedPlayerListAdapter = new StringArrayListAdapter(generatedTeam, this);
        ListView generatedPlayerView = (ListView) findViewById(R.id.generated_players);
        generatedPlayerView.setAdapter(generatedPlayerListAdapter);

        final Button generateTeam = (Button) findViewById(R.id.btn_generate_team);
        generateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTeam();
            }
        });

        Button managePlayersBtn = (Button) findViewById(R.id.manage_players_btn);
        managePlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, ManagePlayers.class);
                intent.putExtra("playerList", players);
                startActivityForResult(intent, NEW_TEAM_LIST);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateTeam() {
        if(players.isEmpty()){
            Toast toast = Toast.makeText(this, "Try adding some players. . .", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        generatedTeam.clear();

        ArrayList<String> playersToChooseFrom = new ArrayList<String>();
        for (SortedMap<String, Boolean> player : players) {
            String name = player.firstKey();
            if (player.get(name)) {
                playersToChooseFrom.add(name);
            }
        }

        int maxPlayers = 10;
        if (playersToChooseFrom.size() < 10) {
            maxPlayers = playersToChooseFrom.size();
        }

        for (int i = 0; i < maxPlayers; i++) {
            int nextIndex = random.nextInt(playersToChooseFrom.size());
            generatedTeam.add(playersToChooseFrom.get(nextIndex));
            playersToChooseFrom.remove(nextIndex);
        }
        generatedPlayerListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_TEAM_LIST) {
            if (resultCode == RESULT_OK) {
                //Required because SortedMaps passed between activities turn into HashMaps. WHY GOOGLE WHY?!!!!!
                ArrayList<HashMap<String, Boolean>> newList = (ArrayList<HashMap<String, Boolean>>) data.getSerializableExtra("playerList");
                players.clear();
                for (int i = 0; i < newList.size(); i++) {
                    //Required because SortedMaps passed between activities turn into HashMaps. WHY GOOGLE WHY?!!!!!
                    SortedMap<String, Boolean> player = new TreeMap<String, Boolean>((Map<String, Boolean>) newList.get(i));
                    players.add(player);
                }
                currentPlayerListAdapter.notifyDataSetChanged();
            }
            generatedTeam.clear();
            generatedPlayerListAdapter.notifyDataSetChanged();
        }
    }
}
