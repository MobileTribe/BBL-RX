package com.adeo.rxjava.demo.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adeo.rxjava.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by paul-hubert on 03/01/2017.
 */

public class LongCallFragment extends Fragment {

    private Unbinder mUnbinder;

    private Subscription mSubscription;

    @BindView(R.id.long_call_tv)
    TextView mTvLongCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.long_call_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        mUnbinder.unbind();
    }

    @OnClick(R.id.long_call_without_rx_btn)
    void longCallWithoutRx() {
        new LongAsyncTask().execute();
    }

    @OnClick(R.id.long_call_with_rx_btn)
    void longCallWithRx() {
        mSubscription = Observable.just("rx")
                .map(s -> {
                    try {
                        Thread.sleep(5000); // Don't do that :)
                    } catch (InterruptedException e) {}
                    return s;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(s -> {
                    mTvLongCall.setText("success with rx");
                }, throwable -> {
                    mTvLongCall.setText("error");
                });
    }

    private class LongAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mTvLongCall.setText("success without rx");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000); // Don't do that :)
            } catch (InterruptedException e) {}
            return null;
        }
    }
}
