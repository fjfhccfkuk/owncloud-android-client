/**
 * Created by hsx on 17-8-6.
 */

package com.example.hsx.data;

import com.example.hsx.data.models.OwnPicture;

public abstract class DataSource {

    public interface PhotoCallback {
        void onSuccess(OwnPicture p);
        void onError(String s);
    }

    public abstract void getPhoto(int x, PhotoCallback c);
}
