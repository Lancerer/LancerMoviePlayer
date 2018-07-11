package com.example.lancer.notepad.util;


/**
 * 作用：常量类，配置网络地址
 */
public class Constants {
    /**
     * 网络视频的联网地址
     */
    /*public static final String NET_URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";*/
    /**
     * 搜索的路径
     */
    public static final String SEARCH_URL = "http://hot.news.cntv.cn/index.php?controller=list&action=searchList&sort=date&n=20&wd=";
    /**
     * 网络音乐
     */
    public static final String ALL_RES_URL = "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8";
    /**
     * 停止状态
     */
    public static final String STATUS_STOP = "status_stop";
    /**
     * 播放状态
     */
    public static final String STATUS_PLAY = "status_play";
    /**
     * 暂停状态
     */
    public static final String STATUS_PAUSE = "status_pause";
    /**
     * 服务端的播放器播放的位置position消息
     */
    public static final int STATUS_PREPARED = 1;
    /**
     * 播放结束状态
     */
    public static final String STATUS_END = "status_end";
    /**
     * 本地歌曲listview点击
     */
    public static final String ACTION_LIST_ITEM = "status_list_item";
    /**
     * 下一曲
     */
    public static final String ACTION_NEXT = "status_next";
    /**
     * 上一曲
     */
    public static final String ACTION_UP = "status_up";
    /**
     * 更新theme
     */
    public static final String UPDATA_THEME = "updata_theme";
    /**
     * 播放模式
     */
    public static final String ACTION_MODE = "action_mode";
    /**
     * 随机播放
     */
    public static final int ACTION_RANDOM = 3;
    /**
     * 单曲循环
     */
    public static final int ACTION_ONE = 1;
    /**
     * 顺序播放
     */
    public static final int ACTION_LOOP = 2;
    /**
     * 服务端歌曲进度条设置
     */
    public static final int ACTION_PROGRESS = 2;
    /**
     * 显示歌词
     */
    public static final int SHOW_LYRIC = 3;
    /**
     * 网络视频 url
     */
    public static final String NET_URL = "topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8";
    /**
     * 网络视频 baseurl
     */
    public static final String NET_BASE_URL = "http://s.budejie.com/";
}
