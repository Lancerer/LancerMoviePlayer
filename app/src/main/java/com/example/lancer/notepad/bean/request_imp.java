package com.example.lancer.notepad.bean;

import com.example.lancer.notepad.util.Constants;

import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;

/**
 * Created by Lancer on 2018/7/11.
 */

public interface request_imp {
    @GET(Constants.NET_URL)
    Observable<IntentBean> getBSjie();
}
