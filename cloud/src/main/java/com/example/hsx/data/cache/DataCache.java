import android.graphics.Bitmap;

import com.example.hsx.data.models.BitmapInfo;

/**
 * Created by hz on 17-11-6.
 */

public class DataCache implements IAppCache<BitmapInfo, String>, IDisakCache<String, Bitmap> {

    @Override
    public BitmapInfo getData(String p1) {
        return null;
    }

    @Override
    public void setData(String s, Bitmap bitmap) {

    }
}
