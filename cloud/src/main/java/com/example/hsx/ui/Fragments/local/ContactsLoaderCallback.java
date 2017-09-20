package com.example.hsx.ui.Fragments.local;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

import com.han.utils.HanLog;

import static android.provider.ContactsContract.CommonDataKinds.Email.DISPLAY_NAME;
import static android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER;


/**
 * Created by hsx on 17-9-8.
 */

public class ContactsLoaderCallback implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private Context mCtx = null;

    public ContactsLoaderCallback(Context c) {
        this.mCtx = c;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        HanLog.write("OWNCLOUD", " PictureLoaderCallback() onCreateLoader()");

        String sql = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND (" + Contacts.HAS_PHONE_NUMBER + "=1) AND (" + Contacts.DISPLAY_NAME + " != '' ))";
/*
        return new CursorLoader(this.mCtx, mUri, new String[]{
                Contacts.DISPLAY_NAME,
                ContactsContract.Data.DATA1
        }, sql, null, Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
         */
        Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        return new CursorLoader(this.mCtx, CONTENT_URI, new String[]{DISPLAY_NAME, NUMBER}, sql, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int dataCount = data.getCount();
        int dataColumn = data.getColumnCount();

        HanLog.write("OWNCLOUD", " PictureLoaderCallback() onLoadFinished() data rows:" + data.getCount() + " columns:" + data.getColumnCount());
        for (int i = 0; i < dataCount; i ++) {
            data.moveToNext();

            StringBuilder columnInfo = new StringBuilder();
            for (int j = 0; j < dataColumn; j ++) {
                String colName = data.getColumnName(j);
                columnInfo.append("col:" + colName + " value:" + data.getString(data.getColumnIndex(colName)) + "\n");
            }

/*            int numberIndex = data.getColumnIndex(NUMBER);

            if (numberIndex < 0) {
                columnInfo.append("There is no index of column \"NUMBER\"");
                HanLog.write("OWNCLOUD", " PictureLoaderCallback() There is no index of column \"NUMBER\"");
            } else {
                columnInfo.append(" col[" + 0 + "]:" + data.getString(numberIndex));
            }*/

            HanLog.write("OWNCLOUD", " PictureLoaderCallback() rows:" + i + "\n" + columnInfo);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        HanLog.write("OWNCLOUD", " PictureLoaderCallback() onLoaderReset() ");
    }
}
