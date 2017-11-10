package com.example.hsx.ui.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.hsx.myapplication.R;

/**
 * Created by hsx on 17-8-30.
 */

public class SearchBar extends RelativeLayout {
    private Context mCtx = null;
    private ImageButton mBtn = null;

    public SearchBar(Context c, AttributeSet attrs) {
        super(c, attrs);

        this.mCtx = c;

        initView(this.mCtx, R.layout.search_bar_layout);
        mBtn = (ImageButton) findViewById(R.id.toolBtn);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(Context c, int resId) {
        View v = LayoutInflater.from(c).inflate(resId, null);
        addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
