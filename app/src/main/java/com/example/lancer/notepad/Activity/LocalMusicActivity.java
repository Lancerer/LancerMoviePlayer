package com.example.lancer.notepad.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.adapter.popAdapter;
import com.example.lancer.notepad.bean.MusicBean;
import com.example.lancer.notepad.service.MyService;
import com.example.lancer.notepad.util.Constants;
import com.example.lancer.notepad.util.LyricUtils;
import com.example.lancer.notepad.util.MusicUtils;
import com.example.lancer.notepad.util.MyUtils;
import com.example.lancer.notepad.weight.LycView;

import java.io.File;
import java.util.ArrayList;
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
    private boolean isShowLyc;
    private PopupWindow popupWindow;

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
            } else if (msg.what == Constants.SHOW_LYRIC) {
                //1.得到当前的进度
                try {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    //2.把进度传入ShowLyricView控件，并且计算该高亮哪一句
                    lycView.setshowNextLyric(currentPosition);
                    //3.实时的发消息
                    handler.removeMessages(Constants.SHOW_LYRIC);
                    handler.sendEmptyMessage(Constants.SHOW_LYRIC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private MediaPlayer mediaPlayer;
    private LycView lycView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        initView();
        initData();
        showLyric();
    }

    private void showLyric() {
        //解析歌词
        LyricUtils lyricUtils = new LyricUtils();

        try {
            String path = lists.get(position).getPath();//得到歌曲的绝对路径

            //传歌词文件
            //mnt/sdcard/audio/beijingbeijing.mp3
            //mnt/sdcard/audio/beijingbeijing.lrc
            path = path.substring(0, path.lastIndexOf("."));
            File file = new File(path + ".lrc");
            if (!file.exists()) {
                file = new File(path + ".txt");
            }
            lyricUtils.readLyricFile(file);//解析歌词

            lycView.setLyrics(lyricUtils.getLyrics());

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (lyricUtils.isExistsLyric()) {
            handler.sendEmptyMessage(Constants.SHOW_LYRIC);
        }

    }

    /**
     * 更新歌曲歌手名专辑ui
     *
     * @param position
     */
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
        lycView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_localmusic_album:
                lycView.setVisibility(View.VISIBLE);
                ivLocalmusicAlbum.setVisibility(View.GONE);
                showLyric();
                break;
            case R.id.lycView:
                lycView.setVisibility(View.GONE);
                ivLocalmusicAlbum.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_localmusic_back:
                finish();
                break;
            case R.id.iv_localmusic_menu:
                showpop();
                break;
            case R.id.iv_localmusic_mode:
                setPlayMode();
                break;
            case R.id.iv_localmusic_next:
                if (!mIsPlaying) {
                    ivLocalmusicPlay.setImageResource(R.drawable.play_pause);
                }
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

    /**
     * 显示popwindow
     */
    private void showpop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_item, null);

        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //2.设置布局中各个View点击事件
        ListView lv_pop = contentView.findViewById(R.id.lv_pop);
        lv_pop.setAdapter(new popAdapter(lists,this));
        lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendBrodcast(Constants.ACTION_LIST_ITEM, position);
                ivLocalmusicPlay.setImageResource(R.drawable.play_pause);
                popupWindow.dismiss();
            }
        });

        TextView tvpopclose = contentView.findViewById(R.id.tv_pop_close);
        tvpopclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //3.设置popwindow要显示的位置
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_localmusic, null), Gravity.BOTTOM, 0, 0);
        // popupWindow.setAnimationStyle(R.style.pop_anim);
    }

    private void sendBrodcast(String action, int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setAction(action);
        sendBroadcast(intent);
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
        lycView = findViewById(R.id.lycView);
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
