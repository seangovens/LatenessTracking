package com.example.kendra.tardiness;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.io.Serializable;
import java.util.Timer;

/**
 * Created by Kendra on 2017-11-19.
 */

public class Event implements Parcelable{
    public String title;
    public String classification;
    public String previousActivity;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(classification);
        dest.writeString(previousActivity);
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
        id = Long.toString(System.currentTimeMillis());
        title = data;
    }

    public Event(String id, String data){
        this.id = id;
        title = data;
    }
}
