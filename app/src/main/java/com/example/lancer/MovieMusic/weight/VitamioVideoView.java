package com.example.lancer.MovieMusic.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Lancer on 2018/7/3.
 */

public class VitamioVideoView extends io.vov.vitamio.widget.VideoView {
    public VitamioVideoView(Context context) {
        this(context, null);
    }

    public VitamioVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VitamioVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置VideoView的宽高
     *
     * @param VideoWidth
     * @param VidwoHeight
     */
    public void setVideoSize(int VideoWidth, int VidwoHeight) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = VideoWidth;
        params.height = VidwoHeight;
        setLayoutParams(params);
    }
}
