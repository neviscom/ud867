package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nevis.nanodegree.displayjokes.DisplayJokeActivity;
import com.nikita.simonov.jokes.backend.myApi.MyApi;

import java.io.IOException;

/**
 * @author Nikita Simonov
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    private  Context mContext;

    @Override
    protected String doInBackground(Context... params) {
        mContext = params[0];
        try {
            return new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    })
                    .build()
                    .getJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mContext.startActivity(DisplayJokeActivity.startIntent(mContext, joke));
    }
}
