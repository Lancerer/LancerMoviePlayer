package com.example.lancer.notepad.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lancer.notepad.Activity.VideoViewActivity;
import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.MusicBean;
import com.example.lancer.notepad.bean.VideoBean;
import com.example.lancer.notepad.util.MusicUtils;
import com.example.lancer.notepad.util.MyUtils;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class LocalMusicFragment extends BaseFragment {


    private android.widget.ImageView topBack;
    private android.widget.TextView topTitle;
    private android.widget.ImageView topSetting;
    private android.widget.ListView lvLocalMusic;
    private List<MusicBean> lists = new ArrayList<>();
    private MusicUtils musicUtils;

    @Override
    public void initData() {
        musicUtils = new MusicUtils();
        lists = musicUtils.getMusicList(getContext());
        MyAdapter adapter = new MyAdapter();
        lvLocalMusic.setAdapter(adapter);
        lvLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.local_music);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public MusicBean getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
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
            MusicBean item = getItem(position);
          /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MusicUtils.getAlbumArt(item.getAblumid(), getContext()).compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(getContext()).load(bytes).into(viewHolder.ivItemLocalvideo);*/
            viewHolder.ivItemLocalvideo.setImageBitmap(MusicUtils.getAlbumArt(item.getAblumid(), getContext()));
            viewHolder.tvItemTitle.setText(item.getName());
            viewHolder.tvItemTime.setText("时长:" + MyUtils.formatTime(item.getDuration()));
            viewHolder.tvItemSize.setText("" + item.getArtist());
            return convertView;
        }
    }

    static class ViewHolder {
        private ImageView ivItemLocalvideo;
        private TextView tvItemTitle;
        private TextView tvItemTime;
        private TextView tvItemSize;

    }

    @Override
    public void initView(View view) {
        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
        lvLocalMusic = view.findViewById(R.id.lv_local_music);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_plan;
    }
}
