package com.zhhl.android.policemovecareful.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.adapter.PendingAdapter;
import com.zhhl.android.policemovecareful.base.BaseActivity;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class CaseSearchActivity extends BaseActivity {


    @BindView(R.id.mSearchEditor)
    EditText mSearchEditor;
    @BindView(R.id.mList)
    ListView mList;
    private ArrayList<QueryDataWrap> data = new ArrayList<>();
    private ArrayList<QueryDataWrap> search = new ArrayList<>();
    private int flag;
    private PendingAdapter adapter = new PendingAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_search_case);
        ButterKnife.bind(this);
        setTitle("审批", Color.WHITE);
        setBackArrow(R.mipmap.write_fanhui);
        mList.setAdapter(adapter);
        adapter.setFlag(flag!=0);
        flag = getIntent().getIntExtra("flag", 0);
        data = getIntent().getParcelableArrayListExtra("data");
        adapter.setData(data);
        mSearchEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search.clear();
                if (s.toString().equals("")) {
                    search.addAll(data);
                } else {
                    for (QueryDataWrap wrap : data) {
                        if (wrap.data.getTOPIC().contains(s.toString())) {
                            search.add(wrap);
                        }
                    }
                }
                adapter.setData(search);


            }
        });
    }

    @OnItemClick(R.id.mList)
    void onItemClick(int idx) {
        if (flag==0){
            QueryDataWrap data = adapter.getItem(idx);
            //传给 PendingCheckActivity
            Intent intent = new Intent(this, PendingCheckActivity.class);
            intent.putExtra("type", data);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, ApprovalDetailsActivity.class);
            intent.putExtra("primaryKey", data.get(idx));
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_main_enter,R.anim.anim_main_exit);
    }
}
