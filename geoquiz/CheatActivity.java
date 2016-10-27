package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG2 = "CheatActivity";
    private static final String KEY_INDEX2 = "index";

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mAPI;
    private Button mShowAnswer;
    int apiLevel = Build.VERSION.SDK_INT;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG2, "onCreate(Bundle) called");
        setContentView(R.layout.activity_cheat);

        mAPI = (TextView) findViewById(R.id.api_level);
        mAPI.setText("API level " + String.valueOf(apiLevel));

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }else{
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG2, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_INDEX2, mAnswerIsTrue);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG2, "onRestoreInstanceState");
        mAnswerIsTrue = savedInstanceState.getBoolean(KEY_INDEX2);
        setAnswerShownResult(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG2, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG2, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG2, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG2, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG2, "onDestroy() called");
    }

}
