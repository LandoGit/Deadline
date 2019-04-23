package com.example.deadline;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.deadline.data.EventContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCursorAdapter extends CursorAdapter {

    public EventCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView eventNameTV = view.findViewById(R.id.event_item_name_tv);
        TextView eventDateTV = view.findViewById(R.id.event_item_date_tv);

        // Extract properties from cursor
        int eventNameIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_EVENT_NAME);
        int eventDateIndex = cursor.getColumnIndex(EventContract.EventEntry.COLUMN_EVENT_DATE);

        String eventNameString = cursor.getString(eventNameIndex);
        String eventDateString = cursor.getString(eventDateIndex);

        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = sdfSource.parse(eventDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdfDatabase = new SimpleDateFormat("dd.mm.yyyy");
        eventDateString = sdfDatabase.format(date);

        // Populate fields with extracted properties
        eventNameTV.setText(eventNameString);
        eventDateTV.setText(eventDateString);
    }
}
