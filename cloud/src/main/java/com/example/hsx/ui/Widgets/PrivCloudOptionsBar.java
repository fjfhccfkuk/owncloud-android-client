package com.example.hsx.ui.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hsx.data.models.PrivCloudAccount;
import com.example.hsx.myapplication.R;

import java.util.zip.Inflater;

/**
 * Created by hsx on 17-8-26.
 */

public class PrivCloudOptionsBar extends LinearLayout {
    public PrivCloudOptionsBar(Context c, AttributeSet attr) {
        super(c, attr);

        View v = LayoutInflater.from(c).inflate(R.layout.optionsbar_layout, null);
        addView(v)  ;
    }
}
