package com.zhhl.android.policemovecareful.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.adapter.PendingAdapter;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by miao on 2018/9/22.
 */
public class HistoryApprovalActivity extends WaterActivity {
    @BindView(R.id.pending_true_fragment_listview)
    ListView pendingTrueFragmentListview;
    @BindView(R.id.noData)
    TextView noData;
    private PendingAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pengding_true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        adapter = new PendingAdapter(new ArrayList<>());
        pendingTrueFragmentListview.setAdapter(adapter);
        ArrayList<QueryDataWrap> approvalData = getIntent().getParcelableArrayListExtra("approvalData");
        if (approvalData != null) {
            adapter.setData(approvalData);
        }
        showNoDataIfNeed(adapter.getCount() == 0);
    }

    private void showNoDataIfNeed(boolean b) {
        if (pendingTrueFragmentListview != null && noData != null) {
            pendingTrueFragmentListview.setVisibility(b ? View.GONE : View.VISIBLE);
            noData.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    @OnItemClick(R.id.pending_true_fragment_listview)
    void onItemClick(int idx) {
//        Intent intent = new Intent(this, ApprovalDetailsActivity.class);
//        intent.putExtra("primaryKey", adapter.getData().get(idx));
//        startActivity(intent);
        ApprovalDetailsActivity.startCurrent(this, adapter.getData().get(idx));
    }


    public static void startCurrent(Context context, ArrayList<QueryData> value) {
        Intent intent = new Intent(context, HistoryApprovalActivity.class);
        intent.putParcelableArrayListExtra("approvalData", value);
        context.startActivity(intent);

    }
}
