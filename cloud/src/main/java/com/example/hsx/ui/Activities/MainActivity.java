package com.example.hsx.ui.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.hsx.ui.Fragments.CloudFragment;
import com.example.hsx.ui.Fragments.LocalFragment;
import com.example.hsx.ui.Fragments.MineFragment;
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
    private FragmentManager mFragManager = null;
    private Fragment mCloudFragment = null;
    private Fragment mMineFragment = null;
    private Fragment mLocalFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

/*
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setBackgroundColor(Color.GRAY);
        mListAdapter = new AdapterListview(this);
        mListView.setAdapter(mListAdapter);
*/

        BtnListner listener = new BtnListner();

        mBtnCloud = (PointBtn) findViewById(R.id.btnCloud);
        mBtnCloud.setCallbackListener(listener);
        mBtnLocal = (PointBtn) findViewById(R.id.btnLocal);
        mBtnLocal.setCallbackListener(listener);
        mBtnMine = (PointBtn) findViewById(R.id.btnMine);
        mBtnMine.setCallbackListener(listener);

        mFragManager = this.getFragmentManager();
    }

    private class BtnListner implements IPointBtn.Change {
        private void replaceFragment(FragmentManager fm, Fragment f) {
            if (fm == null)
                return;
            if (f == null)
                return;

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_root, f);
            ft.commit();
        }

        @Override
        public void onShow(PointBtn v) {
            switch (v.getId()) {
                case R.id.btnCloud:
                    BusLog.write("OWNCLOUD", " cloud show");
                    if (mCloudFragment == null)
                        mCloudFragment = new CloudFragment();

                    replaceFragment(mFragManager, mCloudFragment);
                    break;
                case R.id.btnLocal:
                    BusLog.write("OWNCLOUD", " local show");
                    if (mLocalFragment == null)
                        mLocalFragment = new LocalFragment();

                    replaceFragment(mFragManager, mLocalFragment);
                    break;
                case R.id.btnMine:
                    BusLog.write("OWNCLOUD", " mine show");
                    if (mMineFragment == null)
                        mMineFragment = new MineFragment();

                    replaceFragment(mFragManager, mMineFragment);
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
