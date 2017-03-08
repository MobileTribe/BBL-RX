package com.adeo.rxjava.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adeo.rxjava.demo.R;
import com.adeo.rxjava.demo.ui.fragments.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.adeo.rxjava.demo.DateUtil.getDate;

/**
 * Created by paul-hubert on 08/12/2016.
 */

public class BasicDemoFragment extends BaseFragment {

    @BindView(R.id.basic_demo_tv_result)
    TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.basic_demo_fragment, container, false);
    }

    @OnClick(R.id.basic_demo_btn)
    void basicDemo() {
        mTvResult.setText("");
        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onNext(String value) {
                        addText("onNext : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addText("onError");
                    }

                    @Override
                    public void onCompleted() {
                        addText("onComplete");
                    }
                });
    }

    @OnClick(R.id.basic_demo_btn_2)
    void basicDemo2() {
        mTvResult.setText("");
        Observable.just("one", "two", "three", "four", "five")
                .map(string -> string + " bananas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onNext(String value) {
                        addText("onNext : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addText("onError");
                    }

                    @Override
                    public void onCompleted() {
                        addText("onComplete");
                    }
                });
    }

    @OnClick(R.id.basic_demo_btn_3)
    void basicDemo3() {
        mTvResult.setText("");
        Observable.just("one", "two", "three", "four", "five")
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (string, aLong) -> string)
                .filter(string -> string.length() > 3)
                .map(string -> string + " bananas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onNext(String value) {
                        addText("onNext : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addText("onError");
                    }

                    @Override
                    public void onCompleted() {
                        addText("onComplete");
                    }
                });

    }

    void addText(String text) {
        mTvResult.setText(mTvResult.getText().toString() + "\n" + getDate() + " : " + text);
    }
}
