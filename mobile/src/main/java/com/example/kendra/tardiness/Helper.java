package com.example.kendra.tardiness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kendra on 2017-11-19.
 * Following tutorial https://www.youtube.com/watch?v=vPlMzs0C-nI
 */

public class Helper {
    public static String saveEvent(Event event, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //event.id = String.valueOf(System.currentTimeMillis());
        editor.putString(event.id, event.title);
        editor.commit();
        return event.id;
    }

    public static ArrayList<Event> getAllEvents(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Event> events = new ArrayList<Event>();
        Map<String, ?> key = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : key.entrySet()) {
            String savedData = (String) entry.getValue();
            if (savedData != null) {
                Event event = new Event(entry.getKey(), savedData);
                events.add(event);
            } else {
                events.add(new Event("Nope"));
            }
        }
        return events;
    }

    public static void removeEvent(String id, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(id);
        editor.commit();
    }
}