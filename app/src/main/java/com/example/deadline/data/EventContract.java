package com.example.deadline.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class EventContract {

    private EventContract (){}

    public static final String CONTENT_AUTHORITY = "com.example.deadline";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EVENTS = "events";


    public static final class EventEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EVENTS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + "/" + PATH_EVENTS;
        public final static String TABLE_NAME = "events";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EVENT_NAME = "name";
        public final static String COLUMN_EVENT_DATE = "date";

        public final static String TABLE_SORT_BY = "date ASC";
    }
}
