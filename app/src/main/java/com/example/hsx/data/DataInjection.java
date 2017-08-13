package com.example.hsx.data;

import android.content.Context;

import com.example.hsx.data.DataRepository;
import com.example.hsx.data.remote.INetwork;
import com.example.hsx.data.remote.RemoteDataSource;
import com.example.hsx.data.remote.VolleyNetWork;

/**
 * Created by hsx on 17-8-7.
 */

public class DataInjection {
    public static DataRepository providerDataRepository (Context c) {
        INetwork network = VolleyNetWork.getInstance(c);
        return DataRepository.getInstance(RemoteDataSource.getInstance(network), null);
    }
}
