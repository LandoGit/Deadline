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


        // Perform a query on the pets table
       /** Cursor cursor= db.query(
                EventEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        */
       Cursor cursor = getContentResolver().query(EventEntry.CONTENT_URI,
               projection, null, null, null);

       TextView displayView = findViewById(R.id.events_lv);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The events table contains " + cursor.getCount() + " events.\n\n");
            displayView.append(EventEntry._ID + " - " +
                    EventEntry.COLUMN_EVENT_NAME + " - " +
                    EventEntry.COLUMN_EVENT_DATE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EventEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_DATE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                // Display the values from each column of the current row in the cursor in the
                // TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDate));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


    public void newEventIntent(View v) {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }
}
