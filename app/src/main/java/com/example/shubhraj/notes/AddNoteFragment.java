package com.example.shubhraj.notes;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.shubhraj.notes.data.NotesContract.NoteEntry;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Created by Shubhraj on 19-04-2017.
 */

public class AddNoteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int EXISTING_PET_LOADER = 0;
    private EditText body;
    public AddNoteFragment()
    {
        //REQUIRED
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.add_notes, container, false);
        body = (EditText) rootView.findViewById(R.id.body);
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                NoteEntry._ID,
                NoteEntry.COLUMN_NOTES_BODY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                NoteEntry.CONTENT_URI,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if(cursor.moveToFirst())
        {
            int bodyColumnIndex = cursor.getColumnIndex(NoteEntry.COLUMN_NOTES_BODY);
            String name = cursor.getString(bodyColumnIndex);
            body.setText(name);
            saveNote();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        body.setText("");
    }

    private void saveNote()
    {
        String nameString = body.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(NoteEntry.COLUMN_NOTES_BODY, nameString);
        Uri newUri = getActivity().getContentResolver().insert(NoteEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(getContext(), "Failed to add Note",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),"Note Added",
                        Toast.LENGTH_SHORT).show();
            }
    }
}
