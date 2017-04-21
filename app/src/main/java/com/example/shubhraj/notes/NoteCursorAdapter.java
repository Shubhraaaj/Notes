package com.example.shubhraj.notes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import com.example.shubhraj.notes.data.NotesContract.NoteEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shubhraj on 19-04-2017.
 */

public class NoteCursorAdapter extends CursorAdapter
{
    public NoteCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.content);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(NoteEntry.COLUMN_NOTES_BODY);

        String NoteName = cursor.getString(nameColumnIndex);

        nameTextView.setText(NoteName);
    }
}
