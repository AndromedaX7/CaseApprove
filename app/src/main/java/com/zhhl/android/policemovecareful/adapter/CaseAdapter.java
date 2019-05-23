package com.zhhl.android.policemovecareful.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.ConnectImpl;
import com.zhhl.android.policemovecareful.DateUtil;
import com.zhhl.android.policemovecareful.R;
import com.zhhl.android.policemovecareful.base.BaseAdapter;
import com.zhhl.android.policemovecareful.bean.CaseBasicInformation;
import com.zhhl.android.policemovecareful.bean.UserInfoWrapper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miao on 2018/9/11.
 */
public class CaseAdapter extends BaseAdapter<CaseBasicInformation, CaseAdapter.ViewHolder> {

    private ConnectImpl soapConnection = new ConnectImpl();

    private SparseArray<String> users = new SparseArray<>();

    public CaseAdapter(ArrayList<CaseBasicInformation> data) {
        super(data);

    }

    @Override
    protected ViewHolder create(View view, int itemViewType) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder viewHolder, int position, CaseBasicInformation item) {
        String admissibleTime = item.getAdmissibleTime();

        // todo 建议修改成线程池 提高效率
        if (!item.getAdmissibleTime().equals("0")) {
            viewHolder.mCaseTime.setText(DateUtil.format("yyyy-MM-dd ", DateUtil.parseDate("yyyyMMdd", admissibleTime)));
//        String.format(Locale.CHINA, "%s-%s-%s %s:%s:%s",
//                admissibleTime.substring(0, 4),
//                admissibleTime.substring(4, 6),
//                admissibleTime.substring(6, 8),
//                admissibleTime.substring(8, 10),
//                admissibleTime.substring(10, 12),
//                admissibleTime.substring(12, 14)
//        ));
        } else viewHolder.mCaseTime.setText(item.getAdmissibleTime());

        viewHolder.mCaseName.setText(item.getName());
        viewHolder.mCaseType.setText(item.getState());
        viewHolder.mPetitioner.setText(userInfoMap.get(item.getAdmissibler()) == null ? "" : userInfoMap.get(item.getAdmissibler()).toString());
//        if (users.get(position) == null)
//            new Thread(() -> {
//                ViewHolder vh = viewHolder;
//                int positionT = position;
//
//
//                        String userName;
//                StringBuilder sb = new StringBuilder();
//                for (UserInfo u :
//                        userInfos) {
//                    sb.append(u.getUserName()).append("  ");
//                }
//                userName = sb.toString();
//
//                users.put(positionT, userName);
//
//                ((Activity) context).runOnUiThread(() -> {
//                            vh.mPetitioner.setText(users.get(positionT));
//                            notifyDataSetChanged();
//                        }
//                );
//            }).start();
//        else {
////            ArrayList<UserInfo> userInfos = item.getUserInfo();
////            String userName;
////            StringBuilder sb = new StringBuilder();
////            for (UserInfo u :
////                    userInfos) {
////                sb.append(u.getUserName()).append("  ");
////            }
////            userName = sb.toString();
//
////            viewHolder.mPetitioner.setText(users.get(position));
//        }

    }

    public String getUsers(int pos) {
        return userInfoMap.get(data.get(pos).getAdmissibler()).toString();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.item_case_record;
    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {

        @BindView(R.id.mCaseName)
        TextView mCaseName;
        @BindView(R.id.mCaseType)
        TextView mCaseType;
        @BindView(R.id.mPetitioner)
        TextView mPetitioner;
        @BindView(R.id.mCaseTime)
        TextView mCaseTime;

        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public void addData(ArrayList<CaseBasicInformation> data) {
        super.addData(data);
        for (CaseBasicInformation b :
                data) {
            Observable.create(new ObservableOnSubscribe<UserInfoWrapper>() {
                @Override
                public void subscribe(ObservableEmitter<UserInfoWrapper> e) throws Exception {
                    UserInfoWrapper wrapper = new UserInfoWrapper();
                    wrapper.userInfos = soapConnection.queryUserInfo(b.getAdmissibler());
                    wrapper.uid = b.getAdmissibler();
                    e.onNext(wrapper);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::userInfo, this::onErr, this::onComplete)
                    .isDisposed();

        }
    }

    private void onComplete() {
    }

    private void onErr(Throwable throwable) {
    }

    private void userInfo(UserInfoWrapper userInfoWrapper) {
        userInfoMap.put(userInfoWrapper.uid, userInfoWrapper);
        notifyDataSetChanged();
    }

    private HashMap<String, UserInfoWrapper> userInfoMap = new HashMap<>();
}
