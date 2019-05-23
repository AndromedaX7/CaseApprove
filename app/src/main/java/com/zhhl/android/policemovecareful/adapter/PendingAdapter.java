package com.zhhl.android.policemovecareful.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.BaseAdapter;
import com.zhhl.android.policemovecareful.bean.QueryData;
import com.zhhl.android.policemovecareful.bean.QueryDataWrap;

import java.util.ArrayList;

import butterknife.BindView;


public class PendingAdapter extends BaseAdapter<QueryDataWrap, PendingAdapter.cqViewHolder> {


    private boolean flag;

    public PendingAdapter(ArrayList<QueryDataWrap> data) {
        super(data);
    }

    @Override
    protected cqViewHolder create(View view, int itemViewType) {
        return new cqViewHolder(view);
    }

    @Override
    protected void bindView(cqViewHolder viewHolder, int position, QueryDataWrap item) {

        viewHolder.mRedPoint.setVisibility(flag ? View.INVISIBLE : View.VISIBLE);

        if (flag){
            if(!item.data.getTOPIC().contains("同意")){
                viewHolder.state .setText("已审批");
                viewHolder.state.setBackgroundColor(viewHolder.state.getResources().getColor(R.color.colorBlueTextBar));

            }else  if (item.data.getTOPIC().contains("不同意")){
                viewHolder.state .setText("不同意");
                viewHolder.state.setBackgroundColor(Color.parseColor("#F0EFF5"));
            }else {
                viewHolder.state .setText("同意");
                viewHolder.state.setBackgroundColor(viewHolder.state.getResources().getColor(R.color.colorBlueTextBar));
            }

        }
        viewHolder.state.setVisibility(flag ? View.VISIBLE : View.GONE);

        if (position==0){
            viewHolder.mRedPoint.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mRedPoint.setVisibility(View.INVISIBLE);
        }

        String content = item.data.getCONTENT();
        if (content.startsWith("你")) {
            String[] c = content.split("，");
            int start = c[1].indexOf("“");
            int end = c[1].lastIndexOf("”");
            if (item.data.getTOPIC().contains("呈请")) {
                int start_mTitle = item.data.getTOPIC().indexOf("呈");
                int end_mTitle = item.data.getTOPIC().lastIndexOf("（");
                viewHolder.mTitle.setText(item.data.getTOPIC().substring(start_mTitle, end_mTitle));
            }

            if (item.data.getTOPIC().contains("受案")) {
                int start_mTitle = item.data.getTOPIC().indexOf("受");
                int end_mTitle = item.data.getTOPIC().lastIndexOf("（");
                viewHolder.mTitle.setText(item.data.getTOPIC().substring(start_mTitle, end_mTitle));
            }

            viewHolder.mConetnt.setText(c[1].substring(start + 1, end));
            viewHolder.sendFrom.setText(item.data.getSENDFROM());
            viewHolder.mDate.setText(item.data.getSENDTODATE());
        } else {
            data.remove(item);
            notifyDataSetChanged();
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        notifyDataSetChanged();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.adapter_pending_cq_item;
    }

    @Override
    protected int[] getMultiLayout() {
        return new int[]{
                R.layout.adapter_pending_item, R.layout.adapter_pending_cq_item
        };
    }

    @Override
    protected int getAdapterViewTypeCount() {
        return 2;
    }

    public static class cqViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.mConetnt)
        TextView mConetnt;
        @BindView(R.id.sendFrom)
        TextView sendFrom;
        @BindView(R.id.mDate)
        TextView mDate;
        @BindView(R.id.mTitle)
        TextView mTitle;

        @BindView(R.id.red_point)
        TextView mRedPoint;
        @BindView(R.id.state)
        TextView state;

        public cqViewHolder(View view) {
            super(view);
        }
    }


//    @Override
//    protected int getAdapterItemViewType(int position) {
//
//        data.get(position).
//
//    }

    //为布局定义一个标识
//    private final int TYPE1 = 0;
//    private final int TYPE2 = 1;
//    public PendingAdapter(Context context, ArrayList<QueryData> data) {
//        super(context, data);
//    }
//    @Override
//    public int getItemViewType(int position) {
//        //获取当前布局的数据
//        Case aCase = getData().get(position);
//        //哪个字段不为空就说明这是哪个布局
//        //比如第一个布局只有item1_str这个字段，那么就判断这个字段是不是为空，
//        //如果不为空就表明这是第一个布局的数据
//        //根据字段是不是为空，判断当前应该加载的布局
//        if (TYPE1 ==aCase.getType()) {
//            return TYPE1;
//        } else {
//            return TYPE2;
//        }
//    }
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        ViewHolder viewHolder;
//        int type = getItemViewType(i);
//        if (view == null) {
//            switch (type) {
//                case TYPE1:
//    view =
//
//    getLayoutInflater().
//
//    inflate(R.layout.adapter_pending_item, null);
//                    viewHolder = new ViewHolder();
//
//                    view.setTag(viewHolder);
//                    break;
//                case TYPE2:
//    view =
//
//    getLayoutInflater().
//
//    inflate(R.layout.adapter_pending_cq_item, null);
//
//                    break;
//            }
//        } else {
//            switch (type) {
//                case TYPE1:
//                    viewHolder = (ViewHolder) view.getTag();
//                    break;
//                case TYPE2:
//
//                    break;
//            }
//        }
//
////        MakeBigMoney.Data data=getData().get(i);
////        viewHolder.titleTv.setText(data.getTitle());//项目名字
////        if ("".equals(data.getDudget())) {
////            viewHolder.priceTv.setText("面议");//项目价格
////        } else{
////            viewHolder.priceTv.setText("￥" + data.getDudget());//项目价格
////        }
////        //设置图片
////        if(!"".equals(data.getCoverImg())){
////            Picasso.with(getContext()).load(data.getCoverImg()).fit().into(viewHolder.picIv);
////        }
////        viewHolder.dateTv.setText(data.getCreatedDatetime());//项目日期
////        viewHolder.personTv.setText(data.getAuctionCount()+"人");//项目竞拍人数
//        return view;
//    }
//
//    public final class ViewHolder {
//    TextView pendingTv;
//    TextView priceTv;
//    TextView dateTv;
//    TextView personTv;
//    ImageView picIv;
//
//    }
}



