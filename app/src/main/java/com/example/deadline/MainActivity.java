package com.example.deadline;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.example.deadline.data.EventContract.EventEntry;
import com.example.deadline.data.EventDbHelper;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    EventDbHelper mDbHelper = new EventDbHelper(MainActivity.this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.add_event_fab);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EventEntry._ID,
                EventEntry.COLUMN_EVENT_NAME,
                EventEntry.COLUMN_EVENT_DATE };

       Cursor cursor = getContentResolver().query(EventEntry.CONTENT_URI,
               projection, null, null, EventEntry.TABLE_SORT_BY);

        ListView eventLV = findViewById(R.id.events_lv);

        EventCursorAdapter adapter = new EventCursorAdapter(this, cursor);

        eventLV.setAdapter(adapter);

    }


    public void newEventIntent(View v) {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }
}
