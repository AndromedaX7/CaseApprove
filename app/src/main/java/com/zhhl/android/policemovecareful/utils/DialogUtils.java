package com.zhhl.android.policemovecareful.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.zhhl.android.policemovecareful.R;

/**
 * Created by miao on 2018/9/22.
 */
public class DialogUtils {
    public static AlertDialog showSuccess(View view, CallbackDialog callback) {

        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setView(R.layout.dialog_success)
                .setCancelable(true)
                .create();
        dialog.show();
        view.postDelayed(() -> {
            dialog.dismiss();
            callback.callback();
        }, 3000);
        return dialog;
    }

    public static AlertDialog showFailed(Context context, final CallbackDialog dialogCallback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage("提交失败")
                .setPositiveButton("重试", (dialog1, which) -> dialogCallback.callback())
                .setNegativeButton("取消", (dialog12, which) -> dialog12.dismiss()).create();
        dialog.show();
        return dialog;
    }

    public static ProgressDialog showProgressDialog(Context c) {
        return showProgressDialog(c, "正在提交,请稍后...");
    }

    public static ProgressDialog showProgressDialog(Context context, String content) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(content);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
}
