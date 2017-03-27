package com.adeo.rxjava.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adeo.rxjava.demo.R;
import com.adeo.rxjava.demo.model.GithubRelease;
import com.adeo.rxjava.demo.service.GithubService;
import com.adeo.rxjava.demo.ui.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paul-hubert on 19/12/2016.
 */

public class ChainedCallsFragment extends BaseFragment {

    @BindView(R.id.chained_call_tv)
    TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chained_calls_fragment, container, false);
    }

    @OnClick(R.id.chained_call_btn)
    void doWsCall() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GithubService service = retrofit.create(GithubService.class);


        Observable.just("rx")
                .flatMap(query -> service.search(query, "stars"))
                .flatMap(githubRepoSearch -> Observable.fromIterable(githubRepoSearch.getItems()))
                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .map(githubRepo -> {
                    addText(githubRepo.getFull_name());
                    return githubRepo;
                })
                .observeOn(Schedulers.io())
                .flatMap(githubRepo -> service.getReleases(githubRepo.getReleases_url()))
                .flatMap(githubReleases -> service.getRelease(githubReleases.get(0).getUrl()))
                .map(GithubRelease::getTag_name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(tagName -> {
                    addText(tagName); // OnNext
                }, throwable -> {
                    addText("Error : " + throwable.toString()); // OnError
                });

    }

    void addText(String text) {
        mTvResult.setText(mTvResult.getText() + "\n" + text);
    }
}
