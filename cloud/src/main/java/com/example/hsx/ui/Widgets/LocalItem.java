package com.example.hsx.ui.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hsx.myapplication.R;

/**
 * Created by hsx on 17-9-1.
 */

public class LocalItem extends LinearLayout{

    private ImageView mImg = null;
    private TextView  mTxt = null;

    private Drawable mDrawable = null;
    private String   mString = null;

    public LocalItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);

        int size = attrs.getAttributeCount();
//        HanLog.write("OWNCLOUD", " attrs cc:" + attrs.getAttributeValue(null, "cc"));

        View vImg = LayoutInflater.from(context).inflate(R.layout.fragment_local_image_item, this);
        ImageView iv = (ImageView) vImg.findViewById(R.id.iv);

        View vTxt = LayoutInflater.from(context).inflate(R.layout.fragment_local_text_item, this);
        TextView tv = (TextView) vTxt.findViewById(R.id.tv);

        initWidgetAttrs(context.obtainStyledAttributes(attrs, R.styleable.LocalItem));
        iv.setImageDrawable(this.mDrawable);
        tv.setText(this.mString);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void initWidgetAttrs(TypedArray a) {
        int count = a.getIndexCount();

        for (int i = 0; i < count; i ++) {
            int index = a.getIndex(i);

            switch (index) {
                case R.styleable.LocalItem_img:
                    this.mDrawable = a.getDrawable(index);
                    break;
                case R.styleable.LocalItem_txt:
                    this.mString = a.getString(index);
                    break;
            }
        }
    }
}
