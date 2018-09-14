package com.example.lancer.MovieMusic.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.view.View;

import android.widget.AdapterView;

import android.widget.TextView;


import com.example.lancer.MovieMusic.Activity.LocalMusicActivity;

import com.example.lancer.MovieMusic.R;
import com.example.lancer.MovieMusic.adapter.LocalMusicAdapter;
import com.example.lancer.MovieMusic.bean.MusicBean;

import com.example.lancer.MovieMusic.util.Constants;
import com.example.lancer.MovieMusic.util.MusicUtils;
import java.util.ArrayList;
import java.util.List;


public class LocalMusicFragment extends BaseFragment {


    private android.widget.ImageView topBack;
    private android.widget.TextView topTitle;
    private android.widget.ImageView topSetting;
    private android.widget.ListView lvLocalMusic;
    private List<MusicBean> lists = new ArrayList<>();
    private MusicUtils musicUtils = new MusicUtils();
    private android.widget.ProgressBar pbLcoalvideo;
    private TextView tvLocalvideo;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lists != null && lists.size() > 0) {
                lvLocalMusic.setAdapter(new LocalMusicAdapter(lists,getContext()));
            } else {
                tvLocalvideo.setVisibility(View.VISIBLE);
            }
            pbLcoalvideo.setVisibility(View.GONE);
        }
    };

    @Override
    public void initData() {
        getLocalMusic();
        lvLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendBrodcast(Constants.ACTION_LIST_ITEM, position);
                Intent intent = new Intent(getContext(), LocalMusicActivity.class);
                intent.putExtra("toactivityposition", position);
                getContext().startActivity(intent);
            }
        });
    }

    private void getLocalMusic() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                lists = musicUtils.getMusicList(getContext());
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void sendBrodcast(String action, int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setAction(action);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.local_music);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void initView(View view) {
        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
        lvLocalMusic = view.findViewById(R.id.lv_local_music);
        pbLcoalvideo = view.findViewById(R.id.pb_lcoalvideo);
        tvLocalvideo = view.findViewById(R.id.tv_localvideo);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_plan;
    }
}
