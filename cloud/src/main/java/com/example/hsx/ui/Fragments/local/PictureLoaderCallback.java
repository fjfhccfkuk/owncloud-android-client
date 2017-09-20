package com.example.hsx.ui.Fragments.local;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import com.han.utils.HanLog;


/**
 * Created by hsx on 17-9-8.
 */

public class PictureLoaderCallback implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private Context mCtx = null;

    public PictureLoaderCallback(Context c) {
        this.mCtx = c;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void foo() {}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        HanLog.write("OWNCLOUD", " PictureLoaderCallback() onCreateLoader()");

        String sql = "((" + Images.Thumbnails.IMAGE_ID + " > 0))";
/*
        return new CursorLoader(this.mCtx, mUri, new String[]{
                Contacts.DISPLAY_NAME,
                ContactsContract.Data.DATA1
        }, sql, null, Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
         */

        Uri thumbInterUrl = Images.Media.EXTERNAL_CONTENT_URI;

        return new CursorLoader(this.mCtx, thumbInterUrl, null/**new String[]{MediaStore.Images.Thumbnails.DATA}*/, null/**sql*/, null, null);
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

            HanLog.writeDisk("OWNCLOUD", " PictureLoaderCallback() rows:" + i + "\n" + columnInfo);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        HanLog.write("OWNCLOUD", " PictureLoaderCallback() onLoaderReset() ");
    }
}
