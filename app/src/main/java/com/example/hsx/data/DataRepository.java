package com.example.hsx.data;

import com.example.hsx.data.remote.INetwork;
import com.example.hsx.data.remote.RemoteDataSource;
import com.example.hsx.data.remote.VolleyNetWork;

/**
 * Created by hsx on 17-8-7.
 */

public class DataRepository {

    private DataSource.PhotoCallback mCb = null;
    private DataSource mRemoteSource = null;
    private DataSource mLocalSource = null;

    private static DataRepository mRespository = null;

    private DataRepository(DataSource remoteSource, DataSource localSource){
        this.mRemoteSource = remoteSource;
        this.mLocalSource = localSource;
    }

    public static DataRepository getInstance(DataSource remoteSource, DataSource localSource) {
        if (mRespository == null)
            mRespository = new DataRepository(remoteSource, localSource);

        return mRespository;
    }

    public void getPhoto(int index, DataSource.PhotoCallback cb) {
        mCb = cb;

        if (mRemoteSource != null) {
            mRemoteSource.getPhoto(index, cb);
        }
    }
}
