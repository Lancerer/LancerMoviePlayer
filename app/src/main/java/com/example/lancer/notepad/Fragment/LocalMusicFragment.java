package com.example.lancer.notepad.Fragment;

import android.view.View;

import com.example.lancer.notepad.R;


public class LocalMusicFragment extends BaseFragment {


    private android.widget.ImageView topBack;
    private android.widget.TextView topTitle;
    private android.widget.ImageView topSetting;

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.local_music);
    }

    @Override
    public void initView(View view) {
        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_plan;
    }
}
