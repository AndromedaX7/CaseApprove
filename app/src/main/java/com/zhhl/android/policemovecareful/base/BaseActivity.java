package com.zhhl.android.policemovecareful.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.activity.WaterActivity;


/**
 *  Created by yuan on 2017/5/19.
 * 自定义头部
 */
public class BaseActivity extends WaterActivity {
    /**
     * 通用的ToolBar标题
     */
    private TextView commonTitleTv;
    /**
     * 通用的ToolBar保存文字
     */
    private TextView commonSaveTv;
    /**
     * 通用的ToolBar
     */
    private Toolbar commonTitleTb;
    /**
     * 内容区域
     */
    private RelativeLayout content;
    /**
     * 通用图标
     */
    ImageView imageView;

    BaseActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setSupportActionBar(commonTitleTb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        commonTitleTv = findViewById(R.id.base_activity_common_title_tv);
        commonTitleTb = findViewById(R.id.base_activity_common_title_tb);
        commonSaveTv = findViewById(R.id.base_activity_common_save_tv);
        imageView = findViewById(R.id.base_activity_common_image_iv);
        content = findViewById(R.id.base_activity_content);
        baseActivity=this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    /**
     * 子类调用，重新设置Toolbar
     *
     * @param layout
     */
    public void setToolBar(int layout) {
        hidetoolBar();
        commonTitleTb = content.findViewById(layout);
        setSupportActionBar(commonTitleTb);
        //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE。
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 隐藏ToolBar，通过setToolBar重新定制ToolBar
     */
    public void hidetoolBar() {
        commonTitleTb.setVisibility(View.GONE);
    }

    /**
     * menu的点击事件
     *
     * @param onclick
     */
    public void setToolBarMenuOnclick(Toolbar.OnMenuItemClickListener onclick) {
        commonTitleTb.setOnMenuItemClickListener(onclick);
    }

    /**
     * 设置右边文字
     * @param mtext
     */
    public void setSaveText(String mtext){
        commonSaveTv.setText(mtext);
    }
    /**
     * 设置右边图片
     * @param id
     */
    public void setImageViewIv(int id){
        imageView.setImageResource(id);
        imageView.setVisibility(View.VISIBLE);

    }

    public void setIvOnclick(View.OnClickListener onclick){
        imageView.setOnClickListener(onclick);
    }

    /**
     * 设置右边背景
     * @param color
     */
    public void setSaveTextBackgound(int color){
        commonSaveTv.setBackgroundColor(color);
    }
    /**
     * Save监听事件
     *
     * @param onclick
     */
    public void setSaveOnclick(View.OnClickListener onclick) {
        commonSaveTv.setOnClickListener(onclick);
    }
    /**
     * 设置背景颜色
     */
    public void setBackground(int color){
        commonTitleTb.setBackgroundColor(color);
    }

    /**
     * 设置左上角back按钮
     */
    public void setBackArrow(int id) {
        final Drawable upArrow = getResources().getDrawable(id);
        //给ToolBar设置左侧的图标
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回按钮的点击事件
        commonTitleTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_main_enter,R.anim.anim_main_exit);
            }
        });
    }
    /**
     * 设置右边菜单按钮
     */
    public void setMenu(int id){
        final Drawable upArrow = getResources().getDrawable(id);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * 设置toolbar下面内容区域的内容
     *
     * @param layoutId
     */
    public void setContentLayout(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        content.addView(contentView, params);
    }

    /**
     * 设置标题
     *
     * @param title
     * @param color
     */
    public void setTitle(String title, int color) {
        if (!TextUtils.isEmpty(title)) {
            commonTitleTv.setText(title);
            commonTitleTv.setTextColor(color);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        commonTitleTv.setText(resId);
    }

    /**
     * Toast
     * @param s
     */
    public void mToast(String s){
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 头部隐藏
     *
     */
    public void setmVisibility(boolean isVisibility){
        if(isVisibility) {
            commonTitleTb.setVisibility(View.VISIBLE);
        }else {
            commonTitleTb.setVisibility(View.GONE);
        }
    }
}