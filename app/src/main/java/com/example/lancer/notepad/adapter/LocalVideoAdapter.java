package com.example.lancer.notepad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.VideoBean;
import com.example.lancer.notepad.util.MyUtils;

import java.util.List;

/**
 * Created by Lancer on 2018/7/11.
 */

public class LocalVideoAdapter extends BaseAdapter {
    private List<VideoBean> lists;
    private Context context;

    public LocalVideoAdapter(List<VideoBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

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
        VideoBean item = getItem(position);
        viewHolder.ivItemLocalvideo.setImageResource(R.mipmap.user);
        viewHolder.tvItemTitle.setText(item.getTitle());
        viewHolder.tvItemTime.setText("时长:" + MyUtils.formatTime(item.getDuration()));
        viewHolder.tvItemSize.setText("大小:" + MyUtils.formatSize(item.getSize()));
        return convertView;
    }


  class ViewHolder {
      private ImageView ivItemLocalvideo;
      private TextView tvItemTitle;
      private TextView tvItemTime;
      private TextView tvItemSize;

  }
}
