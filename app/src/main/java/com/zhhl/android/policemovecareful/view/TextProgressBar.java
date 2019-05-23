package com.zhhl.android.policemovecareful.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhhl.android.policemovecareful.R;

/**
 * Created by miao on 2018/9/11.
 */
public class TextProgressBar extends View {

    private int max;
    private int progress;
    private Paint paint;
    private Paint paintBg;
    private Paint paintText;

    public TextProgressBar(Context context) {
        this(context, null);
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextProgressBar);
        progress = typedArray.getInt(R.styleable.TextProgressBar_progress, 0);
        max = typedArray.getInt(R.styleable.TextProgressBar_max, 100);
        typedArray.recycle();
        init();
        setProgress(0);
        setMax(1);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.colorBlueTextBar));
        paint.setAntiAlias(true);
        paintBg = new Paint();
        paintBg.setColor(getContext().getResources().getColor(R.color.colorProgressBg));
        paintBg.setAntiAlias(true);
        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextSize(sp2px(18));
        paintText.setColor(getContext().getResources().getColor(R.color.colorTextDefault));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) dp2px(100), (int) dp2px(20));
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) dp2px(100), height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, (int) dp2px(20));
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        double progressDouble = (getProgress() * 1.0) / (getMax() * 1.0);
        int progressWidth = (int) (progressDouble * getWidth());
        Rect rect = new Rect(0, 0, width, getHeight());
        Rect rectProgress = new Rect(0, 0, progressWidth, getHeight());


        canvas.drawRect(rect, paintBg);
        canvas.drawRect(rectProgress, paint);
        canvas.drawText(String.valueOf(progress), progressWidth, getHeight() - 10, paintText);

    }

    private float dp2px(int dp) {
        return getContext().getResources().getDisplayMetrics().density * dp + 0.5f;
    }

    private float sp2px(int sp) {
        return getContext().getResources().getDisplayMetrics().scaledDensity * sp + 0.5f;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            TextProgressBar.this.progress = (int) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    public int getProgress() {
        return progress;
    }
}
