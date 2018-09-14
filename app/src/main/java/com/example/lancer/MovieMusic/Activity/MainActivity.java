package com.example.lancer.MovieMusic.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lancer.MovieMusic.Fragment.IntentMusicFragment;
import com.example.lancer.MovieMusic.Fragment.IntentVideoFragment;
import com.example.lancer.MovieMusic.Fragment.LocalMusicFragment;
import com.example.lancer.MovieMusic.Fragment.LocalVideoFragment;
import com.example.lancer.MovieMusic.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fm;
    private FragmentTransaction ft;
    private IntentMusicFragment intentMusicFragment;
    private LocalMusicFragment localMusicFragment;
    private IntentVideoFragment intentVideoFragment;
    private LocalVideoFragment localVideoFragment;
    private FrameLayout pool;
    private LinearLayout llRemember;
    private ImageView ivRemember;
    private TextView tvRemember;
    private LinearLayout llPaln;
    private ImageView ivPlan;
    private TextView tvPlan;
    private LinearLayout llMy;
    private ImageView ivMy;
    private TextView tvMy;
    private LinearLayout llList;
    private ImageView ivList;
    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        select(3);//默认选择本地视频选项
        llList.setOnClickListener(this);
        llMy.setOnClickListener(this);
        llPaln.setOnClickListener(this);
        llRemember.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_remember:
                select(0);
                break;
            case R.id.ll_paln:
                select(1);
                break;
            case R.id.ll_my:
                select(2);
                break;
            case R.id.ll_list:
                select(3);
                break;
            default:
                break;
        }
    }

    /**
     * 选项卡功能
     * @param i 选择的界面
     */
    private void select(int i) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        reSetTab();
        switch (i) {
            case 0:
                if (intentMusicFragment == null) {
                    intentMusicFragment = new IntentMusicFragment();
                    ft.replace(R.id.pool, intentMusicFragment);
                } else {
                    ft.replace(R.id.pool, intentMusicFragment);
                }
                tvRemember.setTextColor(Color.BLUE);
                break;
            case 1:
                if (localMusicFragment == null) {
                    localMusicFragment = new LocalMusicFragment();
                    ft.replace(R.id.pool, localMusicFragment);
                } else {
                    ft.replace(R.id.pool, localMusicFragment);
                }
                tvPlan.setTextColor(Color.BLUE);
                break;
            case 2:
                if (intentVideoFragment == null) {
                    intentVideoFragment = new IntentVideoFragment();
                    ft.replace(R.id.pool, intentVideoFragment);
                } else {
                    ft.replace(R.id.pool, intentVideoFragment);
                }
                tvMy.setTextColor(Color.BLUE);
                break;
            case 3:
                if (localVideoFragment == null) {
                    localVideoFragment = new LocalVideoFragment();
                    ft.replace(R.id.pool, localVideoFragment);
                } else {
                    ft.replace(R.id.pool, localVideoFragment);
                }
                tvList.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }
        ft.commit();
    }

    /**
     * 初始化选项的字体颜色
     */
    private void reSetTab() {
        tvRemember.setTextColor(Color.BLACK);
        tvPlan.setTextColor(Color.BLACK);
        tvMy.setTextColor(Color.BLACK);
        tvList.setTextColor(Color.BLACK);
    }

    private void initView() {
        pool = findViewById(R.id.pool);
        llRemember = findViewById(R.id.ll_remember);
        ivRemember = findViewById(R.id.iv_remember);
        tvRemember = findViewById(R.id.tv_remember);
        llPaln = findViewById(R.id.ll_paln);
        ivPlan = findViewById(R.id.iv_plan);
        tvPlan = findViewById(R.id.tv_plan);
        llMy = findViewById(R.id.ll_my);
        ivMy = findViewById(R.id.iv_my);
        tvMy = findViewById(R.id.tv_my);
        llList = findViewById(R.id.ll_list);
        ivList = findViewById(R.id.iv_list);
        tvList = findViewById(R.id.tv_list);
    }

}
