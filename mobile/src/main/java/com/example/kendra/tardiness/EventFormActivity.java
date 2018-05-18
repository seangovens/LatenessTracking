package com.example.kendra.tardiness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;

import com.example.kendra.tardiness.fragments.DatePickerFragment;
import com.example.kendra.tardiness.fragments.TimePickerFragment;

import java.text.DateFormatSymbols;
import java.util.Date;

public class EventFormActivity extends AppCompatActivity {

    Helper helper;
    Button saveButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);

        int itemNum = 0;
        Helper.EVENT_TYPES eventType = Helper.EVENT_TYPES.FUTURE;
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            itemNum = (int)b.get("itemNumber");
            eventType = (Helper.EVENT_TYPES)b.get("eventType");
        }
        helper = Helper.getInstance();
        final Event event = helper.getAllEvents(eventType).get(itemNum);
        final TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.name);

        final TextView date = (TextView) findViewById(R.id.date);
        date.setText(Event.dateToString.format(event.date));

        TextView time = (TextView) findViewById(R.id.start_time);
        time.setText(Event.timeToString.format(event.startTime));

        saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.title = title.getText().toString();
                event.complete = true;
                finish();
            }
        });

        deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.removeEvent(event);
                finish();
            }
        });
    }

    public void dateEntry(View view) {
        DialogFragment frag = new DatePickerFragment();
        frag.show(getSupportFragmentManager(), "datePicker");
    }

    public void timeEntry(View view) {
        Bundle args = new Bundle();
        args.putInt("view", view.getId());
        DialogFragment frag = new TimePickerFragment();
        frag.setArguments(args);
        frag.show(getSupportFragmentManager(), "timePicker");
    }
}
