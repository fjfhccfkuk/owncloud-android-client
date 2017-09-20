package com.example.hsx.presenter;

import com.example.hsx.data.DataRepository;
import com.example.hsx.data.DataSource;
import com.example.hsx.data.models.PrivMedia;
import com.example.hsx.ui.OwnText;

/**
 * Created by hsx on 17-8-7.
 */

public class OwnPicTextPresenter implements OwnText.Presenter{

    private OwnText.ViewText mTxt = null;
    private DataRepository mData = null;

    public OwnPicTextPresenter (OwnText.ViewText txt, DataRepository data) {
        this.mTxt = txt;
        this.mData = data;
    }

    @Override
    public void getPhotoName(int index) {

        if (mData != null) {
            mData.getPhoto(index, new DataSource.PhotoCallback() {
                @Override
                public void onSuccess(PrivMedia p) {
                    mTxt.showText(p.getName());
                }

                @Override
                public void onError(String s) {
                    mTxt.resetText(" Failed to get pic info.\n" + s);
                }
            });
        }

    }
}
