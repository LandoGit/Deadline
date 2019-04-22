package com.example.deadline.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.EventLog;
import android.util.Log;

import com.example.deadline.data.EventContract.EventEntry;

import static com.example.deadline.data.EventDbHelper.LOG_TAG;


public class EventProvider extends ContentProvider {

    private EventDbHelper mDbHelper;

    private static final int EVENTS = 111;
    private static final int EVENT_ID = 222;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_EVENTS, EVENTS);
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY, EventContract.PATH_EVENTS + "/#", EVENT_ID);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new EventDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                cursor = database.query(EventEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EVENT_ID:
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(EventEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        return cursor;
    }

    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return EventEntry.CONTENT_LIST_TYPE;
            case EVENT_ID:
                return EventEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Override
    public Uri insert( Uri uri, ContentValues contentValues)  {
    int match = sUriMatcher.match(uri);
    switch (match){
        case EVENTS:
            return insertEvent(uri, contentValues);
        default:
            throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertEvent(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(EventEntry.TABLE_NAME, null, values);

        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " +  uri);
        }
        return ContentUris.withAppendedId(uri, id);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                // Delete all rows that match the selection and selection args
                return database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
            case EVENT_ID:
                // Delete a single row given by the ID in the URI
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Something went wrong");
        }
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return updatePet(uri, values, selection, selectionArgs);
            case EVENT_ID:
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Something went wrong");
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        String name = values.getAsString(EventEntry.COLUMN_EVENT_NAME);
        String date = values.getAsString(EventEntry.COLUMN_EVENT_DATE);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        return database.update(EventEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
