package com.example.hsx.ui.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.hsx.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsx on 17-8-26.
 */

public class PointBtn extends View implements IPointBtn.Forcus, View.OnClickListener{
    private static List<IPointBtn.Forcus> mForcusList = new ArrayList<IPointBtn.Forcus>();
    private static synchronized void ProcessForcusChange (IPointBtn.Forcus m) {
        for (IPointBtn.Forcus f : mForcusList) {
            if (m == f)
                f.forcusOn();
            else
                f.forcusOff();
        }
    }

    private static synchronized void AddForcus(IPointBtn.Forcus f) {
        if (mForcusList.size() <= 0)
            f.forcusOn();
        mForcusList.add(f);
    }

    private int padTop = 0;
    private int padRight = 0;
    private int padBottom = 0;
    private int padLeft = 0;
    private int forcusOffColor = Color.GRAY;
    private int forcusOnColor = 0x89909900;
    private int vHeight = 0;
    private int vWidth = 0;
    private Drawable mDrawableForcusOn = null;
    private Drawable mDrawableForcusOff = null;
    private Drawable mDrawableCur = null;
    private AttributeSet mAtts = null;
    private Context mCtx = null;
    private Rect mDrawRect = null;
    private Paint mBackColorPaint = null;
    private int minSize = 0;
    private IPointBtn.Change mCallbackListener = null;

    public PointBtn(Context c, AttributeSet attr) {
        super(c, attr);

        this.mCtx = c;
        this.mAtts = attr;

//        BusLog.write("OWNCLOUD", " PointBtn(2)");
        initDeclaredAttrs(this.mCtx.obtainStyledAttributes(this.mAtts, R.styleable.PointBtn));
        mBackColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.mDrawableCur = this.mDrawableForcusOff;
        AddForcus(this);

        this.setOnClickListener(this);
    }

    public void setCallbackListener (IPointBtn.Change l) {
        if (l == null)
            return;

        mCallbackListener = l;
    }

    private void initDeclaredAttrs(TypedArray a) {
        int count = a.getIndexCount();

        if (count <= 0)
            return;

        int i = 0;
        for (i = 0; i < count; i ++) {
            int index = a.getIndex(i);
//            BusLog.write("OWNCLOUD", "count:" + count + " index:" + index + " i:" + i);

            switch (index) {
                case R.styleable.PointBtn_forcusOffDraw:
                    this.mDrawableForcusOff = a.getDrawable(index);
                    break;
                case R.styleable.PointBtn_forcusOnDraw:
                    this.mDrawableForcusOn = a.getDrawable(index);
                    break;
                case R.styleable.PointBtn_pad_bottom:
                    this.padBottom = a.getDimensionPixelSize(index, this.padBottom);
                    break;
                case R.styleable.PointBtn_pad_right:
                    this.padRight = a.getDimensionPixelSize(index, this.padRight);
                    break;
                case R.styleable.PointBtn_pad_top:
                    this.padTop = a.getDimensionPixelSize(index, this.padTop);
                    break;
                case R.styleable.PointBtn_pad_left:
                    this.padLeft = a.getDimensionPixelSize(index, this.padLeft);
                    break;
                default:
                    break;
            }
        }

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension(minSize, minSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.vHeight = h;
        this.vWidth = w;

        compulateDrawRect();
    }

    private void compulateDrawRect() {
        if (mDrawRect == null)
            mDrawRect = new Rect();

        minSize = Math.min(getWidth(), getHeight() - this.padTop);

        mDrawRect.top = (getHeight() - minSize)/2 + this.padTop / 2;
        mDrawRect.bottom = mDrawRect.top + minSize;

        mDrawRect.left = (getWidth() - minSize) / 2;
        mDrawRect.right = mDrawRect.left + minSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawColor(this.forcusOnColor);

        if (this.mDrawableCur == null)
            return;

//        mBackColorPaint.setColor(Color.RED);
//        mBackColorPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle((mDrawRect.left + mDrawRect.right) / 2, ((mDrawRect.top + mDrawRect.bottom) / 2), minSize / 2, mBackColorPaint);

//        mBackColorPaint.setShader(new BitmapShader(BitmapFactory.decodeFile("/sdcard/moon.png"), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        canvas.drawCircle((mDrawRect.left + mDrawRect.right) / 2, ((mDrawRect.top + mDrawRect.bottom) / 2), minSize/2, mBackColorPaint);

        this.mDrawableCur.setBounds(mDrawRect);
        this.mDrawableCur.draw(canvas);
    }

    @Override
    public void forcusOn() {
        this.mDrawableCur = this.mDrawableForcusOn;

        if (mCallbackListener != null) {
            mCallbackListener.onShow(this);
        }

        postInvalidate();
    }

    @Override
    public void forcusOff() {
        this.mDrawableCur = this.mDrawableForcusOff;

        if (mCallbackListener != null) {
            mCallbackListener.onDismiss(this);
        }

        postInvalidate();
    }

    @Override
    public void onClick(View v) {
        ProcessForcusChange(this);
    }
}
