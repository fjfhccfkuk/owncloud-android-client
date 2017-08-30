package com.example.hsx.ui.Activities;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.busap.utils.BusLog;
import com.example.hsx.adapter.AdapterFragmentPager;
import com.example.hsx.myapplication.R;
import com.example.hsx.ui.Fragments.CloudFragment;
import com.example.hsx.ui.Fragments.LocalFragment;
import com.example.hsx.ui.Fragments.MineFragment;
import com.example.hsx.ui.IPictureView;
import com.example.hsx.ui.Widgets.IPointBtn;
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
        mBtnLocal = (PointBtn) findViewById(R.id.btnLocal);
        mBtnCloud = (PointBtn) findViewById(R.id.btnCloud);
        mBtnMine  = (PointBtn) findViewById(R.id.btnMine);


        List<PointBtn> btnList = new ArrayList<PointBtn>();
        btnList.add(mBtnLocal);
        btnList.add(mBtnCloud);
        btnList.add(mBtnMine);

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
        mPager.addOnPageChangeListener(new PageChangedListener(btnList));

        BtnListner btnListener = new BtnListner(mPager);
        mBtnLocal.setCallbackListener(btnListener);
        mBtnCloud.setCallbackListener(btnListener);
        mBtnMine.setCallbackListener(btnListener);
    }

    private class PageChangedListener implements ViewPager.OnPageChangeListener {
        private List<PointBtn> mBtnList = null;

        public PageChangedListener(List<PointBtn> l) {
            this.mBtnList = l;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            PointBtn c = this.mBtnList.get(position);

            if (c == null)
                return;

            c.performClick();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private class BtnListner implements IPointBtn.Change {
        private ViewPager mPager = null;
        public BtnListner(ViewPager p) {
            this.mPager = p;
        }

        @Override
        public void onShow(PointBtn v) {
            switch (v.getId()) {
                case R.id.btnLocal:
                    this.mPager.setCurrentItem(0);
                    break;
                case R.id.btnCloud:
                    this.mPager.setCurrentItem(1);
                    break;
                case R.id.btnMine:
                    this.mPager.setCurrentItem(2);
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
