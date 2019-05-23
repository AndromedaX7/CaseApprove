package com.zhhl.android.policemovecareful.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhhl.android.policemovecareful.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miao on 2018/9/22.
 */
public class LogDetailsActivity extends AppCompatActivity {
    @BindView(R.id.mText)
    TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);
        ButterKnife.bind(this);
        String fileName = getIntent().getStringExtra("fileName");
        if (!TextUtils.isEmpty(fileName)) {
            try {
                FileInputStream f = new FileInputStream(fileName);
                byte[] b = new byte[f.available()];
                f.read(b);
                mText.setText(new String (b));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
