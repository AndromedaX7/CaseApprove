package com.zhhl.android.policemovecareful.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhhl.android.policemovecareful.R;

/**
 * Created by miao on 2018/9/12.
 */
public class Pie extends View {

    private Paint paintColor;
    private Paint paint;
    private Paint paintBg;

    public Pie(Context context) {
        this(context, null);
    }

    public Pie(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pie(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Pie(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Pie);
        progress = typedArray.getInt(R.styleable.Pie_progress, 0);
        max = typedArray.getInt(R.styleable.Pie_max, 100);
        typedArray.recycle();
        init();
    }

    private int max;
    private int progress;
    private Rect bound;
    private Rect bound2;
    private Rect bound3 = new Rect();
    private Rect bound4 = new Rect();
    private Rect bound5 = new Rect();

    private Paint paintText;
    private Paint paintText2;

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#F67850"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((60));
        paintBg = new Paint();
        paintBg.setAntiAlias(true);
        paintBg.setColor(Color.parseColor("#cccccc"));
        paintBg.setStyle(Paint.Style.STROKE);
        paintBg.setStrokeWidth((60));
        bound = new Rect();
        bound2 = new Rect();
        paintText = new Paint();
        paintText.getTextBounds(k, 0, k.length(), bound);
        paintText.setAntiAlias(true);
        paintText.setColor(Color.parseColor("#111111"));
        paintText.setTextSize(sp2px(14));

        paintColor = new Paint();
        paintColor.setAntiAlias(true);
        paintColor.setColor(Color.parseColor("#F67850"));

        paintText2 = new Paint();
        paintText2.setAntiAlias(true);
        paintText2.setColor(Color.parseColor("#000000"));
        paintText2.setTextSize(sp2px(18));
        paintText2.getTextBounds(String.valueOf(max), 0, String.valueOf(max).length(), bound2);
        paintText.getTextBounds("立案审批(占比)", 0, "立案审批(占比)".length(), bound3);
        paintText2.getTextBounds(progress + "", 0, (progress + "").length(), bound4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) dp2px(100), (int) dp2px(50));
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(height * 2, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {

            setMeasuredDimension(width, width / 2);
        }
    }

    String k = "受理总量";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 255, 255, 255);
        int width = getWidth();
        int radius = width / 2 - 80;


        float angle = (float) (progress * 1.0 / 1.0 / max * 360);
        canvas.drawArc(40, 40, radius + 40, radius + 40, 270, angle, false, paint);
        canvas.drawArc(40, 40, radius + 40, radius + 40, (270 + angle) % 360, 360 - angle, false, paintBg);
        int b1height = bound.bottom - bound.top;
        int b2height = bound2.bottom - bound2.top;
        int b1width = bound.right - bound.left;
        int b2width = bound2.right - bound2.left;


        canvas.drawText(k, (radius / 2 - b1width / 2), 40 + radius / 2 - 20, paintText);
        canvas.drawText(max + "",   (radius / 2 - b2width / 2), 60 + radius / 2 + b2height, paintText2);


        ///////////////////////
        int height = getHeight();

        int cenLine = height / 2;


        canvas.drawRect(width / 2 + 100, cenLine - 60 - (bound4.bottom - bound4.top), width / 2 + 130, cenLine - 30 - (bound4.bottom - bound4.top), paintColor);
        canvas.drawText("立案审批数量", width / 2 + 140, cenLine - 30 - (bound4.bottom - bound4.top), paintText);
        canvas.drawText("" + progress, width / 2 + 140, cenLine - 20, paintText2);
        canvas.drawRect(width / 2 + 100, cenLine + 20, width / 2 + 130, cenLine + 50, paintColor);
        canvas.drawText("立案审批(占比)", width / 2 + 140, cenLine + 50, paintText);


        int res = ((int) (progress * 1.0 / max * 100));
        paintText2.getTextBounds(res + "%", 0, (res + "%").length(), bound5);
        canvas.drawText(res + "%", width / 2 + 140, cenLine + 60 + (bound5.bottom - bound5.top), paintText2);


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
            Pie.this.progress = (int) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();

    }

    public int getProgress() {
        return progress;
    }
}
