package com.cw.bullseyeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lostman on 16-9-25.
 * 1. onMeasure -> onSizeChange -> onDraw
 * 2. onMeasure -> onDraw
 */
public class BullsEyeview extends View {
    private Paint mPaint;
    private Point mCenter;
    private float mRadius;

    /*
    * Java constructor
     */
    public BullsEyeview(Context context) {
        this(context, null);
    }

    /*
    * XML constructor
     */
    public BullsEyeview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /*
    * style XML constructor
     */
    public BullsEyeview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mCenter = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;
        int contentWidth = 100;
        int contentHeight = 100;

        width = getMeasurement(widthMeasureSpec, contentWidth);
        height = getMeasurement(heightMeasureSpec, contentHeight);

        Log.i("cwcw", "width : " + width + ", widthMeasureSpec : " + widthMeasureSpec +
             ", heigh : " + height + ", heightMeasureSpec : " + heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getMeasurement(int measureSpec, int contentSize) {
        int specSize = MeasureSpec.getSize(measureSpec);
        switch(MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.AT_MOST:
                Log.i("cwcw", "mode : AT_MOST" + ", specSize : " + specSize + ", contentSize : " + contentSize);
                return Math.min(specSize, contentSize);
            case MeasureSpec.UNSPECIFIED:
                Log.i("cwcw", "mode : UNSPECIFIED");
                return contentSize;
            case MeasureSpec.EXACTLY:
                Log.i("cwcw", "mode : EXACTLY");
                return specSize;
            default:
                return 0;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("cwcw", "w : " + w + ", h : " + h + ", oldw : " + oldw + ", oldh : " + oldh);
        if (w != oldh || h != oldh) {
            mCenter.x = w/2;
            mCenter.y = h/2;

            mRadius = Math.min(mCenter.x, mCenter.y);
            Log.i("cwcw", "mCenter.x : " + mCenter.x +
                    ", mCenter.y : " + mCenter.y + ", mRadius : " + mRadius);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("cwcw", "mCenter.x : " + mCenter.x + ", mCenter.x : " + mCenter.y + ", mRadius : " + mRadius);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.8f, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.6f, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.4f, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.1f, mPaint);
    }
}
