package com.zhhl.android.policemovecareful.adapter;

import android.view.View;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.base.BaseAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by miao on 2018/9/22.
 */
public class LogAdapter extends BaseAdapter<File,LogAdapter.LogViewHolder> {


    public LogAdapter(ArrayList<File> data) {
        super(data);
    }

    @Override
    protected LogViewHolder create(View view, int itemViewType) {
        return new LogViewHolder(view);
    }

    @Override
    protected void bindView(LogViewHolder logViewHolder, int position, File item) {
        logViewHolder.textView.setText(item.getName());
    }

    @Override
    protected int getDefaultLayout() {
        return android.R.layout.simple_list_item_1;
    }

    public  static  class  LogViewHolder extends BaseAdapter.ViewHolder{

        @BindView(android.R.id.text1)
        TextView textView ;
        public LogViewHolder(View view) {
            super(view);
        }
    }
}
