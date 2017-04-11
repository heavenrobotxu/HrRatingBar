package com.hr.xz.hrratingbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hr.xz.hrratingbar.R;

/**
 * Created by zhaoxu on 2017/3/22.
 */

public class HrRatingBar extends View {

    private static final int DEFAULT_TOP_BO_PADDING = 50;

    // 要显示的星星总数
    private int mStarTotalNum = 5;
    // 选中的星星总数
    private float mStarSelectedNum = 5f;
    // 星星图标资源ID
    private int mStarSelectedId, mStarDefaultId, mStarHalfId;
    // 星星图标bitmap
    private Bitmap mStarDefaultBmp, mStarSelectedBmp, mStarHalfBitmap;
    // 是否仅做展示使用
    private boolean isIndicator = false;
    // 是否显示半颗星
    private boolean isShowHalf = false;
    // 星星之间间隔
    private float mStarMargin;
    // 星星高
    private int mStarHeight;
    // 星星宽
    private int mStarWidth;
    // 单个星星占有的总宽度
    private int mItemWidth;

    private OnRatingChangListener mRatingChangeListener;

    private int mViewWidth;
    private int mViewHeight;

    private Paint mPaint = new Paint();

    public HrRatingBar(Context context) {
        super(context);
        init();
    }

    public HrRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HrRatingBar);
        mStarTotalNum = typedArray.getInteger(R.styleable.HrRatingBar_starTotalNum, 5);
        mStarSelectedNum = typedArray.getFloat(R.styleable.HrRatingBar_starSelectedNum, 5f);
        mStarDefaultId = typedArray.getResourceId(R.styleable.HrRatingBar_starDefaultDrawable,
                R.mipmap.star_default);
        mStarSelectedId = typedArray.getResourceId(R.styleable.HrRatingBar_starSelectedDrawable,
                R.mipmap.star_selected);
        mStarHalfId = typedArray.getResourceId(R.styleable.HrRatingBar_starHalfDrawable,
                R.mipmap.star_half);
        isIndicator = typedArray.getBoolean(R.styleable.HrRatingBar_isIndicator, false);
        isShowHalf = typedArray.getBoolean(R.styleable.HrRatingBar_isShowHalf, false);
        mStarMargin = typedArray.getDimension(R.styleable.HrRatingBar_starMargin,
                getResources().getDimension(R.dimen.star_default_margin));
        mStarWidth = (int)typedArray.getDimension(R.styleable.HrRatingBar_starWidth, 0);
        mStarHeight = (int)typedArray.getDimension(R.styleable.HrRatingBar_starHeight, 0);
        typedArray.recycle();
        init();
    }

    private void init(){
        mPaint.setAntiAlias(true);
        mStarDefaultBmp = BitmapFactory.decodeResource(getResources(), mStarDefaultId);
        mStarSelectedBmp = BitmapFactory.decodeResource(getResources(), mStarSelectedId);
        mStarHalfBitmap = BitmapFactory.decodeResource(getResources(), mStarHalfId);
        if (mStarWidth == 0)
        mStarWidth = mStarDefaultBmp.getWidth();
        if (mStarHeight == 0)
        mStarHeight = mStarDefaultBmp.getHeight();
        mStarDefaultBmp = Bitmap.createScaledBitmap(mStarDefaultBmp, mStarWidth, mStarHeight,
                false);
        mStarSelectedBmp = Bitmap.createScaledBitmap(mStarSelectedBmp, mStarWidth, mStarHeight,
                false);
        mStarHalfBitmap = Bitmap.createScaledBitmap(mStarHalfBitmap, mStarWidth, mStarHeight,
                false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.AT_MOST) {
            width = (int)(mStarWidth + mStarMargin) * mStarTotalNum;
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = mStarHeight + DEFAULT_TOP_BO_PADDING * 2;
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
        mItemWidth = mViewWidth / mStarTotalNum;

        for (int i = 0; i < mStarTotalNum; i++) {
            int left = i * mItemWidth + mItemWidth / 2 - mStarWidth / 2;
            int top = mViewHeight / 2 - mStarHeight / 2;
            if (i < mStarSelectedNum) {
                if (i == (int)mStarSelectedNum) {
                    if (isShowHalf)
                        canvas.drawBitmap(mStarHalfBitmap, left, top, mPaint);
                    else
                        canvas.drawBitmap(mStarDefaultBmp, left, top, mPaint);
                } else {
                    canvas.drawBitmap(mStarSelectedBmp, left, top, mPaint);
                }
            } else {
                canvas.drawBitmap(mStarDefaultBmp, left, top, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isIndicator) return false;
        float stepSize;
        if (isShowHalf)
            stepSize = 0.5f;
        else
            stepSize = 1f;
        float x,y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                for (float i = 0; i < mStarTotalNum; i+=stepSize) {
                    if (x >= i * mItemWidth && x <= (i + stepSize) * mItemWidth) {
                        if (i == 0 && x <= (i + stepSize) * mItemWidth / 4)
                            mStarSelectedNum = 0;
                        else
                            mStarSelectedNum = i + stepSize;
                        if (mRatingChangeListener != null) {
                            mRatingChangeListener.onRatingChange(mStarSelectedNum);
                        }
                        invalidate();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public float getStarSelectedNum() {
        return mStarSelectedNum;
    }

    public void setStarSelectedNum(float mStarSelectedNum) {
        this.mStarSelectedNum = mStarSelectedNum;
        invalidate();
    }

    public void setRatingChangeListener(OnRatingChangListener mRatingChangeListener) {
        this.mRatingChangeListener = mRatingChangeListener;
    }

    public void setIndicator(boolean indicator) {
        isIndicator = indicator;
    }

    public void setShowHalf(boolean showHalf) {
        isShowHalf = showHalf;
    }

    public interface OnRatingChangListener {
        void onRatingChange(float rating);
    }
}
