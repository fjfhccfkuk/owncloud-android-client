package com.example.hsx.ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.han.utils.HanLog;
import com.example.hsx.myapplication.R;
import com.example.hsx.ui.Activities.LocalPictureActivity;
import com.example.hsx.ui.Widgets.LocalItem;

/**
 * Created by hsx on 17-8-29.
 */

public class LocalFragment extends Fragment {

    private LocalItem mPic = null;
    private LocalItem mMusic = null;
    private LocalItem mVideo = null;
    private LocalItem mNewly = null;
    private LocalItem mSDStorage = null;
    private LocalItem mInternalStorage = null;

    private View.OnClickListener mItemClickListener = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mItemClickListener = new LocalItemClickListener(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_local_layout, null);

        mPic = (LocalItem) v.findViewById(R.id.itemPic);
        mPic.setOnClickListener(this.mItemClickListener);

        mMusic = (LocalItem) v.findViewById(R.id.itemMusic);
        mMusic.setOnClickListener(this.mItemClickListener);

        mVideo = (LocalItem) v.findViewById(R.id.itemVideo);
        mVideo.setOnClickListener(this.mItemClickListener);

        mNewly = (LocalItem) v.findViewById(R.id.itemNewly);
        mNewly.setOnClickListener(this.mItemClickListener);

        mSDStorage = (LocalItem) v.findViewById(R.id.itemSD);
        mSDStorage.setOnClickListener(this.mItemClickListener);

        mInternalStorage = (LocalItem) v.findViewById(R.id.itemInternal);
        mInternalStorage.setOnClickListener(this.mItemClickListener);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

/*
        new LruCache<>()
*/
        HanLog.write("OWNCLOUD", " local fragment onResume , max available mem size" + Runtime.getRuntime().maxMemory());
    }

    @Override
    public void onPause() {
        super.onPause();

        HanLog.write("OWNCLOUD", " local fragment onPause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        HanLog.write("OWNCLOUD", " local fragment onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        HanLog.write("OWNCLOUD", " local fragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        HanLog.write("OWNCLOUD", " local fragment onDestroy");
    }

    private class LocalItemClickListener implements View.OnClickListener {
        Context mC = null;

        LocalItemClickListener(Context c) {
            mC = c;
        }

        Intent i = null;

        @Override
        public void onClick(View v) {
            i = null;
            switch(v.getId()) {
                case R.id.itemPic:
                    i = new Intent();
                    i.setClass(this.mC, LocalPictureActivity.class);
                    break;
                case R.id.itemMusic:
                    break;
                case R.id.itemVideo:
                    break;
                case R.id.itemNewly:
                    break;
                case R.id.itemSD:
                    break;
                case R.id.itemInternal:
                    break;
            }

            if (i != null) {
                this.mC.startActivity(i);
            }
        }
    }
}
