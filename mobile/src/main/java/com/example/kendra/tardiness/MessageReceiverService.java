package com.example.kendra.tardiness;

/**
 * Created by Kendra on 2017-11-28.
 */


        import android.content.Intent;
        import android.util.Log;

        import com.google.android.gms.wearable.DataEvent;
        import com.google.android.gms.wearable.DataEventBuffer;
        import com.google.android.gms.wearable.DataItem;
        import com.google.android.gms.wearable.MessageEvent;
        import com.google.android.gms.wearable.WearableListenerService;

public class MessageReceiverService extends WearableListenerService {
    private static final String TAG = "SensorDashboard/MessageReceiverService";


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Helper.saveEvent(new Event("SLEEP"), this);
    }

}