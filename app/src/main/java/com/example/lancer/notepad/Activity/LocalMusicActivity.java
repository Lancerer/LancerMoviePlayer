package com.example.lancer.notepad.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.MusicBean;
import com.example.lancer.notepad.service.MyService;
import com.example.lancer.notepad.util.Constants;
import com.example.lancer.notepad.util.MusicUtils;
import com.example.lancer.notepad.util.MyUtils;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class LocalMusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ImageView ivLocalmusicBack;
    private TextView tvLocalmusicArtist;
    private TextView tvLocalmusicSingname;
    private ImageView ivLocalmusicAlbum;
    private TextView tvCurrentTime;
    private SeekBar seekbarLocalmusic;
    private TextView tvTotalTime;
    private ImageView ivLocalmusicMode;
    private ImageView ivLocalmusicPre;
    private ImageView ivLocalmusicPlay;
    private ImageView ivLocalmusicNext;
    private ImageView ivLocalmusicMenu;
    private int position;
    private List<MusicBean> lists = new ArrayList<>();
    private MusicUtils musicUtils = new MusicUtils();
    private boolean mIsPlaying;
    private int playMode;
    /**
     *
     */
    private int currentPosition;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constants.STATUS_PREPARED) {
                position = msg.arg1;
                mIsPlaying = (boolean) msg.obj;
                switchSongUI(position);
            } else if (msg.what == Constants.ACTION_PROGRESS) {
                int currentPosition = msg.arg1;
                int totalDuration = msg.arg2;
                mediaPlayer = (MediaPlayer) msg.obj;
                tvTotalTime.setText(MyUtils.parseTime(totalDuration));
                tvCurrentTime.setText(MyUtils.parseTime(currentPosition));
                seekbarLocalmusic.setProgress(currentPosition);
                seekbarLocalmusic.setMax(totalDuration);
            }
        }
    };
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        initView();
        initData();
    }

    private void switchSongUI(int position) {
        if (lists.size() > 0 && position < lists.size()) {
            //设置歌手名，歌曲名
            tvLocalmusicArtist.setText(lists.get(position).getArtist());
            tvLocalmusicSingname.setText(lists.get(position).getName());
            ivLocalmusicAlbum.setImageBitmap(MusicUtils.getAlbumArt(lists.get(position).getAblumid(), this));
        } else {
            Toast.makeText(this, "没有歌曲，下载歌曲后再来使用 ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        lists = musicUtils.getMusicList(this);
        position = getIntent().getIntExtra("toactivityposition", 0);
        startService();
        switchSongUI(position);
        tvTotalTime.setText(MyUtils.formatTime(lists.get(position).getDuration()));
        seekbarLocalmusic.setMax((int) lists.get(position).getDuration());
        initListener();
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        intent.putExtra("position", position);
        intent.putExtra("messager", new Messenger(handler));
        startService(intent);
    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }

    private void initListener() {
        ivLocalmusicBack.setOnClickListener(this);
        ivLocalmusicAlbum.setOnClickListener(this);
        ivLocalmusicMenu.setOnClickListener(this);
        ivLocalmusicMode.setOnClickListener(this);
        ivLocalmusicNext.setOnClickListener(this);
        ivLocalmusicPlay.setOnClickListener(this);
        ivLocalmusicPre.setOnClickListener(this);
        seekbarLocalmusic.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_localmusic_album:
                break;
            case R.id.iv_localmusic_back:
                finish();
                break;
            case R.id.iv_localmusic_menu:
                break;
            case R.id.iv_localmusic_mode:
                setPlayMode();
                break;
            case R.id.iv_localmusic_next:
                sendBroadcast(Constants.ACTION_NEXT);
                break;
            case R.id.iv_localmusic_play:
              if (!mIsPlaying) {
                    ivLocalmusicPlay.setImageResource(R.drawable.play_pause);
                    mIsPlaying = true;
                    sendBroadcast(Constants.STATUS_PLAY);
                } else {
                    ivLocalmusicPlay.setImageResource(R.drawable.play_btn_play_pressed);
                    mIsPlaying = false;
                    sendBroadcast(Constants.STATUS_PAUSE);
                }
                break;
            case R.id.iv_localmusic_pre:
                sendBroadcast(Constants.ACTION_UP);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    /**
     * 设置播放器的播放模式
     */
    private void setPlayMode() {
        MyService.playMode++;
        switch (MyService.playMode % 3) {
            case 0://随机播放
                ivLocalmusicMode.setImageResource(R.drawable.play_mode_random_pressed);
                break;
            case 1://单曲循环
                ivLocalmusicMode.setImageResource(R.drawable.play_mode_singel_cycle);
                break;
            case 2://列表播放
                ivLocalmusicMode.setImageResource(R.drawable.play_mode_cycle_pressed);
                break;
        }
    }

    private void initView() {
        ivLocalmusicBack = findViewById(R.id.iv_localmusic_back);
        tvLocalmusicArtist = findViewById(R.id.tv_localmusic_artist);
        tvLocalmusicSingname = findViewById(R.id.tv_localmusic_singname);
        ivLocalmusicAlbum = findViewById(R.id.iv_localmusic_album);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        seekbarLocalmusic = findViewById(R.id.seekbar_localmusic);
        tvTotalTime = findViewById(R.id.tv_total_time);
        ivLocalmusicMode = findViewById(R.id.iv_localmusic_mode);
        ivLocalmusicPre = findViewById(R.id.iv_localmusic_pre);
        ivLocalmusicPlay = findViewById(R.id.iv_localmusic_play);
        ivLocalmusicNext = findViewById(R.id.iv_localmusic_next);
        ivLocalmusicMenu = findViewById(R.id.iv_localmusic_menu);

        ivLocalmusicPlay.setImageResource(R.drawable.play_pause);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
