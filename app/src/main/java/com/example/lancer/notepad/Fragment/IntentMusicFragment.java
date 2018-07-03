package com.example.lancer.notepad.Fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.notepad.R;
import com.example.lancer.notepad.dao.RememberDao;
import com.example.lancer.notepad.db.DBHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;




public class IntentMusicFragment extends BaseFragment {

    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.intent_music);
    }

    @Override
    public void initView(View view) {

        topBack = view.findViewById(R.id.top_back);
        topTitle = view.findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_remember;
    }
}
