package com.zhhl.android.policemovecareful.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.zhhl.android.policemovecareful.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * Created by miao on 2018/9/22.
 */
public class LogActivity extends AppCompatActivity {
    @BindView(R.id.mList)
    ListView mList;
    private LogAdapter ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        File filesDir = getFilesDir();
        File[] files = filesDir.listFiles();

        ArrayList<File> files_ = new ArrayList<>();
        Collections.addAll(files_, files);
        ad = new LogAdapter(files_);
        mList.setAdapter(ad);
    }

    @OnItemClick(R.id.mList)
    void onItemClick(int i) {
        String absolutePath = ad.getData().get(i).getAbsolutePath();
        Intent intent = new Intent(this, LogDetailsActivity.class);
        intent.putExtra("fileName", absolutePath);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.mList)
    boolean onItemLongClick(int i) {
        ad.getData().remove(i).delete();
        ad.notifyDataSetChanged();
        return true;
    }
}
