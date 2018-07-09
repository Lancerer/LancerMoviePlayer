package com.example.lancer.notepad.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.notepad.Activity.VideoViewActivity;
import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.VideoBean;
import com.example.lancer.notepad.util.MyUtils;
import com.example.lancer.notepad.util.ToastUtils;
import com.example.lancer.notepad.util.VideoUtils;

import java.io.Serializable;
import java.util.List;


public class LocalVideoFragment extends BaseFragment {


    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;
    private android.widget.ListView lvLcoalvideo;
    private android.widget.ProgressBar pbLcoalvideo;
    private TextView tvLocalvideo;
    private List<VideoBean> lists;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lists != null && lists.size() > 0) {
                lvLcoalvideo.setAdapter(new MyAdapter());

            } else {
                tvLocalvideo.setVisibility(View.VISIBLE);
            }
            pbLcoalvideo.setVisibility(View.GONE);

        }
    };

    @Override
    public void initData() {
        //加载本地视频
        getLocalVideo();
        lvLcoalvideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                // intent.putExtra("uri", lists.get(position).getPath());
                Bundle bundle = new Bundle();
                bundle.putSerializable("VideoList", (Serializable) lists);
                intent.putExtras(bundle);
                intent.putExtra("position", position);
                getContext().startActivity(intent);
               /*
               调用系统的播放器
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(lists.get(position).getPath()),"video*//*");
                getContext().startActivity(intent);
                */
            }
        });
    }

    /*
    * 加载本地视频方法
    * 遍历sd卡，后缀名，使用内容提供者
    * */
    private void getLocalVideo() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // SystemClock.sleep(1000);
                VideoUtils videoUtils = new VideoUtils();
                lists = videoUtils.getVideoList(getContext());
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.local_video);
    }

    @Override
    public void initView(View view) {
        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
        lvLcoalvideo = view.findViewById(R.id.lv_lcoalvideo);
        pbLcoalvideo = view.findViewById(R.id.pb_lcoalvideo);
        tvLocalvideo = view.findViewById(R.id.tv_localvideo);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_list;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public VideoBean getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_video, null);
                viewHolder = new ViewHolder();
                viewHolder.ivItemLocalvideo = convertView.findViewById(R.id.iv_item_localvideo);
                viewHolder.tvItemTitle = convertView.findViewById(R.id.tv_item_title);
                viewHolder.tvItemTime = convertView.findViewById(R.id.tv_item_time);
                viewHolder.tvItemSize = convertView.findViewById(R.id.tv_item_size);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            VideoBean item = getItem(position);
            viewHolder.ivItemLocalvideo.setImageResource(R.mipmap.user);
            viewHolder.tvItemTitle.setText(item.getTitle());
            viewHolder.tvItemTime.setText("时长:" + MyUtils.formatTime(item.getDuration()));
            viewHolder.tvItemSize.setText("大小:" + MyUtils.formatSize(item.getSize()));
            return convertView;
        }
    }

    static class ViewHolder {
        private ImageView ivItemLocalvideo;
        private TextView tvItemTitle;
        private TextView tvItemTime;
        private TextView tvItemSize;

    }
}
