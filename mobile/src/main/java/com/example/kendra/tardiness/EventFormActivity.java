package com.example.kendra.tardiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);
        int itemNum = 0;
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            itemNum = (int)b.get("itemNumber");
        }
        Event event = Helper.getAllEvents(this).get(itemNum);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.title);
    }
}
