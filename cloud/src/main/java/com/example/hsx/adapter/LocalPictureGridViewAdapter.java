package com.example.hsx.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.hsx.data.DataInflator;
import com.example.hsx.data.IRecycle;
import com.example.hsx.data.models.BitmapInfo;
import com.example.hsx.data.models.PrivImages;
import com.example.hsx.myapplication.R;
import com.han.utils.HanLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsx on 17-9-20.
 */

public class LocalPictureGridViewAdapter extends BaseAdapter {
    private static List<Bitmap> mRecycleBitmap = null;
    private int PIC_MAX_SIZE = 400000; // unit K
    private Context mCtx = null;
    private int picSize = 0;
    private ImageView itemView = null;
    private static ArrayList<PrivImages> mImagesList = new ArrayList<PrivImages>();
    private IRecycle<Bitmap> mRecycle = null;
    private DataInflator.LocalPicInflactor mBitmapInflator = null;

    public LocalPictureGridViewAdapter(Context c, IRecycle<Bitmap> recycle, DataInflator.LocalPicInflactor inflator) {
        this.mCtx = c;
        mRecycleBitmap = new ArrayList<Bitmap>();
        mRecycle = recycle;
        mBitmapInflator = inflator;
    }

    @Override
    public int getCount() {
        return this.mBitmapInflator.getDataSize();
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

    private static int maxCellSize = 0;
    private static ImageView imgView = null;
    private static int maxPosition = 0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        BitmapInfo bmp = null;

        if (convertView == null) {
            imgView = (ImageView) LayoutInflater.from(mCtx).inflate(R.layout.local_picture_grid_item, parent, false);
            imgView.setBackgroundColor(0x89002202);
            v = imgView;
            maxCellSize ++;
            this.mBitmapInflator.setSize(maxCellSize);
        } else {
            v = convertView;
        }

        ((ImageView)v).setImageBitmap(null);

        if (0 == position && v.getHeight() == 0 && v.getWidth() == 0);
        else {
            bmp = this.mBitmapInflator.getData(v, position);
            if (bmp != null)
                ((ImageView)v).setImageBitmap(bmp.getBmp());

            HanLog.write("OWNCLOUD", " CellSize:" + maxCellSize + " pos:" + position + " w:" + v.getWidth() + " h:" + v.getHeight() + " cV:" + ((convertView == null)?" is null":"not null"));
        }

//        HanLog.write("OWNCLOUD", "adapter, postion:" + position + " maxCellsize:" + maxCellSize);
/*
        if (0 == last_position && position == 0)
            return convertView;

        last_position = position;


*//*        PrivMedia media = mImagesList.get(position);
        String path = media.getPath();*//*


        if (convertView == null) {
//            HanLog.write("OWNCLOUD", " LocalPictureGridViewAdapter.getView() convertView is null");
//            v = new ImageView(mCtx);//LayoutInflater.from(mCtx).inflate(R.layout.local_picture_view, null);
//            HanLog.write("OWNCLOUD", " position:" + position + " convertView is null");
            v = LayoutInflater.from(mCtx).inflate(R.layout.local_picture_grid_item, parent, false);
            imgView = (ImageView) v;
            imgView.setLayoutParams(new LinearLayout.LayoutParams(200, 180));
            HanLog.write("OWNCLOUD",  "[    null] getView() times: " + ++getViewTimes + " position:" + position + " img w:" + imgView.getWidth() + " img h:" + imgView.getHeight());

            do {
//                HanLog.write("OWNCLOUD", "[ null],position:" + position);

 *//*               if (mBitmapInflator != null)
                    mBitmapInflator.addView(v, position);*//*
            } while(false);

        } else {
//            HanLog.write("OWNCLOUD", " LocalPictureGridViewAdapter.getView() convertView isn't null");
//            HanLog.write("OWNCLOUD", "[ not null], position:" + position);
            v = convertView;
//            imgView = (ImageView) v;

            HanLog.write("OWNCLOUD",  "[Not null] getView() times: " + ++getViewTimes + " position:" + position + " img w:" + imgView.getWidth() + " img h:" + imgView.getHeight());
*//*            imgView = (ImageView) v;
            imgView.setImageBitmap(null);*//*

          *//*  if (mBitmapInflator != null)
                mBitmapInflator.replace(v, position);*//*

*//*            mBitMapF = (Bitmap) v.getTag(R.id.tag_fore);
            if (this.mRecycle != null)
                mRecycle.add(mBitMapF);

            mBitMapB = (Bitmap) v.getTag(R.id.tag_back);
            if (this.mRecycle != null)
                mRecycle.add(mBitMapB);*//*
        }
*//*

        // 控制bitmap 大小
        if (media.getSize() > PIC_MAX_SIZE) {
            sampleValue = (int) (Math.log(media.getSize()) / Math.log(2));
            BitmapFactory.Options picOption = new BitmapFactory.Options();
            picOption.inSampleSize = sampleValue;
            mBitMapF = BitmapFactory.decodeFile(path, picOption);
        } else {
            mBitMapF = BitmapFactory.decodeFile(path);
        }

        imgView = (ImageView) v;
        imgView.setTag(R.id.tag_fore, mBitMapF);
        imgView.setImageBitmap(mBitMapF);//

        //高斯渲染
        Bitmap sourceFg = Bitmap.createBitmap(mBitMapF);// mBitMap.copy(mBitMap.getConfig(), false);//Bitmap.createBitmap(mBitMap, 0, 0, mBitMap.getWidth()/2, mBitMap.getHeight()/2); //mBitMap.copy(mBitMap.getConfig(), false);
        Bitmap renderBg = sourceFg.copy(sourceFg.getConfig(), false);//Bitmap.createBitmap(sourceFg.getWidth(), sourceFg.getHeight(), sourceFg.getConfig());//sourceFg.getWidth(), sourceFg.getHeight(), sourceFg.getConfig());

*//*
*//*

        HanLog.write("OWNCLOUD", "addr1:" + mBitMap.isMutable() + " addr2:" + sourceFg.isMutable() + " addr3:" + renderBg.isMutable());
        HanLog.write("OWNCLOUD", "addr1:" + mBitMap + " addr2:" + sourceFg + " addr3:" + renderBg);
        HanLog.write("OWNCLOUD", "bitmap1:" + mBitMap.getGenerationId() + " bitmap2:" + sourceFg.getGenerationId() + " bitmap3:" + renderBg.getGenerationId());
*//**//*


        RenderScript rs = RenderScript.create(mCtx);
        ScriptIntrinsicBlur sib = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, sourceFg);
        Allocation tmpOut = Allocation.createFromBitmap(rs, renderBg);

        sib.setRadius(24.0f);
        sib.setInput(tmpIn);
        sib.forEach(tmpOut);

        tmpOut.copyTo(renderBg);
//        HanLog.write("OWNCLOUD", " compare bitmaps equals: " + mBitMap.equals(renderBg));
        imgView.setTag(R.id.tag_back, renderBg);
        imgView.setBackground(new BitmapDrawable(this.mCtx.getResources(), renderBg));
//        v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 400));
*/
        return v;
    }
}
