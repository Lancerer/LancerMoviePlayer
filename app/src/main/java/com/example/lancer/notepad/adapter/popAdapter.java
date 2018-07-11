package com.example.lancer.notepad.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lancer.notepad.Activity.LocalMusicActivity;
import com.example.lancer.notepad.R;
import com.example.lancer.notepad.bean.MusicBean;

import java.util.List;

/**
 * Created by Lancer on 2018/7/11.
 */

public class popAdapter extends BaseAdapter {
    private List<MusicBean> lists;
    private Context context;

    public popAdapter(List<MusicBean> lists, Context context) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_sing, null);
            viewHolder = new ViewHolder();
            viewHolder.tvItemSing = convertView.findViewById(R.id.tv_item_sing);
            viewHolder.tvItemSinger = convertView.findViewById(R.id.tv_item_singer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MusicBean item = getItem(position);
        viewHolder.tvItemSing.setText("" + item.getName());
        viewHolder.tvItemSinger.setText("" + item.getArtist());
        return convertView;
    }


    public class ViewHolder {
        TextView tvItemSing;
        TextView tvItemSinger;
    }
}
