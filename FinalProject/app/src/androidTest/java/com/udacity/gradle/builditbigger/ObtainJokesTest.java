package com.udacity.gradle.builditbigger;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@RunWith(AndroidJUnit4.class)
public class ObtainJokesTest {

    @Test
    public void testJokeObtainment() throws Exception {
        EndpointsAsyncTask.JokeLoadingCallback callback = new EndpointsAsyncTask.JokeLoadingCallback() {
            @Override
            public void onStartLoading() {
                // do nothing
            }

            @Override
            public void onFinishLoading(@NonNull String joke) {
                // do nothing
            }
        };

        String joke = new EndpointsAsyncTask(callback).execute().get();
        assertNotNull(joke);
        assertTrue(joke.length() > 0);
    }
}