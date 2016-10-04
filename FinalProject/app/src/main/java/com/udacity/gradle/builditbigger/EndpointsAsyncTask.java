package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.nevis.nanodegree.displayjokes.DisplayJokeActivity;
import com.nikita.simonov.jokes.backend.MyEndpoint;

/**
 * @author Nikita Simonov
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    private final static MyEndpoint sMyEndpoint = new MyEndpoint();
    private  Context mContext;

    @Override
    protected String doInBackground(Context... params) {
        mContext = params[0];
        return sMyEndpoint.getJoke().getData();
    }

    @Override
    protected void onPostExecute(String joke) {
        mContext.startActivity(DisplayJokeActivity.startIntent(mContext, joke));
    }
}
