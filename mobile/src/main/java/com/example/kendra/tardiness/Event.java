package com.example.kendra.tardiness;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;
import java.util.Locale;
import java.util.Timer;


/**
 * Created by Kendra on 2017-11-19.
 */

public class Event implements Parcelable{
    public String title;
    public String name;
    public String classification;
    public int trasportMethod;
    public int waitingPeople;
    public String why;
    public String consequences;
    public int importance;
    public Date date;
    public Date switchTime;
    public Date realizeTime;
    public Date arrivalTime;
    public Date startTime;
    public String id;
    public String tippingPoint;
    public boolean complete;
    DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSZ", Locale.ENGLISH);
    DateFormat dateToString = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    DateFormat timeToString = new SimpleDateFormat("hh:mm a");

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(classification);
        dest.writeInt(trasportMethod);
        dest.writeInt(waitingPeople);
        dest.writeString(why);
        dest.writeString(consequences);
        dest.writeInt(importance);
        dest.writeLong(date.getTime());
        dest.writeLong(switchTime.getTime());
        dest.writeLong(realizeTime.getTime());
        dest.writeLong(arrivalTime.getTime());
        dest.writeLong(startTime.getTime());
        dest.writeString(tippingPoint);
    }

    public enum TRANSPORTATION{
        WALKING,
        WALKINGPLUS,
        BIKING,
        BUS,
        TRAIN,
        CAR
    }

    public Event(String data){
        this(Long.toString(System.currentTimeMillis()), data);
    }

    public Event(String id, String data){
        this.id = id;
        title = data;
        complete = false;
        String[] input = title.split("\\(");
        name = input[0].trim();

        StringBuilder builder = new StringBuilder(input[1]);
        //builder.deleteCharAt(10); //Remove T
        builder.deleteCharAt(builder.length() - 1);
        //builder.deleteCharAt(0); //Removing () around datetime
        try {
            date = formatDate.parse(builder.toString());
            startTime = formatDate.parse(builder.toString());
        } catch (Exception e) {

        }
    }

    public String getCSVEntry() {
        return String.format("%s, %s, %s, %s", title, date, startTime, switchTime);
    }
}
