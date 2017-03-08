package com.adeo.rxjava.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adeo.rxjava.demo.R;
import com.adeo.rxjava.demo.ui.fragments.BasicDemoFragment;
import com.adeo.rxjava.demo.ui.fragments.ChainedCallsFragment;
import com.adeo.rxjava.demo.ui.fragments.LongCallFragment;
import com.adeo.rxjava.demo.ui.fragments.PlaceAutocompleteFragment;
import com.adeo.rxjava.demo.ui.fragments.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by paul-hubert on 08/12/2016.
 */

public class MainFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @OnClick(R.id.main_activity_basic_demo_btn)
    void basicDemo() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new BasicDemoFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.main_activity_places_autocomplete_demo_btn)
    void autoCompleteDemo() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new PlaceAutocompleteFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.main_activity_chained_calls_demo_btn)
    void chainedCallsDemo() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new ChainedCallsFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.main_activity_long_call_demo_btn)
    void longCallDemo() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new LongCallFragment())
                .addToBackStack(null)
                .commit();
    }
}
