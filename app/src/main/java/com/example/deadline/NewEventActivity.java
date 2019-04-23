package com.example.deadline;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deadline.Fragments.DatePickerFragment;
import com.example.deadline.data.EventContract.EventEntry;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NewEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mNameET;
    private TextView mDateTV;
    private FloatingActionButton mSaveEventFAB;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String unifiedDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mNameET = findViewById(R.id.deadline_name_edittext);
        mDateTV = findViewById(R.id.date_tv);
        mSaveEventFAB = findViewById(R.id.save_event_fab);

        // Setting displayed date to current date when starting the activity
        mDateTV.setText("Select date");

        mDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker" );
            }
        });
    }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            int selectedMonth = month + 1;
            String currentDateString = String.format("%02d.%02d.%02d", dayOfMonth, selectedMonth, year);
            mDateTV.setText(currentDateString);
        }

    private void insertEvent() {
        // Get data to Strings
        String nameString = mNameET.getText().toString().trim();
        String dateString = mDateTV.getText().toString();

            SimpleDateFormat sdfSource = new SimpleDateFormat("dd.mm.yyyy");
        Date date = null;
        try {
            date = sdfSource.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdfDatabase = new SimpleDateFormat("yyyy-mm-dd");
            dateString = sdfDatabase.format(date);

            ContentValues values = new ContentValues();
            values.put(EventEntry.COLUMN_EVENT_NAME, nameString);
            values.put(EventEntry.COLUMN_EVENT_DATE, dateString);

            if (nameString.equals("")  && dateString != "Select Date") {
                Toast.makeText(this, "Event name and date required", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Something went wrong");
            } else {
                Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();
                Uri newUri = getContentResolver().insert(EventEntry.CONTENT_URI, values);
            }

        }





    public void saveFab(View v){
        insertEvent();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
