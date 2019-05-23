package com.zhhl.android.policemovecareful.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.BaseActivity;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;
import com.zhhl.android.policemovecareful.fragment.FragmentCallback;
import com.zhhl.android.policemovecareful.fragment.PendingFragment;
import com.zhhl.android.policemovecareful.fragment.PengdingTrueFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuan on 2018/8/6.
 * 受案审批
 */

public class PendingActivity extends BaseActivity implements FragmentCallback {
    @BindView(R.id.mCount)
    TextView mCount;
    @BindView(R.id.mSearch)
    ImageView mSearch;

    private TextView pengdingTv;
    private TextView pengdingTrueTv;
    private View pengdingView;
    private View pengdingTrueView;
    //fragment
    PendingFragment pendingFragment;
    PengdingTrueFragment pengdingTrueFragment;
    //fragment 集合
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    //tag
    int currentFragmentIndex = 0;
    int clickButtonIndex;
    //点击
    LinearLayout[] btnArray = new LinearLayout[2];
    ArrayList<QueryDataWrap> pendingApprovalData;
    ArrayList<QueryDataWrap> approvalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentLayout(R.layout.activity_pending);
            ButterKnife.bind(this);
            setTitle("审批", Color.WHITE);
            setBackArrow(R.mipmap.write_fanhui);
            pendingApprovalData = new ArrayList<>(getIntent().getParcelableArrayListExtra("pendingApprovalData"));
            approvalData = new ArrayList<>(getIntent().getParcelableArrayListExtra("approvalData"));

            initView();
            FragmentManager fragmentManager = getSupportFragmentManager();
            // transaction 事务
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            Fragment showFragment = fragmentList.get(currentFragmentIndex);
            transaction.add(R.id.pending_activity_fl, showFragment);
            transaction.show(showFragment);
            transaction.commit();

            Log.e("onCreate: ", "" + pendingApprovalData.size());
            if (pendingApprovalData.size() > 0) {
                mCount.setVisibility(View.VISIBLE);
            } else {
                mCount.setVisibility(View.GONE);
            }
            btnArray[0] = findViewById(R.id.pending_activity_ll);
            btnArray[1] = findViewById(R.id.pending_activity_true_ll);
            btnArray[currentFragmentIndex].setSelected(true);
            MyListener myListener = new MyListener();
            for (int i = 0; i < btnArray.length; i++) {
                btnArray[i].setOnClickListener(myListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        pengdingTv = findViewById(R.id.pending_activity_tv);
        pengdingTrueTv = findViewById(R.id.pending_activity_true_tv);

        pengdingView = findViewById(R.id.pending_activity_view);
        pengdingTrueView = findViewById(R.id.pending_activity_true_view);

        pendingFragment = new PendingFragment();
        pendingFragment.setCallback(this);
//        ArrayList<QueryData> data = ;

        pendingFragment.setData(pendingApprovalData);
        mCount.setText(String.valueOf(pendingApprovalData.size()));


        pengdingTrueFragment = new PengdingTrueFragment();
        pengdingTrueFragment.setData(approvalData);
        pengdingTrueFragment.setCallback(this);
        fragmentList.add(pendingFragment);
        fragmentList.add(pengdingTrueFragment);
        pengdingTv.setTextColor(Color.parseColor("#2E68EB"));
        pengdingView.setVisibility(View.VISIBLE);

    }

    /**
     * 改变文字颜色
     *
     * @param id
     */
    private void changeBtnIcon(int id) {
        pengdingView.setVisibility(View.GONE);
        pengdingTrueView.setVisibility(View.GONE);
        pengdingTv.setTextColor(Color.parseColor("#555555"));
        pengdingTrueTv.setTextColor(Color.parseColor("#555555"));
        if (id == R.id.pending_activity_ll) {
            pengdingTv.setTextColor(Color.parseColor("#2E68EB"));
            pengdingView.setVisibility(View.VISIBLE);
        } else if (id == R.id.pending_activity_true_ll) {
            pengdingTrueTv.setTextColor(Color.parseColor("#2E68EB"));
            pengdingTrueView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void callback() {

    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            changeBtnIcon(v.getId());
            try {
                switch (v.getId()) {
                    case R.id.pending_activity_ll:
                        clickButtonIndex = 0;
                        break;
                    case R.id.pending_activity_true_ll:
                        clickButtonIndex = 1;
                        break;

                }
                // 单击的不是当前的按钮
                if (currentFragmentIndex != clickButtonIndex) {
                    FragmentTransaction transaction = PendingActivity.this
                            .getSupportFragmentManager().beginTransaction();
                    transaction.hide(fragmentList.get(currentFragmentIndex));

                    Fragment showFragment = fragmentList.get(clickButtonIndex);
                    // 没有添加过，
                    if (!showFragment.isAdded()) {
                        transaction.add(R.id.pending_activity_fl, showFragment);
                    }
                    transaction.show(showFragment);
                    transaction.commit();
                    btnArray[currentFragmentIndex].setSelected(false);
                    btnArray[clickButtonIndex].setSelected(true);
                    currentFragmentIndex = clickButtonIndex;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @OnClick(R.id.mSearch)
    void onSearch() {
        ArrayList<QueryDataWrap> dataWraps;
        if (currentFragmentIndex == 0) {
            dataWraps = new ArrayList<>(pendingApprovalData);
        } else {
            dataWraps = new ArrayList<>(approvalData);
        }
        Intent intent = new Intent(this,CaseSearchActivity.class);
        intent.putExtra("flag",currentFragmentIndex);
        intent.putExtra("data",dataWraps);
        startActivity( intent );
        overridePendingTransition(R.anim.anim_main_enter,R.anim.anim_main_exit);
    }
}
