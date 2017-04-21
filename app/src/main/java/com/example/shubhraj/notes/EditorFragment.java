package com.example.shubhraj.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.shubhraj.notes.data.NotesContract.NoteEntry;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class EditorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int EXISTING_PET_LOADER = 0;

    private Uri mCurrentNoteUri;

    private EditText content;

    private boolean mPetHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_editor,container, false);
        content = (EditText)rootView.findViewById(R.id.edit_content);
        content.setOnTouchListener(mTouchListener);
        Intent intent = getActivity().getIntent();
        mCurrentNoteUri = intent.getParcelableExtra("NOTEURI");
        String delete = intent.getStringExtra("DELETE");
        if(delete == "delete")
            deleteNote();
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
    }

    public void onResume()
    {
        super.onResume();
    }

    public void onPause()
    {
        super.onPause();
    }

    private void saveNote()
    {
        String nameString = content.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(NoteEntry.COLUMN_NOTES_BODY, nameString);

        int rowsAffected = getActivity().getApplicationContext().getContentResolver().update(mCurrentNoteUri, values, null, null);
        if (rowsAffected == 0) {
            Toast.makeText(getContext(), "Failed to update",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Update Successful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                NoteEntry._ID,
                NoteEntry.COLUMN_NOTES_BODY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                mCurrentNoteUri,         // Query the content URI for the current pet
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
            content.setText(name);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        content.setText("");
    }

    public void onBackPressed()
    {
        saveNote();
    }
    private void deleteNote()
    {
        int rowsDeleted = getActivity().getApplicationContext().getContentResolver().
                delete(mCurrentNoteUri, null, null);

        // Show a toast message depending on whether or not the delete was successful.
        if (rowsDeleted == 0)
        {
            // If no rows were deleted, then there was an error with the delete.
            Toast.makeText(getContext(),"Deletion Failed", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the delete was successful and we can display a toast.
            Toast.makeText(getContext(),"Deletion Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
