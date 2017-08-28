package com.example.hsx.ui.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hsx.myapplication.R;
import com.example.hsx.ui.AdapterListview;
import com.example.hsx.ui.IPictureView;

public class MainActivity extends BaseActivity implements IPictureView.ViewPic {

    private TextView mTxt = null;
    private ImageView mImg = null;

    private ListView mListView = null;
    private Thread imagDownloader = null;
    private ListAdapter mListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setBackgroundColor(Color.GRAY);
        mListAdapter = new AdapterListview(this);
        mListView.setAdapter(mListAdapter);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        imagDownloader.interrupt();
    }

    @Override
    public void showText(Bitmap s) {
        mImg.setImageBitmap(s);
    }

    @Override
    public void resetText() {

    }
}
