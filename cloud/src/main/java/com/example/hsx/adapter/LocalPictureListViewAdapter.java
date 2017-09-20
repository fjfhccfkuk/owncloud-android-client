package com.example.hsx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hsx.myapplication.R;
import com.example.hsx.presenter.Presenter;
import com.example.hsx.ui.Fragments.local.PictureGrid;
import com.han.utils.HanLog;

import java.util.ArrayList;

/**
 * Created by hsx on 17-9-20.
 */

public class LocalPictureListViewAdapter extends BaseAdapter implements Presenter.PictureListViewAdapter{
    private Context mCtx = null;
    private int picSize = 0;
    private ImageView itemView = null;//LayoutInflater.from(mCtx).inflate(R.layout.list_item_pic_image, null);
    private ArrayList<ImageView> mPicList = new ArrayList<ImageView>();

    public LocalPictureListViewAdapter(Context c) {
        this.mCtx = c;
    }

    @Override
    public int getCount() {
        return (picSize==0)?0:2;
    }

    @Override
    public Object getItem(int position) {
        HanLog.write("OWNCLOUD", "LocalPictureListViewAdapter.getItem() ");
        itemView = new ImageView(mCtx);
        itemView.setBackgroundColor(Color.GRAY);
        return itemView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HanLog.write("OWNCLOUD", "LocalPictureListViewAdapter.getView() ");
//        View v = LayoutInflater.from(mCtx).inflate(R.layout.list_item_pic_image, ll);
//        View v = LayoutInflater.from(mCtx).inflate(R.layout.list_item_pic_image, null);

/*
        ImageView v = new ImageView(mCtx);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        v.setBackgroundColor(Color.GRAY);
        v.setImageBitmap(BitmapFactory.decodeFile(mPicList.get(position)));
*/

        return mPicList.get(position);
    }

    @Override
    public void update(Cursor c) {
        mPicList.clear();
        picSize = 2;//c.getCount();
        String path = null;
        int index = 0;
        ImageView v = null;

//        c.moveToFirst();
        int rowSize = 2;
        for (int i = 0; i < rowSize; i ++) {
            c.moveToNext();
/*
            for (int j = 0; j < c.getColumnCount(); j ++) {
                HanLog.write("OWNCLOUD", " LocalPictureListViewAdapter.update() col[" + j + "]="+c.getColumnName(j) + " \"_data\" index:" + c.getColumnIndex("_data"));
            }
*/

            index = c.getColumnIndex("_data");
//            HanLog.write("OWNCLOUD", " LocalPictureListViewAdapter.update() index:" + index);
            path = c.getString(index);
//            HanLog.write("OWNCLOUD", " LocalPictureListViewAdapter.update() index:" + index + " path:" + path);

            v = new ImageView(mCtx);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
            v.setImageBitmap(BitmapFactory.decodeFile(path));
            mPicList.add(v);
        }

        notifyDataSetChanged();
        HanLog.write("OWNCLOUD", " LocalPictureListViewAdapter.update() picSize:" + picSize);
    }
}
