package com.example.lancer.MovieMusic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.MovieMusic.R;
import com.example.lancer.MovieMusic.bean.MusicBean;
import com.example.lancer.MovieMusic.util.MusicUtils;
import com.example.lancer.MovieMusic.util.MyUtils;

import java.util.List;

/**
 * Created by Lancer on 2018/7/11.
 */

public class LocalMusicAdapter extends BaseAdapter {
    private List<MusicBean> lists;
    private Context context;

    public LocalMusicAdapter(List<MusicBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

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
            convertView = View.inflate(context, R.layout.item_video, null);
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
        viewHolder.ivItemLocalvideo.setImageBitmap(MusicUtils.getAlbumArt(item.getAblumid(), context));
        viewHolder.tvItemTitle.setText(item.getName());
        viewHolder.tvItemTime.setText("时长:" + MyUtils.formatTime(item.getDuration()));
        viewHolder.tvItemSize.setText("" + item.getArtist());
        return convertView;
    }


     class ViewHolder {
        private ImageView ivItemLocalvideo;
        private TextView tvItemTitle;
        private TextView tvItemTime;
        private TextView tvItemSize;
    }

}
