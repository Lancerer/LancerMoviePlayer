package com.example.lancer.MovieMusic.Fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.MovieMusic.R;


public class IntentMusicFragment extends BaseFragment {

    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.intent_music);
    }

    @Override
    public void initView(View view) {

        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_remember;
    }
}
