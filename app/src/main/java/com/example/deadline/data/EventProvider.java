package com.example.deadline.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class EventProvider extends ContentProvider {

    private static final int EVENTS = 100;
    private static final int EVENT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_EVENTS, EVENTS);
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_EVENTS + "+", EVENT_ID);
    }

    public static final String LOG_TAG = EventProvider.class.getSimpleName();
    private EventDbHelper mDbHelper = new EventDbHelper(getContext());

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,  String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
