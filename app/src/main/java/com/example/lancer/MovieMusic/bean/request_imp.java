package com.example.lancer.MovieMusic.bean;

import com.example.lancer.MovieMusic.util.Constants;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lancer on 2018/7/11.
 */

public interface request_imp {
    @GET(Constants.NET_URL)
    Observable<IntentBean> getBSjie();

    @GET("/api/data/休息视频/10/{page}")
    Observable<DouYinBean> getDouYin(@Path("page") int page);
}
