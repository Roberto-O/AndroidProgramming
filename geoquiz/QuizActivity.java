package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEXS = "indexs";
    private static final String KEY_INDEXX = "indexx";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private boolean[] mWhereCheated;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if(mWhereCheated[mCurrentIndex] == false) {
                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast;
                } else {
                    messageResId = R.string.incorrect_toast;
                }
            }else{
                messageResId = R.string.judgment_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mWhereCheated = new boolean[mQuestionBank.length];
        for(int i = 0; i < mQuestionBank.length; i++){
            mWhereCheated[i] = false;
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mIsCheater){
                    mWhereCheated[mCurrentIndex] = true;
                }

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mCurrentIndex == 0){
                    if(mIsCheater){
                        mWhereCheated[mCurrentIndex] = true;
                    }

                    mCurrentIndex = 4;
                    mIsCheater = false;
                    updateQuestion();
                }else{
                    if(mIsCheater){
                        mWhereCheated[mCurrentIndex] = true;
                    }
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    mIsCheater = false;
                    updateQuestion();
                }
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();

    }//end onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
            savedInstanceState.putBoolean(KEY_INDEXS, mIsCheater);
            savedInstanceState.putBooleanArray(KEY_INDEXX, mWhereCheated);
        }

        public void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            Log.i(TAG, "onRestoreInstanceState");
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEXS);
            mWhereCheated = savedInstanceState.getBooleanArray(KEY_INDEXX);
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart() called");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause() called");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume() called");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop() called");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy() called");
        }

}
