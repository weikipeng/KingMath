package com.pengjunwei.kingmath.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.pengjunwei.kingmath.R;

/**
 * Created by WikiPeng on 2017/3/11 15:02.
 */
public class PhotovoltaicView extends View {
    protected Paint mPaint;
    protected int   mWidth, mHeight;
    protected int mColorBackground;


    public PhotovoltaicView(Context context) {
        super(context);
        init();
    }

    public PhotovoltaicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotovoltaicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhotovoltaicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        mColorBackground = getResources().getColor(R.color.colorBackground);
        mPaint = new Paint();
        mPaint.setColor(mColorBackground);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();

        canvas.drawColor(mColorBackground);
    }
}
