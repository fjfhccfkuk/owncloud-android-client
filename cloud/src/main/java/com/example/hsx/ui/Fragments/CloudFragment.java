package com.example.hsx.ui.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.busap.utils.BusLog;
import com.example.hsx.myapplication.R;

/**
 * Created by hsx on 17-8-29.
 */

public class CloudFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusLog.write("OWNCLOUD", " CloudFragment onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BusLog.write("OWNCLOUD", " CloudFragment onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusLog.write("OWNCLOUD", " CloudFragment onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusLog.write("OWNCLOUD", " CloudFragment onDetach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cloud_layout, null);
    }
}
