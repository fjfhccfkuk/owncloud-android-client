package com.example.hsx.adapter;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.hsx.presenter.Presenter;

/**
 * Created by hsx on 17-9-20.
 */

public class LocalPictureListViewAdapter extends BaseAdapter implements Presenter.PictureListView{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void update(Cursor c) {

    }

    public interface Presenter {
        void start();
    }
}
