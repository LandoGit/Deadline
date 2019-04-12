package com.example.deadline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;


public class NewEventActivity extends AppCompatActivity {

    TextView dateTV;
    FloatingActionButton saveEventFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        dateTV = findViewById(R.id.date_tv);
        saveEventFAB = findViewById(R.id.save_event_button);


        saveEventFAB = findViewById(R.id.add_event_fab);
        saveEventFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewEventActivity.this, MainActivity.class));
            }
        });
    }
}
