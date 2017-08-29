package com.example.hsx.ui.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hsx.adapter.AdapterFragmentPager;
import com.example.hsx.myapplication.R;
import com.example.hsx.ui.Fragments.CloudFragment;
import com.example.hsx.ui.Fragments.LocalFragment;
import com.example.hsx.ui.Fragments.MineFragment;
import com.example.hsx.ui.IPictureView;
import com.example.hsx.ui.Widgets.PointBtn;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements IPictureView.ViewPic {

    private TextView mTxt = null;
    private ImageView mImg = null;

    private ListView mListView = null;
    private Thread imagDownloader = null;
    private ListAdapter mListAdapter = null;

    private PointBtn mBtnLocal = null;
    private PointBtn mBtnCloud = null;
    private PointBtn mBtnMine = null;
    private ViewPager mPager = null;
    private Fragment mLocalFragment = null;
    private Fragment mCloudFragment = null;
    private Fragment mMineFragment  = null;

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
        mCloudFragment = new CloudFragment();
        mMineFragment  = new MineFragment();
        mLocalFragment = new LocalFragment();

        List<Fragment> fl = new ArrayList<Fragment>();

        fl.add(mLocalFragment);
        fl.add(mCloudFragment);
        fl.add(mMineFragment);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new AdapterFragmentPager(getSupportFragmentManager(), fl));
        mPager.setCurrentItem(0);
    }

/*
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
*/

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
