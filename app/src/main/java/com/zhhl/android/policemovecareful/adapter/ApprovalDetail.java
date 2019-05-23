package com.zhhl.android.policemovecareful.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by miao on 2018/9/18.
 */
public class ApprovalDetail extends BaseAdapter<String, ApprovalDetail.ImageViewHolder> {


    public ApprovalDetail(ArrayList<String> data) {
        super(data);
    }

    @Override
    protected ImageViewHolder create(View view, int itemViewType) {
        return new ImageViewHolder(view);
    }

    @Override
    protected void bindView(ImageViewHolder imageViewHolder, int position, String item) {
        Glide.with(imageViewHolder.approvalDetailImage).load(Uri.parse(item)).into(imageViewHolder.approvalDetailImage);
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.item_approval_detail;
    }

    public static class ImageViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.approval_detail_image)
        ImageView approvalDetailImage;

        public ImageViewHolder(View view) {
            super(view);
        }
    }
}
