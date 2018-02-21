package com.example.kendra.tardiness;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

//import com.google.android.gms.nearby.messages.internal.Update;

import java.io.Serializable;
import java.util.*;

import static com.example.kendra.tardiness.R.id.text;

public class ListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Temp fill list
        ArrayList<Event> events = Helper.getAllEvents(this);
        for( Event e: events){
            Helper.removeEvent(e.id, this);
        }
        Helper.saveEvent(new Event("Brush Teeth"), this);
        Helper.saveEvent(new Event("Coffee"), this);
        listView = (ListView) findViewById(R.id.event_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                displayEventForm(position);
            }
        });
        updateUI();

        if (SavedAuthInfo.getUName(this).length() == 0) {
            //GoogleAuthActivity.mainContext = this;
            Intent intent = new Intent(this, GoogleAuthActivity.class);
            startActivity(intent);
        }
    }

    public void displayEventForm(int position) {
        Intent intent = new Intent(this, EventFormActivity.class);
        int itemNum = position/50;
        intent.putExtra("itemNumber", itemNum);
        this.startActivity(intent);

        //Trigger speech
        /*Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "WHAT?!");
        startActivityForResult(intent, 1001);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data ) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String message = results.get(0);
            Helper.saveEvent(new Event(message), this);
            updateUI();
        } else {
            String message = "bird";
            Helper.saveEvent(new Event(message), this);
            updateUI();
        }
    }

    public void updateUI(){
        ArrayList<Event> events = Helper.getAllEvents(this);
        /*for(Event e : events) {
            View child = new EventItemView(this, e.title, "Temp");
            listView.addView(child);
        }*/

        ListViewAdapter lva = new ListViewAdapter(this, 0, events);
        listView.setAdapter(lva);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
