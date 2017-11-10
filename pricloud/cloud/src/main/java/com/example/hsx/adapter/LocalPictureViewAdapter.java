package com.example.hsx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsx.data.models.PrivImages;
import com.example.hsx.data.models.PrivMedia;
import com.example.hsx.myapplication.R;
import com.example.hsx.presenter.Presenter;
import com.han.utils.HanLog;

import java.util.ArrayList;

/**
 * Created by hsx on 17-9-20.
 */

public class LocalPictureViewAdapter extends BaseAdapter implements Presenter.PictureViewAdapter {
    private int PIC_MAX_SIZE = 400000; // unit K
    private Context mCtx = null;
    private int picSize = 0;
    private ImageView itemView = null;
    private static ArrayList<PrivImages> mImagesList = new ArrayList<PrivImages>();

    public LocalPictureViewAdapter(Context c) {
        this.mCtx = c;
    }

    @Override
    public int getCount() {
        return picSize;
    }

    @Override
    public Object getItem(int position) {
        HanLog.write("OWNCLOUD", "LocalPictureViewAdapter.getItem() ");
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static Bitmap mBitMap = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int sampleValue = 1;
        ImageView imgView = null;
        View v = null;
        PrivMedia media = mImagesList.get(position);
        TextView tvSize = null;
        TextView tvTime = null;
        TextView tvPath = null;
        String path = media.getPath();


        if (convertView == null) {
            v = LayoutInflater.from(mCtx).inflate(R.layout.local_picture_view, null);
        } else {
            v = convertView;
            mBitMap = (Bitmap) v.getTag();
            if (mBitMap != null && !mBitMap.isRecycled()) {
                mBitMap.recycle();
                mBitMap = null;
                System.gc();
            }
        }

        imgView = (ImageView) v.findViewById(R.id.img);
        tvPath = (TextView) v.findViewById(R.id.path);
        tvTime = (TextView) v.findViewById(R.id.time);
        tvSize = (TextView) v.findViewById(R.id.size);


        // 控制bitmap 大小
        if (media.getSize() > PIC_MAX_SIZE) {
            sampleValue = (int) (Math.log(media.getSize()) / Math.log(2));
            BitmapFactory.Options picOption = new BitmapFactory.Options();
            picOption.inSampleSize = sampleValue;
            mBitMap = BitmapFactory.decodeFile(path, picOption);
        } else {
            mBitMap = BitmapFactory.decodeFile(path);
        }

        imgView.setTag(mBitMap);
        imgView.setImageBitmap(mBitMap);//

        tvTime.setText(media.getCreatedTimeStamp() + "");
        tvPath.setText(media.getPath());
        tvSize.setText((media.getSize() / 1024) + "KB");

        //高斯渲染
        Bitmap sourceFg = Bitmap.createBitmap(mBitMap);// mBitMap.copy(mBitMap.getConfig(), false);//Bitmap.createBitmap(mBitMap, 0, 0, mBitMap.getWidth()/2, mBitMap.getHeight()/2); //mBitMap.copy(mBitMap.getConfig(), false);
        Bitmap renderBg = sourceFg.copy(sourceFg.getConfig(), false);//Bitmap.createBitmap(sourceFg.getWidth(), sourceFg.getHeight(), sourceFg.getConfig());//sourceFg.getWidth(), sourceFg.getHeight(), sourceFg.getConfig());

/*

        HanLog.write("OWNCLOUD", "addr1:" + mBitMap.isMutable() + " addr2:" + sourceFg.isMutable() + " addr3:" + renderBg.isMutable());
        HanLog.write("OWNCLOUD", "addr1:" + mBitMap + " addr2:" + sourceFg + " addr3:" + renderBg);
        HanLog.write("OWNCLOUD", "bitmap1:" + mBitMap.getGenerationId() + " bitmap2:" + sourceFg.getGenerationId() + " bitmap3:" + renderBg.getGenerationId());
*/

        RenderScript rs = RenderScript.create(mCtx);
        ScriptIntrinsicBlur sib = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, sourceFg);
        Allocation tmpOut = Allocation.createFromBitmap(rs, renderBg);

        sib.setRadius(24.0f);
        sib.setInput(tmpIn);
        sib.forEach(tmpOut);

        tmpOut.copyTo(renderBg);
//        HanLog.write("OWNCLOUD", " compare bitmaps equals: " + mBitMap.equals(renderBg));
        imgView.setBackground(new BitmapDrawable(this.mCtx.getResources(), renderBg));
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        return v;
    }

    @Override
    public void update(Cursor c) {
        mImagesList.clear();
        String path = null;
        int index = 0;
        ImageView v = null;

        int rowSize = c.getCount();
        for (int i = 0; i < rowSize; i ++) {
            c.moveToNext();

            PrivImages img = new PrivImages();
            mImagesList.add(img);
            generagePrivPicture(c, img);
        }

        picSize = mImagesList.size();
        notifyDataSetChanged();
        HanLog.write("OWNCLOUD", " LocalPictureViewAdapter.update() picSize:" + picSize);
    }

    private void generagePrivPicture(Cursor c, PrivMedia media) {
        int index = 0;
        String absolutePath = null;
        String tmpStr = null;
        int dataSize = 0;

        //data absolute path
        absolutePath = c.getString(c.getColumnIndex("_data"));
        media.setPath(absolutePath);

        //data size
        tmpStr = c.getString(c.getColumnIndex("_size"));
        dataSize = Integer.parseInt(tmpStr);
        media.setSize(dataSize);

        //data name "_display_name"
        tmpStr = c.getString(c.getColumnIndex("_display_name"));
        media.setName(tmpStr);

        //data create time "date_added"
        tmpStr = c.getString(c.getColumnIndex("date_added"));
        media.setCreatedTimeStamp(Integer.parseInt(tmpStr));

        //data type "bucket_display_name"
        tmpStr = c.getString(c.getColumnIndex("bucket_display_name"));
        media.setBelongTo(tmpStr);
    }
}
