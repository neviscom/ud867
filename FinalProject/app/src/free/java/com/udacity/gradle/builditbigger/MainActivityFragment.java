package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.nevis.nanodegree.displayjokes.DisplayJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeLoadingCallback {

    private View mProgressBar;
    private View mContent;

    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = root.findViewById(R.id.progress);
        mContent = root.findViewById(R.id.content);
        root.findViewById(R.id.btn_tell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdAndTellJoke();
            }
        });

        AdUtils.loadAd(root);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJoke();
            }
        });
        requestNewInterstitial();

        return root;
    }

    @Override
    public void onStartLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mContent.setVisibility(View.GONE);
    }

    @Override
    public void onFinishLoading(@NonNull String joke) {
        startActivity(DisplayJokeActivity.startIntent(getActivity(), joke));
        mProgressBar.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("DE813FFAB892382C32E0A5AF126741B7")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void showAdAndTellJoke() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            tellJoke();
        }
    }

    private void tellJoke() {
        new EndpointsAsyncTask(this).execute();
    }
}
