package com.example.hsx.ui.Activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.busap.utils.BusLog;
import com.example.hsx.myapplication.R;
import com.example.hsx.ui.AdapterListview;
import com.example.hsx.ui.IPictureView;
import com.example.hsx.ui.Widgets.IPointBtn;
import com.example.hsx.ui.Widgets.PointBtn;

public class MainActivity extends BaseActivity implements IPictureView.ViewPic {

    private TextView mTxt = null;
    private ImageView mImg = null;

    private ListView mListView = null;
    private Thread imagDownloader = null;
    private ListAdapter mListAdapter = null;

    private PointBtn mBtnLocal = null;
    private PointBtn mBtnCloud = null;
    private PointBtn mBtnMine = null;

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

        BtnListner listener = new BtnListner();

        mBtnCloud = (PointBtn) findViewById(R.id.btnCloud);
        mBtnCloud.setCallbackListener(listener);
        mBtnLocal = (PointBtn) findViewById(R.id.btnLocal);
        mBtnLocal.setCallbackListener(listener);
        mBtnMine = (PointBtn) findViewById(R.id.btnMine);
        mBtnMine.setCallbackListener(listener);
    }

    private class BtnListner implements IPointBtn.Change {
        @Override
        public void onShow(PointBtn v) {
            switch (v.getId()) {
                case R.id.btnCloud:
                    BusLog.write("OWNCLOUD", " cloud show");
                    break;
                case R.id.btnLocal:
                    BusLog.write("OWNCLOUD", " local show");
                    break;
                case R.id.btnMine:
                    BusLog.write("OWNCLOUD", " mine show");
                    break;
            }
        }

        @Override
        public void onDismiss(PointBtn v) {
            switch (v.getId()) {
                case R.id.btnCloud:
                    BusLog.write("OWNCLOUD", " cloud dismiss");
                    break;
                case R.id.btnLocal:
                    BusLog.write("OWNCLOUD", " local dismiss");
                    break;
                case R.id.btnMine:
                    BusLog.write("OWNCLOUD", " mine dismiss");
                    break;
            }
        }
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
