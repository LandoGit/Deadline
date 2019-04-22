package com.example.deadline;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.deadline.data.EventDbHelper;

import java.text.DateFormat;
import java.util.Calendar;


public class NewEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mNameET;
    private TextView mDateTV;
    private FloatingActionButton mSaveEventFAB;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

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
            String currentDateString = DateFormat.getDateInstance().format(c.getTime());
            mDateTV.setText(currentDateString);
        }

    private void saveEvent() {
        // Get data to Strings
        String nameString = mNameET.getText().toString().trim();
        String dateString = mDateTV.getText().toString();

        if (!nameString.equals("")  && dateString != "Select Date"){
            EventDbHelper mDbHelper = new EventDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(EventEntry.COLUMN_EVENT_NAME, nameString);
            values.put(EventEntry.COLUMN_EVENT_DATE, dateString);

            long newRowId = db.insert(EventEntry.TABLE_NAME, null, values);

            if (newRowId == -1) {
                Toast.makeText(this, "Error with saving event", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Event saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Event name and date required", Toast.LENGTH_SHORT).show();
        }






    }



    public void saveFab(View v){
        saveEvent();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
