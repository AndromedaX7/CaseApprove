//package com.zhhl.android.policemovecareful.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.zhhl.android.policemovecareful.R;
//
//
///**
// * Created by yuan on 2017/7/5.
// * 圆形开关按钮
// */
//public class SlipButton extends View
//{
//    private boolean nowChoose = false;// 记录当前按钮是否打开,true为打开,flase为关闭
//
//    private boolean onSlip = false;// 记录是否在滑动
//
//    private float nowX;// 当前的x
//
//    private OnChangedListener changeListener;
//
//    private Bitmap bg_on, bg_off, slip_btn;
//
//    public SlipButton(Context context)
//    {
//        super(context);
//        init();
//    }
//
//    public SlipButton(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//        init();
//    }
//
//    public SlipButton(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//        init();
//    }
//
//    private void init()
//    {// 初始化
//
//        bg_on = BitmapFactory.decodeResource(getResources(), R.mipmap.my_split_left_1);
//        bg_off = BitmapFactory.decodeResource(getResources(), R.mipmap.my_split_right_1);
//        slip_btn = BitmapFactory.decodeResource(getResources(), R.mipmap.my_dian);
////        Btn_On = new Rect(0, 0, slip_btn.getWidth(), slip_btn.getHeight());
////        Btn_Off = new Rect(bg_off.getWidth() - slip_btn.getWidth(), 0, bg_off.getWidth(),
////                slip_btn.getHeight());
//
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        setMeasuredDimension(bg_off.getWidth(), bg_off.getHeight());
//        int width = measureDimension(bg_off.getWidth(), widthMeasureSpec);
//        int height = measureDimension(bg_off.getHeight(), heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }
//
//
//    public int measureDimension(int expriedSize, int measureSpec){
//        int result;
//
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        if(specMode == MeasureSpec.EXACTLY){
//            result = specSize;
//        }else{
//            result = expriedSize;   //UNSPECIFIED
//            if(specMode == MeasureSpec.AT_MOST){
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//
//        super.onDraw(canvas);
//
//        Matrix matrix = new Matrix();
//        Paint paint = new Paint();
//        float x; //滑动点到左边的距离
//
//        if (nowX < (bg_on.getWidth() / 2))// 滑动到前半段与后半段的背景不同,在此做判断
//        {
//            x = nowX - slip_btn.getWidth() / 2;
//            canvas.drawBitmap(bg_off, matrix, paint);// 画出关闭时的背景
//        }
//
//        else
//        {
//            x = bg_on.getWidth() - slip_btn.getWidth() / 2;
//            canvas.drawBitmap(bg_on, matrix, paint);// 画出打开时的背景
//        }
//
//        if (onSlip)// 是否是在滑动状态,
//
//        {
//            if (nowX >= bg_on.getWidth())// 是否划出指定范围,不能让游标跑到外头,必须做这个判断
//
//                x = bg_on.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...
//
//            else if (nowX < 0)
//            {
//                x = 0;
//            }
//            else
//            {
//                x = nowX - slip_btn.getWidth() / 2;
//            }
//        }
//        else
//        {// 非滑动状态
//
//            if (nowChoose)// 根据现在的开关状态设置画游标的位置
//            {
//                x = bg_off.getWidth() - slip_btn.getWidth();
//                canvas.drawBitmap(bg_on, matrix, paint);// 初始状态为true时应该画出打开状态图片
//            }
//            else
//            {
//				x = 0;
//				canvas.drawBitmap(bg_off, matrix, paint);
//			}
//        }
//
//        if (x < 0)// 对游标位置进行异常判断...
//            x = 0;
//        else if (x > bg_on.getWidth() - slip_btn.getWidth())
//            x = bg_on.getWidth() - slip_btn.getWidth();
//        canvas.drawBitmap(slip_btn, x, 0, paint);// 画出游标.
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        return super.onTouchEvent(event);
//        switch (event.getAction())
//        // 根据动作来执行代码
//
//        {
//            case MotionEvent.ACTION_MOVE:// 滑动
//                nowX = event.getX();
//                break;
//
//            case MotionEvent.ACTION_DOWN:// 按下
//
//                if (event.getX() > bg_on.getWidth() || event.getY() > bg_on.getHeight())
//                    return false;
//                onSlip = true;
//                nowX = event.getX();
//                break;
//
//            case MotionEvent.ACTION_CANCEL: // 移到控件外部
//
//                onSlip = false;
//                boolean choose = nowChoose;
//                if (nowX >= (bg_on.getWidth() / 2))
//                {
////                    nowX = bg_on.getWidth() - slip_btn.getWidth() / 2;
//                    nowChoose = true;
//                }
//                else
//                {
//                    nowX = nowX - slip_btn.getWidth() / 2;
//                    nowChoose = false;
//                }
//                if (changeListener!=null && (choose != nowChoose)) // 调用监听方法
//                    changeListener.onChanged(nowChoose);
//                break;
//            case MotionEvent.ACTION_UP:// 松开
//
//                onSlip = false;
//                boolean lastChoose = nowChoose;
//
//                if (event.getX() >= (bg_on.getWidth() / 2))
//                {
////                    nowX = bg_on.getWidth() - slip_btn.getWidth() / 2;
//                    nowChoose = true;
//                }
//
//                else
//                {
//                    nowX = nowX - slip_btn.getWidth() / 2;
//                    nowChoose = false;
//                }
//
//                if (changeListener!=null && (lastChoose != nowChoose)) // 调用监听方法
//                    changeListener.onChanged(nowChoose);
//                break;
//            default:
//        }
//        invalidate();// 重画控件
//        return true;
//    }
//
//
//
//    public void setOnChangedListener(OnChangedListener l)
//    {
//        changeListener = l;
//    }
//
//    public interface OnChangedListener
//    {
//        abstract void onChanged(boolean checkState);
//    }
//
//    public void setState(boolean isChecked)
//    {
//        nowChoose = isChecked;
//        invalidate();
//    }
//
//
//    public boolean getState()
//    {
//        return this.nowChoose;
//    }
//}