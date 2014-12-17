package com.example.laytongilbraith.teamrandomiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;


public class ManagePlayers extends Activity {

    private Button addPlayerBtn;
    private ArrayList<SortedMap<String, Boolean>> players;
    private StringBoolTreeMapAdapter playerArrayAdapter;
    private Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_players);

        Intent intent = getIntent();
        ArrayList<HashMap<String, Boolean>> newPlayerList = (ArrayList<HashMap<String, Boolean>>) intent.getSerializableExtra("playerList");
        if (newPlayerList == null) {
            newPlayerList = new ArrayList<HashMap<String, Boolean>>();
        }
        if (players == null) {
            players = new ArrayList<SortedMap<String, Boolean>>();
        }
        players.clear();
        for (int i = 0; i < newPlayerList.size(); i++) {
            SortedMap<String, Boolean> player = new TreeMap<String, Boolean>(newPlayerList.get(i));
            players.add(player);
        }

        playerArrayAdapter = new StringBoolTreeMapAdapter(players, this);
        ListView playerListView = (ListView) findViewById(R.id.current_players);
        playerListView.setAdapter(playerArrayAdapter);

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = players.get(position).firstKey();
                players.get(position).put(name, !players.get(position).get(name));
                playerArrayAdapter.notifyDataSetChanged();
            }
        });

        playerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                players.remove(position);
                playerArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        addPlayerBtn = (Button) findViewById(R.id.add_player_btn);
        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_players, menu);
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
        if (id == android.R.id.home) {
            returnIntent = new Intent();
            returnIntent.putExtra("playerList", players);
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addPlayer() {
        EditText playerNameEditText = (EditText) findViewById(R.id.new_player_name);
        String playerName = playerNameEditText.getText().toString();
        if (playerName == null || playerName.isEmpty()) {
            return;
        }

        playerName = playerName.toUpperCase();
        if (players.contains(playerName)) {
            return;
        }
        SortedMap<String, Boolean> playerEntry = new TreeMap<String, Boolean>();
        playerEntry.put(playerName, true);
        players.add(playerEntry);
        playerNameEditText.setText("");

        playerArrayAdapter.notifyDataSetChanged();
    }
}
