package com.example.shubhraj.notes;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shubhraj.notes.data.NotesContract.NoteEntry;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Shubhraj on 19-04-2017.
 */

public class ListsFragment extends android.support.v4.app.ListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    OnHeadlineSelectedListener mCallback;

    NoteCursorAdapter mCursorAdapter;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onArticleSelected(position);
    }

    public ListsFragment()
    {
        //Required Empty Constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        mCursorAdapter = new NoteCursorAdapter(getContext(), null);
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(listView,view,position,id);
            }
        });

        return rootView;
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                NoteEntry._ID,
                NoteEntry.COLUMN_NOTES_BODY };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                NoteEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

}
