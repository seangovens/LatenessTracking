package com.example.kendra.tardiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventFormActivity extends AppCompatActivity {

    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);
        int itemNum = 0;
        Helper.EVENT_TYPES eventType = Helper.EVENT_TYPES.TODO;
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            itemNum = (int)b.get("itemNumber");
            eventType = (Helper.EVENT_TYPES)b.get("eventType");
        }
        helper = Helper.getInstance(this);
        Event event = helper.getAllEvents(eventType).get(itemNum);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.name);

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(event.dateToString.format(event.date));

        TextView time = (TextView) findViewById(R.id.start_time);
        time.setText(event.timeToString.format(event.startTime));
    }
}
