package com.poc.cursorloader;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView lstContact;
    SimpleCursorAdapter mAdapter;

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
    };
    String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
            + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstContact = (ListView) findViewById(R.id.lstContacts);

        mAdapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, null,
                new String[] { ContactsContract.Contacts.DISPLAY_NAME },
                new int[] { android.R.id.text1}, 0);
        lstContact.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(MainActivity.this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }
}
