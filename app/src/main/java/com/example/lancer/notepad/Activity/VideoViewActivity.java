package com.example.lancer.notepad.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.VideoBean;
import com.example.lancer.notepad.util.MyUtils;
import com.example.lancer.notepad.util.ToastUtils;
import com.example.lancer.notepad.weight.MyVideoView;

import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.vov.vitamio.provider.MediaStore;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    /*
    * 视频进度更新
    * */
    private static final int PROGRESS = 1;
    /*
    * 三秒没有操作隐藏控制器
    * */
    private static final int HIDEVIDEO = 2;
    /**
     * 全屏模式
     */
    private static final int FULL_TYPE = 1;
    /**
     * 默认模式
     */
    private static final int DEFAULT_TYPE = 2;
    private MyVideoView vv;
    private LinearLayout llTop;
    private LinearLayout llSound;
    private TextView tvVideocontrollerVideoName;
    private TextView tvVideocontrollerSystemTime;
    private Button btVideocontrollerSound;
    private SeekBar seekbarSound;
    private Button btVideocontrollerMsg;
    private LinearLayout llSeekbar;
    private TextView tvVideocontrollerStartTime;
    private SeekBar seekbarVideo;
    private TextView tvVideocontrollerEndTime;
    private LinearLayout llAll;
    private Button btVideocontrollerExit;
    private Button btVideocontrollerPrevoius;
    private Button btVideocontrollerPlay;
    private Button btVideocontrollerNext;
    private Button btVideocontrollerScreen;
    private LinearLayout llBottom;
    private GestureDetector dector;  // 手势识别器
    private String uri;
    /**
     * 判断是否是静音
     */
    private boolean ismute = false;
    /**
     * 当前的音量
     */
    private int currentSounds;
    /**
     * 最大的音量
     */
    private int maxSounds;
    /**
     * 音量管理器
     */
    private AudioManager audioManager;
    /*
    *LocalVideoFragment界面传来的视频列表
    * */
    private List<VideoBean> videoList;
    /*
    * LocalVideoFragment界面传来的播放位置
    * */
    private int position;
    /**
     * 判断d当前videoView是否是全屏
     */
    private boolean isFull = false;
    /**
     * 系统屏幕的宽
     */
    private int scaleWidth;
    /**
     * 系统屏幕的高
     */
    private int scaleHeihgt;
    /**
     * 视频真实的宽
     */
    private int videoWidth;
    /**
     * 视频真实的高
     */
    private int videoHeight;
    /**
     * 判断是否是网络视频
     */
    private boolean netUri;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    int currentPosition = vv.getCurrentPosition();
                    seekbarVideo.setProgress(currentPosition);
                    tvVideocontrollerStartTime.setText(MyUtils.parseTime(currentPosition));
                    //设置系统时间
                    tvVideocontrollerSystemTime.setText(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));

                    //网络视频缓冲处理
                    if (netUri) {
                        int buffer = vv.getBufferPercentage();
                        int totalBuffer = buffer * seekbarVideo.getMax();
                        int secondProgress = totalBuffer / 100;
                        seekbarVideo.setSecondaryProgress(secondProgress);
                    } else {
                        seekbarVideo.setSecondaryProgress(0);
                    }

                    //每秒更新一次
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS, 1000);
                    break;
                case HIDEVIDEO:
                    llBottom.setVisibility(View.GONE);
                    llTop.setVisibility(View.GONE);
                    break;
            }
        }
    };


    /*
    * 沉浸式代码
    * */
   @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        initView();
        initData();
    }

    private void initData() {
        //todo 获得系统屏幕的宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        scaleHeihgt = displayMetrics.heightPixels;
        scaleWidth = displayMetrics.widthPixels;

        //todo 获得音量管理器AudioManager
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        currentSounds = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxSounds = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekbarSound.setMax(maxSounds);
        seekbarSound.setProgress(currentSounds);

        videoList = (List<VideoBean>) getIntent().getSerializableExtra("VideoList");
        position = getIntent().getIntExtra("position", 0);
        if (videoList != null && videoList.size() > 0) {
            VideoBean videoBean = videoList.get(position);
            tvVideocontrollerVideoName.setText(videoBean.getTitle());
            netUri = MyUtils.isNetUri(videoBean.getPath());
            vv.setVideoPath(videoBean.getPath());
        } else if (uri != null) {
            netUri = MyUtils.isNetUri(uri);
            vv.setVideoURI(Uri.parse(uri));
        }
        initListener();
        setButtonState();

    }

    private void initListener() {
        btVideocontrollerPlay.setOnClickListener(this);
        btVideocontrollerMsg.setOnClickListener(this);
        btVideocontrollerExit.setOnClickListener(this);
        btVideocontrollerNext.setOnClickListener(this);
        btVideocontrollerPrevoius.setOnClickListener(this);
        btVideocontrollerScreen.setOnClickListener(this);
        btVideocontrollerSound.setOnClickListener(this);
        vv.setOnErrorListener(this);
        vv.setOnPreparedListener(this);
        vv.setOnCompletionListener(this);

        seekbarVideo.setOnSeekBarChangeListener(this);
        seekbarSound.setOnSeekBarChangeListener(this);

        dector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             * 双击触发的函数
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isFull) {
                    setVideoType(FULL_TYPE);
                } else {
                    setVideoType(DEFAULT_TYPE);
                }
                return super.onDoubleTap(e);
            }

            /**
             * 单击触发的函数
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //Toast.makeText(SystemVideoPlayer.this,"单击了屏幕",Toast.LENGTH_SHORT).show();
                handler.removeMessages(HIDEVIDEO);

                llBottom.setVisibility(View.VISIBLE);
                llTop.setVisibility(View.VISIBLE);

                handler.sendEmptyMessageDelayed(HIDEVIDEO, 2000);

                return super.onSingleTapConfirmed(e);
            }
        });


    }

    /**
     * 设置videoview模式方法
     *
     * @param type videoView的模式
     */
    private void setVideoType(int type) {
        switch (type) {
            case FULL_TYPE:
                isFull = false;
                vv.setVideoSize(scaleWidth, scaleHeihgt);
                btVideocontrollerScreen.setBackgroundResource(R.drawable.btn_default_screen_pressed);
                break;
            case DEFAULT_TYPE:
                //todo 设置等比例放大视频的实际大小
                int mVideoHeight = videoHeight;
                int mVideoWidth = videoWidth;
                int width = scaleWidth;
                int height = scaleHeihgt;
                if (mVideoWidth * height < width * mVideoHeight) {
                    width = height * mVideoWidth / mVideoHeight;
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    height = width * mVideoHeight / mVideoWidth;
                }
                vv.setVideoSize(width, height);
                btVideocontrollerScreen.setBackgroundResource(R.drawable.btn_full_screen_pressed);
                isFull = true;
                break;
        }
    }

    /**
     * 双击播放或暂停
     */
    private void StartOrPause() {
        if (vv.isPlaying()) {
            btVideocontrollerPlay.setBackgroundResource(R.drawable.btn_now_playing_real_play);
            vv.pause();
        } else {
            btVideocontrollerPlay.setBackgroundResource(R.drawable.btn_now_playing_pause);
            vv.start();
        }
    }

    //利用onTouchEvent，传递event事件给手势识别器，否则无法触发手势识别器的回调方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * videoView准备播放事件
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        videoHeight = mp.getVideoHeight();
        videoWidth = mp.getVideoWidth();
        vv.start();
        int duration = vv.getDuration();
        seekbarVideo.setMax(duration);
        tvVideocontrollerEndTime.setText(MyUtils.parseTime(vv.getDuration()));
        handler.sendEmptyMessage(PROGRESS);

        setVideoType(DEFAULT_TYPE);
    }

    /**
     * 当VideoView因为一些原因（没有相应的视频解码器）不能播放视频时,
     * 我们(关闭当前播放器，将当前的所有数据发送到万能播放器后)跳转到我们的万能播放器VitamioView去播放
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //释放ViewoView
        if (vv != null) {
            vv.stopPlayback();
        }
        Intent intent = new Intent(this, VitamioActivity.class);
        if (videoList.size() > 0 && videoList != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("VideoList", (Serializable) videoList);
            intent.putExtras(bundle);
            intent.putExtra("position", position);
        } else if (uri != null) {
            intent.setData(Uri.parse(uri));
        }
        startActivity(intent);
        finish();
        return true;
    }

    /**
     * videoView播放完毕事件(自动播放下一个视频)
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (position == videoList.size() - 1) {
            ToastUtils.showShort(this, "已经是最后一个视频了");
        } else {
            playNextVideo();
        }

    }

    /**
     * 上一曲下一曲返回播放暂停全屏点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_videocontroller_play:
                StartOrPause();
                break;
            case R.id.bt_videocontroller_msg:
                toVitamioPlayer();
                break;
            case R.id.bt_videocontroller_exit:
                finish();
                break;
            case R.id.bt_videocontroller_next:
                playNextVideo();
                break;
            case R.id.bt_videocontroller_prevoius:
                playPrevoiusVideo();
                break;
            case R.id.bt_videocontroller_sound:
                ismute = !ismute;
                updataSounds(currentSounds, ismute);

                break;
            case R.id.bt_videocontroller_screen:
                if (isFull) {
                    setVideoType(FULL_TYPE);
                    btVideocontrollerScreen.setBackgroundResource(R.drawable.btn_default_screen_pressed);
                } else {
                    setVideoType(DEFAULT_TYPE);
                    btVideocontrollerScreen.setBackgroundResource(R.drawable.btn_full_screen_pressed);
                }
                break;
            default:
                break;
        }
        handler.removeMessages(HIDEVIDEO);
        handler.sendEmptyMessageDelayed(HIDEVIDEO, 2000);
    }

    /**
     * 点击切换使用Vitamio播放器播放
     */
    private void toVitamioPlayer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否使用Vitamio播放器播放视频？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定啊", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (vv != null) {
                    vv.stopPlayback();
                }
                Intent intent = new Intent(VideoViewActivity.this, VitamioActivity.class);
                if (videoList.size() > 0 && videoList != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("VideoList", (Serializable) videoList);
                    intent.putExtras(bundle);
                    intent.putExtra("position", position);
                } else if (uri != null) {
                    intent.setData(Uri.parse(uri));
                }
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    /**
     * 点击pre按钮播放上一个视频方法
     */
    private void playPrevoiusVideo() {
        if (videoList != null && videoList.size() > 0) {
            position--;
            if (position >= 0) {
                netUri = MyUtils.isNetUri(videoList.get(position).getPath());
                vv.setVideoPath(videoList.get(position).getPath());
                tvVideocontrollerVideoName.setText(videoList.get(position).getTitle());
                setButtonState();
            }
        } else if (uri != null) {
            setButtonState();
        }
    }

    /**
     * 点击next按钮播放下一个视频方法
     */

    private void playNextVideo() {
        if (videoList != null && videoList.size() > 0) {
            position++;
            if (position < videoList.size()) {
                netUri = MyUtils.isNetUri(videoList.get(position).getPath());
                vv.setVideoPath(videoList.get(position).getPath());
                tvVideocontrollerVideoName.setText(videoList.get(position).getTitle());
                setButtonState();
            }
        } else if (uri != null) {
            setButtonState();
        }
    }

    /**
     * 设置按钮的状态（播放第一个视频时，上一个按钮不可点击，播放最后一个视频时，下一个视频按钮不可点击）
     */
    private void setButtonState() {
        if (videoList != null && videoList.size() > 0) {
            if (videoList.size() == 1) {
                setEnable(false);
            } else if (videoList.size() == 2) {
                if (position == 0) {
                    btVideocontrollerPrevoius.setBackgroundResource(R.drawable.btn_pre_gray);
                    btVideocontrollerPrevoius.setEnabled(false);

                    btVideocontrollerNext.setBackgroundResource(R.drawable.select_next);
                    btVideocontrollerNext.setEnabled(true);

                } else if (position == videoList.size() - 1) {
                    btVideocontrollerNext.setBackgroundResource(R.drawable.btn_next_gray);
                    btVideocontrollerNext.setEnabled(false);

                    btVideocontrollerPrevoius.setBackgroundResource(R.drawable.select_prevoius);
                    btVideocontrollerPrevoius.setEnabled(true);

                }
            } else {
                if (position == 0) {
                    btVideocontrollerPrevoius.setBackgroundResource(R.drawable.btn_pre_gray);
                    btVideocontrollerPrevoius.setEnabled(false);
                } else if (position == videoList.size() - 1) {
                    btVideocontrollerNext.setBackgroundResource(R.drawable.btn_next_gray);
                    btVideocontrollerNext.setEnabled(false);
                } else {
                    setEnable(true);
                }
            }
        } else if (uri != null) {
            //两个按钮设置灰色
            setEnable(false);
        }
    }

    /**
     * @param isEnable
     */
    private void setEnable(boolean isEnable) {
        if (isEnable) {
            btVideocontrollerPrevoius.setBackgroundResource(R.drawable.select_prevoius);
            btVideocontrollerPrevoius.setEnabled(true);
            btVideocontrollerNext.setBackgroundResource(R.drawable.select_next);
            btVideocontrollerNext.setEnabled(true);
        } else {
            //两个按钮设置灰色
            btVideocontrollerPrevoius.setBackgroundResource(R.drawable.btn_pre_gray);
            btVideocontrollerPrevoius.setEnabled(false);
            btVideocontrollerNext.setBackgroundResource(R.drawable.btn_next_gray);
            btVideocontrollerNext.setEnabled(false);
        }
    }

    /**
     * 拖动seekbar改变视频进度
     *
     * @param seekBar  看是哪个seekbar
     * @param progress 当前进度
     * @param fromUser 是否是用户操作
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekbarVideo) {
            if (fromUser) {
                vv.seekTo(progress);
            }
        } else {
            if (progress > 0) {
                ismute = false;
            } else {
                ismute = true;
            }
            updataSounds(progress, ismute);
        }

    }

    /**
     * 修改声音大小的方法
     *
     * @param progress 音量的大小
     * @param ismute   是否静音
     */
    private void updataSounds(int progress, boolean ismute) {
        if (ismute) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            seekbarSound.setProgress(0);
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            seekbarSound.setProgress(progress);
            currentSounds = progress;
        }

    }

    /**
     * 将物理键和seekbar连接起来
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            currentSounds++;
            updataSounds(currentSounds, false);
            handler.removeMessages(HIDEVIDEO);
            handler.sendEmptyMessageDelayed(HIDEVIDEO, 2000);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            currentSounds--;
            updataSounds(currentSounds, false);
            handler.removeMessages(HIDEVIDEO);
            handler.sendEmptyMessageDelayed(HIDEVIDEO, 2000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeMessages(HIDEVIDEO);//当在使用seekbar时，控制器不能隐藏
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.sendEmptyMessageDelayed(HIDEVIDEO, 2000);
        //ToastUtils.showShort(this, "当前音量" + currentSounds);
    }

    private void initView() {
        vv = findViewById(R.id.vv);
        llTop = findViewById(R.id.ll_top);
        llSound = findViewById(R.id.ll_sound);
        tvVideocontrollerVideoName = findViewById(R.id.tv_videocontroller_video_name);
        tvVideocontrollerSystemTime = findViewById(R.id.tv_videocontroller_system_time);
        btVideocontrollerSound = findViewById(R.id.bt_videocontroller_sound);
        seekbarSound = findViewById(R.id.seekbar_sound);
        btVideocontrollerMsg = findViewById(R.id.bt_videocontroller_msg);
        llSeekbar = findViewById(R.id.ll_seekbar);
        tvVideocontrollerStartTime = findViewById(R.id.tv_videocontroller_start_time);
        seekbarVideo = findViewById(R.id.seekbar_video);
        tvVideocontrollerEndTime = findViewById(R.id.tv_videocontroller_end_time);
        llAll = findViewById(R.id.ll_all);
        btVideocontrollerExit = findViewById(R.id.bt_videocontroller_exit);
        btVideocontrollerPrevoius = findViewById(R.id.bt_videocontroller_prevoius);
        btVideocontrollerPlay = findViewById(R.id.bt_videocontroller_play);
        btVideocontrollerNext = findViewById(R.id.bt_videocontroller_next);
        btVideocontrollerScreen = findViewById(R.id.bt_videocontroller_screen);
        llBottom = findViewById(R.id.ll_bottom);
    }
}

