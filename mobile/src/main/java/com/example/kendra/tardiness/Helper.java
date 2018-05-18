package com.example.kendra.tardiness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Kendra on 2017-11-19.
 */

public class Helper {

    HashMap<String, Event> events;
    public ArrayList<Event> toDo;
    public ArrayList<Event> complete;
    public ArrayList<Event> future;

    private HashSet<String> completedSet;

    public static enum EVENT_TYPES {
        COMPLETE,
        TODO,
        FUTURE
    }

    private static Helper helper;

    public static Context context;

    private Helper() {
        events = new HashMap<String, Event>();
        toDo = new ArrayList<Event>();
        complete = new ArrayList<Event>();
        future = new ArrayList<Event>();
        completedSet = new HashSet<>();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> key = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : key.entrySet()) {
            String savedData = (String) entry.getValue();
            if (savedData != null) {
                Event event = new Event(entry.getKey(), savedData);
                events.put(event.title, event);
            }
        }
        updateSubLists();
    }

    public void updateSubLists() {
        ArrayList<Event> myEvents = new ArrayList<>(events.values());
        //complete.clear();
        toDo.clear();
        future.clear();
        for(Event e: myEvents) {
        //if ( time is in the future)
            if (completedSet.contains(e.title))
                continue;

            if (e.complete) {
                complete.add(e);
                completedSet.add(e.title);
            } else if (e.date.compareTo( Calendar.getInstance().getTime()) > 0) {
                future.add(e);
            } else {
                toDo.add(e);
            }
        }
    }

    public static Helper getInstance() {
        if (context == null) {
            return null;
        }
        if (helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    public  String addEvent(Event event) {
        if (!events.containsKey(event.title))
            events.put(event.title, event);
        return event.id;
    }

    public ArrayList<Event> getAllEvents(EVENT_TYPES t) {
        updateSubLists();
        if(t.equals( EVENT_TYPES.COMPLETE)) {
            return complete;
        } else if (t.equals( EVENT_TYPES.TODO)){
            return toDo;
        } else {
            return future;
        }
    }

    public void removeEvent(Event e) {
        events.remove(e.id);
        saveEvents(context);
        updateSubLists();
    }

    public void saveEvents(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        ArrayList<Event> myEvents = new ArrayList<>(events.values());

        for (Event e : myEvents) {
            editor.putString(e.id, e.getCSVEntry());
        }
        editor.commit();
    }

    public String exportTheData()
    {
        StringBuilder data = new StringBuilder();
        data.append("Name,Date,StartTime");
        data.append("\n");

        helper.updateSubLists();
        ArrayList<Event> myEvents = new ArrayList<Event>(events.values());
        for (Event e: helper.complete) {
            data.append(e.name+","+e.date+","+e.startTime);
            data.append("\n");
        }
        for (Event e : complete) {
            events.remove(e.id);
        }
        saveEvents(context);
        updateSubLists();


        String fileContents = data.toString();
        return fileContents;
    }
}