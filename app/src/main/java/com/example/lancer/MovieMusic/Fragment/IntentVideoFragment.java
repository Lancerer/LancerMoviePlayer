package com.example.lancer.MovieMusic.Fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.MovieMusic.R;
import com.example.lancer.MovieMusic.adapter.IntentVideoAdapter;
import com.example.lancer.MovieMusic.bean.DouYinBean;
import com.example.lancer.MovieMusic.bean.IntentBean;
import com.example.lancer.MovieMusic.bean.request_imp;
import com.example.lancer.MovieMusic.util.LogUtils;
import com.example.lancer.MovieMusic.util.NetUtil;
import com.example.lancer.MovieMusic.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class IntentVideoFragment extends BaseFragment {
    private static final String TAG = "IntentVideoFragment.class";
    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;
    private android.widget.ProgressBar pbIntentvideo;
    private TextView tvIntentvideo;
    private android.support.v7.widget.RecyclerView recycle;

    private int page = 1;
    private int total = 20;
    private List<DouYinBean.ResultsBean> mResults=new ArrayList<>();

    @Override
    public void initData() {
        NetUtil.getGank().create(request_imp.class)
                .getDouYin(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DouYinBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(getContext(),e.toString(),Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onNext(DouYinBean douYinBean) {
                        pbIntentvideo.setVisibility(View.GONE);
                        mResults = douYinBean.getResults();
                        IntentVideoAdapter adapter = new IntentVideoAdapter(mResults, getContext());
                        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
                        recycle.setAdapter(adapter);
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
        pbIntentvideo = view.findViewById(R.id.pb_intentvideo);
        tvIntentvideo = view.findViewById(R.id.tv_intentvideo);
        recycle = view.findViewById(R.id.recycle);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_my;
    }
}
