package com.example.hsx.ui.Widgets;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by hsx on 17-9-20.
 */

public class PictureListView extends ListView implements com.example.hsx.presenter.Presenter.PictureListView{
    public PictureListView(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void update(Cursor c) {
    }
}
