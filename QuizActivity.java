package com.example.recyclearquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private Button mDoneButton;
    public int score = 0;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_1,true),
            new Question(R.string.question_2,true),
            new Question(R.string.question_3,false),
            new Question(R.string.question_4,false),
            new Question(R.string.question_5,true),
            new Question(R.string.question_6,false),
            new Question(R.string.question_7,false),
            new Question(R.string.question_8,true),
            new Question(R.string.question_9,true),
            new Question(R.string.question_10,false),
    };

    private int mCurrentIndex = 0;

    private void updateQuestion(){
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            if (mCurrentIndex == 9){
                mCurrentIndex = 9;
            }else {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
            }
            updateQuestion();
            score = score + 1;
        }else{
            messageResId = R.string.incorrect_toast;
            score = score + 0;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageButton mHomeButton = (ImageButton)findViewById(R.id.home_button);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExitActivity();
            }
        });

        mDoneButton = (Button)findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this,"Your score is " + score +" / 10",Toast.LENGTH_LONG).show();
                openStartActivity();
            }
        });


        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if (mCurrentIndex == 9){
                    mCurrentIndex = 9;
                }else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                }
                updateQuestion();
            }
        });

        mPreviousButton = (Button)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex == 0){
                    mCurrentIndex = 0;

                }else{
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                }

                updateQuestion();
            }
        });



        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }
    public void openExitActivity(){
        Intent intent = new Intent(this,ExitActivity.class);
        startActivity(intent);
    }

    public void openStartActivity(){
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        ;
    }
}
