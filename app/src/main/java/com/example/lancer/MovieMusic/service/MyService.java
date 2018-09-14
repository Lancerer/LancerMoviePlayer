package com.example.lancer.MovieMusic.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.lancer.MovieMusic.bean.MusicBean;
import com.example.lancer.MovieMusic.util.Constants;
import com.example.lancer.MovieMusic.util.LogUtils;
import com.example.lancer.MovieMusic.util.MusicUtils;
import com.example.lancer.MovieMusic.util.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * Created by Lancer on 2018/7/8.
 */

public class MyService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = "PlayerService";
    private List<MusicBean> lists = new ArrayList<>();
    private MusicUtils musicUtils = new MusicUtils();
    private int currentPositon;
    private MyReceiver receiver;
    private MediaPlayer mediaPlayer = null;
    private Messenger mMessenger;
    public static int playMode = 2;//1.单曲循环 2.列表循环 0.随机播放
    private Random mRandom=new Random();
    public static int prv_position;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 播放错误时直接播放下一曲
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ToastUtils.showShort(getApplicationContext(), "播放歌曲错误");
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_NEXT);
        sendBroadcast(intent);
        return true;
    }

    /**
     * 播放完成后自动播放下一曲
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_NEXT);
        sendBroadcast(intent);
    }

    /**
     * mediaplayer准备播放
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        if (mediaPlayer != null) {
            sentPreparedMessageToMain();
            sentCurrentPositionToMainTimer();
        }
    }

    /**
     * 将歌曲的总时长发送给Actvivity
     */
    private void sentCurrentPositionToMainTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    //1.准备好的时候.告诉activity,当前歌曲的总时长
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int totalDuration = mediaPlayer.getDuration();
                    Message mMessage = Message.obtain();
                    mMessage.what = Constants.ACTION_PROGRESS;
                    mMessage.arg1 = currentPosition;
                    mMessage.arg2 = totalDuration;
                    mMessage.obj = mediaPlayer;
                    //2.发送消息
                    try {
                        mMessenger.send(mMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1000);

    }

    /**
     * 将当前的播放位置以及状态发送给Activity
     */
    private void sentPreparedMessageToMain() {
        Message message = new Message();
        message.what = Constants.STATUS_PREPARED;
        message.arg1 = currentPositon;
        //message.arg2 = mediaPlayer.getCurrentPosition();
        message.obj = mediaPlayer.isPlaying();
        try {
            //发送播放位置
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化广播初始化播放器
     */
    @Override
    public void onCreate() {
        LogUtils.d(TAG, "onCreate");
        regFilter();
        initMediaPlay();
        super.onCreate();
    }

    /**
     * 出事播放器方法
     */
    private void initMediaPlay() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    /**
     * 获得activity传递来的当前播放条目数，以及messanger（handler）
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            lists = musicUtils.getMusicList(getApplicationContext());
            currentPositon = intent.getIntExtra("position", 0);
            mMessenger = (Messenger) intent.getExtras().get("messager");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注销mediaplayer和广播
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getApplicationContext().unregisterReceiver(receiver);
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 注册广播
     */
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_LIST_ITEM);
        intentFilter.addAction(Constants.STATUS_END);
        intentFilter.addAction(Constants.STATUS_PAUSE);
        intentFilter.addAction(Constants.STATUS_PLAY);
        intentFilter.addAction(Constants.STATUS_STOP);
        intentFilter.addAction(Constants.ACTION_NEXT);
        intentFilter.addAction(Constants.ACTION_UP);
        intentFilter.setPriority(1000);//设置优先级
        if (receiver == null) {
            receiver = new MyReceiver();
        }
        getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    /**
     * 广播接收者
     */
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_LIST_ITEM:
                    currentPositon = intent.getIntExtra("position", 0);
                    play(currentPositon);
                    break;
                case Constants.ACTION_NEXT:
                    prv_position = currentPositon;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(currentPositon);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        currentPositon++;
                        if (currentPositon <= lists.size() - 1) {
                            play(currentPositon);
                        } else {
                            currentPositon = 0;
                            play(currentPositon);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case Constants.ACTION_UP:
                    prv_position = currentPositon;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(currentPositon);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        currentPositon--;
                        if (currentPositon < 0) {
                            currentPositon = lists.size() - 1;
                            play(currentPositon);
                        } else {
                            play(currentPositon);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case Constants.STATUS_PAUSE:
                    pause();
                    break;
                case Constants.STATUS_PLAY:
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    } else {
                        initMediaPlay();
                        play(currentPositon);
                    }
                    break;
                case Constants.STATUS_STOP:
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 随机播放
     * @return
     */
    private int getRandom() {
        currentPositon = mRandom.nextInt(lists.size());
        return currentPositon;
    }

    /**
     * 暂停方法
     */
    private void pause() {
        if (mediaPlayer.isPlaying() && mediaPlayer != null) {
            currentPositon = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    /**
     * 播放
     */
    private void play(int position) {
        if (mediaPlayer != null && lists.size() > 0) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(lists.get(position).getPath());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
