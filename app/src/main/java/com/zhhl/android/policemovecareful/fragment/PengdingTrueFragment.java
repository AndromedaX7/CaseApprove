package com.zhhl.android.policemovecareful.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.zhhl.android.policemovecareful.activity.ApprovalDetailsActivity;
import com.zhhl.android.policemovecareful.adapter.PendingAdapter;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * Created by yuan on 2018/8/6.
 * 已审批
 */

public class PengdingTrueFragment extends Fragment {
    private static final String TAG = "PublicClassFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private FragmentCallback callback;
    private ArrayList<QueryDataWrap> data = new ArrayList<>();

    public void setCallback(FragmentCallback callback) {
        this.callback = callback;
    }

    //声明控件
    PendingAdapter pendingAdapter;
    @BindView(R.id.pending_true_fragment_listview)
    ListView mListView;
    @BindView(R.id.noData)
    TextView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
//            Log.e(TAG, "onCreateView: ", );
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        View view = inflater.inflate(R.layout.fragment_pengding_true, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (pendingAdapter == null) pendingAdapter = new PendingAdapter(data);

        pendingAdapter.setFlag(true);

        showNoDataIfNeed(pendingAdapter.getCount() == 0);
        mListView.setAdapter(pendingAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + hidden);

        if (!hidden) callback.callback();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void setData(ArrayList<QueryDataWrap> data) {
        this.data.addAll(data);
        showNoDataIfNeed(data.size() == 0);

        if (pendingAdapter == null) {
            pendingAdapter = new PendingAdapter(data);
        } else {
            pendingAdapter.setData(data);
        }
    }

    private void showNoDataIfNeed(boolean b) {
        if (mListView != null && noData != null) {
            mListView.setVisibility(b ? View.GONE : View.VISIBLE);
            noData.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public ArrayList<QueryDataWrap> getData() {
        return data;
    }


    @OnItemClick(R.id.pending_true_fragment_listview)
    void onItemPressed(int index) {
//        if (!DefaultConfig.ALL) {
            Intent intent = new Intent(getContext(), ApprovalDetailsActivity.class);
            intent.putExtra("primaryKey", data.get(index));
            startActivity(intent);
//        }
    }
}
