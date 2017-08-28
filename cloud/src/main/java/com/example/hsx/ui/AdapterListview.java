package com.example.hsx.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hsx.data.DataInjection;
import com.example.hsx.data.DataRepository;
import com.example.hsx.myapplication.R;
import com.example.hsx.presenter.OwnPicturePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsx on 17-8-10.
 */

public class AdapterListview extends BaseAdapter{
    private IPictureView.Presenter presentor;
    private DataRepository data = null;
    private Context mCtx = null;
    private Drawable mDefDrawable = null;
    private List<ImageView> mList  = null;

    public AdapterListview(Context c) {
        mCtx = c;
        data = DataInjection.providerDataRepository(c);
//        presentor = new OwnPicTextPresenter(this, data);
        presentor = new OwnPicturePresenter(null, data);
        mDefDrawable = mCtx.getResources().getDrawable(R.drawable.def);
        mList = new ArrayList<ImageView>();
    }

    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("LISTVIEW", " getView() pos:" + position);
        ImageView img = null;
        if (convertView == null) {
            img = new ImageView(mCtx);
            img.setTag(img);
            img.setBackgroundColor(Color.BLUE);
        } else {
            img = (ImageView) convertView.getTag();
        }

//        img.getLayoutParams().height = 400;

        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(mDefDrawable);

        presentor.getPhotoName(img, position % 10 + 1);

//        mList.add(img);
        return img;
    }
}
