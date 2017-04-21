package com.example.shubhraj.notes.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shubhraj on 20-04-2017.
 */

public class NotesContract
{
    private NotesContract()
    {

    }

    public static final String CONTENT_AUTHORITY = "com.example.shubhraj.notes";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NOTES = "notes";

    public static final class NoteEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public final static String TABLE_NAME = "notes"; //Table name needs to be username

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NOTES_BODY = "body";
    }

}