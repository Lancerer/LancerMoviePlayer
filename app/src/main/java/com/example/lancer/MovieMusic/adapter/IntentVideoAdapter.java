package com.example.lancer.MovieMusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lancer.MovieMusic.R;
import com.example.lancer.MovieMusic.bean.DouYinBean;
import com.example.lancer.MovieMusic.weight.MyVideoView;

import java.util.List;

/**
 * author: Lancer
 * date：2018/8/9
 * des:
 * email:tyk790406977@126.com
 */

public class IntentVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DouYinBean.ResultsBean> lists;
    private Context context;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOT = 1;

    private int foot_status = 1;
    private static final int LOAD_MORE = 1;
    private static final int NO_MORE = 2;


    public IntentVideoAdapter(List<DouYinBean.ResultsBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     /*  if (viewType == TYPE_NORMAL) {*/
        View view = View.inflate(context, R.layout.item_my, null);
        NormalViewHolder viewHolder = new NormalViewHolder(view);
        return viewHolder;
   /*  }*//* else {
            View view = View.inflate(context, R.layout.foot_view, null);
            FootViewHolder viewHolder = new FootViewHolder(view);
            return viewHolder;
        }*/

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            Uri uri = Uri.parse(lists.get(position).getUrl());
         ((NormalViewHolder) holder).vvMy.setVideoURI(uri);
            ((NormalViewHolder) holder).tvdesc.setText(lists.get(position).getDesc());
          //  ((NormalViewHolder) holder).vvMy.setVideoPath(lists.get(position).getUrl());
           // ((NormalViewHolder) holder).vvMy.requestFocus();
            ((NormalViewHolder) holder).vvMy.start();
          ((NormalViewHolder) holder).vvMy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NormalViewHolder) holder).ivPlay.setVisibility(View.VISIBLE);
                    ((NormalViewHolder) holder).vvMy.pause();
                }
            });
            ((NormalViewHolder) holder).ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NormalViewHolder) holder).ivPlay.setVisibility(View.GONE);
                    ((NormalViewHolder) holder).vvMy.start();
                }
            });
        }/*else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (position == 0) {
                footViewHolder.progressbar.setVisibility(View.GONE);
                footViewHolder.tvLine1.setVisibility(View.GONE);
                footViewHolder.tvLine2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }
            switch (foot_status) {
                case LOAD_MORE:
                    footViewHolder.progressbar.setVisibility(View.VISIBLE);
                    footViewHolder.tvLine1.setVisibility(View.GONE);
                    footViewHolder.tvLine2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("正在加载...");
                    break;
                case NO_MORE:
                    footViewHolder.progressbar.setVisibility(View.GONE);
                    footViewHolder.tvLine1.setVisibility(View.VISIBLE);
                    footViewHolder.tvLine2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText("我是有底线的");
                    footViewHolder.tv_state.setTextColor(Color.parseColor("#ff00ff"));
                    break;
            }
        }*/
    }

   /* @Override
    public int getItemViewType(int position) {
      if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }*/

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        private MyVideoView vvMy;
        private ImageView ivPlay;
        private TextView tvdesc;

        public NormalViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            vvMy = view.findViewById(R.id.vv_my);
            ivPlay = view.findViewById(R.id.iv_play);
            tvdesc = view.findViewById(R.id.tv_desc);
        }
    }

   /*class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLine1;
        private ProgressBar progressbar;
        private TextView tv_state;
        private TextView tvLine2;

        public FootViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            tvLine1 = view.findViewById(R.id.tv_line1);
            progressbar = view.findViewById(R.id.progressbar);
            tv_state = view.findViewById(R.id.foot_view_item_tv);
            tvLine2 = view.findViewById(R.id.tv_line2);
        }
    }*/
}
