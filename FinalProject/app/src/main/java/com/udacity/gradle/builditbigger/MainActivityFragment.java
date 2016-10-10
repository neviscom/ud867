package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nevis.nanodegree.displayjokes.DisplayJokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeLoadingCallback {

    private View mProgressBar;
    private View mContent;

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
                tellJoke();
            }
        });

        AdUtils.loadAd(root);
        return root;
    }

    public void tellJoke() {
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onStartLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mContent.setVisibility(View.GONE);
    }

    @Override
    public void onFinishLoading(@NonNull String joke) {
        mProgressBar.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
        startActivity(DisplayJokeActivity.startIntent(getActivity(), joke));
    }
}
