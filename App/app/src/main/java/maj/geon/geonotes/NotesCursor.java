package maj.geon.geonotes;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import maj.geon.geonotes.util.GNConstants;

/**
 * Created by debjeet on 12/4/16.
 */
public class NotesCursor implements LoaderManager.LoaderCallbacks<Cursor> {


    SimpleCursorAdapter mAdapter;
    Activity activity;

    public NotesCursor(SimpleCursorAdapter mAdapter, Activity activity) {
        this.mAdapter = mAdapter;
        this.activity =  activity;
    }

    
    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};

    // This is the select criteria
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Log.d(GNConstants.APP_NAME,PROJECTION+"\n"+SELECTION);

        return new CursorLoader(this.activity, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

}
