package com.zhhl.android.policemovecareful.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.DefaultConfig;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.activity.PendingCheckActivity;
import com.zhhl.android.policemovecareful.adapter.PendingAdapter;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yuan on 2018/8/6.
 * 待审批
 */

public class PendingFragment extends Fragment {
    private static final String TAG = "PendingFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    //声明控件
    @BindView(R.id.pending_fragment_listview)
    ListView listView;
    @BindView(R.id.noData)
    TextView noData;
    View view;
    PendingAdapter pendingAdapter;

    private ArrayList<QueryDataWrap> data = new ArrayList<>();

    private FragmentCallback callback;

    public void setCallback(FragmentCallback callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        view = inflater.inflate(R.layout.fragment_pengding, container, false);
        ButterKnife.bind(this, view);
        //初始化
        init();
        //监听
        initListener();
        return view;
    }


    /**
     * 控件监听
     */
    private void initListener() {


        //listview监听
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            if (!DefaultConfig.ALL) {
                QueryDataWrap data = pendingAdapter.getItem(i);
                //传给 PendingCheckActivity
                Intent intent = new Intent(getActivity(), PendingCheckActivity.class);
                intent.putExtra("type", data);
                startActivity(intent);
//            }
        });


    }


    private void showNoDataIfNeed(boolean b) {
        if (listView != null && noData != null) {
            listView.setVisibility(b ? View.GONE : View.VISIBLE);
            noData.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + isHidden());
        if (!hidden)
            callback.callback();
    }

    /**
     * 初始化
     */
    private void init() {
        pendingAdapter = new PendingAdapter(data);
        pendingAdapter.setData(data);
        showNoDataIfNeed(data.size() == 0);
        listView.setAdapter(pendingAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public void setData(ArrayList<QueryDataWrap> data) {
        this.data.clear();
        this.data.addAll(data);
        showNoDataIfNeed(data.size() == 0);
        if (pendingAdapter != null) pendingAdapter.setData(data);
    }

    public ArrayList<QueryDataWrap> getData() {
        return data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
