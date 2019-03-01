package net.jws7.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private QuizModel mQuizModel = new QuizModel();
    private TextView mQuestionTextView;
    private ProgressBar mProgressBar;
    private Toast mToastMessage;
    private TextView mScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScoreTextView = findViewById(R.id.score);

        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);

        if(savedInstanceState != null){
            mQuizModel.setScore(savedInstanceState.getInt("scoreKey"));
            mQuizModel.setIndex(savedInstanceState.getInt("questionIndex"));
        }
        updateScreen();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quizzler", "true button onclick");
                //Toast.makeText(getApplicationContext(), "Checking answer", Toast.LENGTH_SHORT).show();

                feedBackOnAnswer(true);
                goToNextQuestion();
                updateScreen();

            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("Quizzler","false button onclick");
                //Toast.makeText(getApplicationContext(), "Checking answer", Toast.LENGTH_SHORT).show();

                feedBackOnAnswer(false);
                goToNextQuestion();
                updateScreen();
            }
        });
    }


    // TODO: Add feedbackOnAnswer() method:
    private void feedBackOnAnswer(boolean userSelection){
        Log.d("Quizzler", "feedBackOnAnswer() method");

        if(mToastMessage!=null) mToastMessage.cancel();

        boolean isCorrect = mQuizModel.checkAnswer(false);
        if (isCorrect) {
            String message = Integer.toString(R.string.correct_toast);
            Log.d("Quizzler", message);
            mToastMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            String message = Integer.toString(R.string.incorrect_toast);
            Log.d("Quizzler", message);
            mToastMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        }
        mToastMessage.show();
    }

    public void updateScreen(){
        Log.d("Quizzler", "updateScreen() method");

        int questionText = mQuizModel.getQuestionText();
        mQuestionTextView.setText(questionText);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mProgressBar.setProgress(mQuizModel.percentComplete(), true);
        }
        else {
            mProgressBar.setProgress(mQuizModel.percentComplete());
        }

        String scoreString = "Score " + mQuizModel.getScore() + "/ " + mQuizModel.NR_OF_QUESTIONS + " ";
        mScoreTextView.setText(scoreString);

    }

    public void goToNextQuestion(){
        mQuizModel.nextQuestion();
        if(mQuizModel.isBeginning()){

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Game Over");
            alert.setMessage("You scored: " + mQuizModel.getScore() + " points!");
            alert.setCancelable(false);

            alert.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mQuizModel.reset();
                    updateScreen();
                }
            });

            alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            alert.show();
        }
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("scoreKey", mQuizModel.getScore());
        outState.putInt("questionIndex", mQuizModel.getIndex());
    }
}