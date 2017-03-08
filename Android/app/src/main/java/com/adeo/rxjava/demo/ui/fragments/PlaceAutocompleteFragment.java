package com.adeo.rxjava.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.adeo.rxjava.demo.R;
import com.adeo.rxjava.demo.ui.fragments.base.BaseFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

import static com.adeo.rxjava.demo.DateUtil.getDate;

/**
 * Created by paul-hubert on 19/12/2016.
 */

public class PlaceAutocompleteFragment extends BaseFragment {

    @BindView(R.id.place_autocomplete_demo_et_input)
    EditText mEtInput;

    @BindView(R.id.place_autocomplete_demo_tv_result)
    TextView mTvResult;

    @BindView(R.id.place_autocomplete_demo_tv_search_result)
    TextView mTvSearchResult;

    GoogleApiClient mGoogleApiClient;

    AutocompleteFilter mAutocompleteFilter = new AutocompleteFilter.Builder()
            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
            .setCountry("fr")
            .build();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_autocomplete_demo_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();

        mGoogleApiClient.connect();

        RxTextView.textChanges(mEtInput)
                .map(s -> {
                    addText("User input : " + s.toString());
                    return s;
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(input -> input.length() > 2)
                .observeOn(Schedulers.io())
                .map(input -> {
                    if (mGoogleApiClient.isConnected()) {
                        return Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, input.toString(), null, mAutocompleteFilter).await(500, TimeUnit.MILLISECONDS);
                    } else {
                        throw Exceptions.propagate(new Error("GoogleClient is not connected."));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        autocompletePredictions -> {
                            addText("Showing results.");
                            showResults(autocompletePredictions);
                            autocompletePredictions.release();
                        },
                        error -> {
                            addText("Error : " + error.toString());
                        });
    }

    void showResults(AutocompletePredictionBuffer buffer) {
        StringBuilder sb = new StringBuilder();
        for (AutocompletePrediction autocompletePrediction : buffer) {
            sb.append(autocompletePrediction.getFullText(null).toString()).append("\n");
        }
        mTvSearchResult.setText(sb.toString());
    }

    void addText(String text) {
        Log.d("AUTOCOMPLETE", getDate() + " : " + text);
//        mTvResult.setText(mTvResult.getText().toString() + "\n" + getDate() + " : " + text);
    }
}
