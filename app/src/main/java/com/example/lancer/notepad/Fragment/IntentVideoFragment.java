package com.example.lancer.notepad.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.IntentBean;
import com.example.lancer.notepad.bean.request_imp;
import com.example.lancer.notepad.util.LogUtils;
import com.example.lancer.notepad.util.NetUtil;
import com.example.lancer.notepad.util.ToastUtils;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class IntentVideoFragment extends BaseFragment {
    private static final String TAG = "IntentVideoFragment.class";
    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;
    private android.widget.ListView lvIntentvideo;
    private android.widget.ProgressBar pbIntentvideo;
    private TextView tvIntentvideo;

    @Override
    public void initData() {
        getDatafromServer();
    }

    private void getDatafromServer() {
        Retrofit retrofit = NetUtil.getRetrofit();
        request_imp request = retrofit.create(request_imp.class);
        request.getBSjie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntentBean>() {
                    @Override
                    public void onCompleted() {
                        pbIntentvideo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "访问网络失败");
                        tvIntentvideo.setVisibility(View.VISIBLE);
                        pbIntentvideo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(IntentBean intentBean) {
                        LogUtils.d(TAG, intentBean.getList().toString());
                    }
                });
    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.intent_video);
    }

    @Override
    public void initView(View view) {
        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
        lvIntentvideo = view.findViewById(R.id.lv_intentvideo);
        pbIntentvideo = view.findViewById(R.id.pb_intentvideo);
        tvIntentvideo = view.findViewById(R.id.tv_intentvideo);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_my;
    }
}
