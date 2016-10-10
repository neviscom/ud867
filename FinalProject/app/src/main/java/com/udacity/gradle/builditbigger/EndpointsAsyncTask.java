package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nikita.simonov.jokes.backend.myApi.MyApi;

import java.io.IOException;

/**
 * @author Nikita Simonov
 */

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    @NonNull
    private final JokeLoadingCallback mJokeLoadingCallback;

    public EndpointsAsyncTask(@NonNull JokeLoadingCallback jokeLoadingCallback) {
        mJokeLoadingCallback = jokeLoadingCallback;
    }

    @Override
    protected void onPreExecute() {
        mJokeLoadingCallback.onStartLoading();
    }

    @Override
    protected String doInBackground(Void... params) {
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
        mJokeLoadingCallback.onFinishLoading(joke);
    }

    public interface JokeLoadingCallback {

        void onStartLoading();

        void onFinishLoading(@NonNull String joke);

    }
}
