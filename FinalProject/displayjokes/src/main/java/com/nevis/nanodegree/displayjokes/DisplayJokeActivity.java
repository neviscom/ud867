package com.nevis.nanodegree.displayjokes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author Nikita Simonov
 */

public class DisplayJokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "joke";

    private TextView mJokeView;

    public static Intent startIntent(@NonNull Context context, @NonNull String joke) {
        Intent intent = new Intent(context, DisplayJokeActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        mJokeView = (TextView) findViewById(R.id.tv_joke);

        if (getIntent() != null && getIntent().hasExtra(EXTRA_JOKE)) {
            tellJoke(getIntent().getStringExtra(EXTRA_JOKE));
        }
    }

    public void tellJoke(@NonNull String joke) {
        mJokeView.setText(joke);
    }
}
