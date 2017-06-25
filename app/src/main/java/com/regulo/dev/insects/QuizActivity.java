package com.regulo.dev.insects;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.regulo.dev.insects.data.Insect;
import com.regulo.dev.insects.data.Insects;
import com.regulo.dev.insects.views.AnswerView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements
        AnswerView.OnAnswerSelectedListener {
    private static final String TAG = QuizActivity.class.getSimpleName();

    //Number of quiz answers
    public static final int ANSWER_COUNT = 5;

    public static final String EXTRA_INSECTS = "insectList";
    public static final String EXTRA_ANSWER = "selectedInsect";
    public static final String CHECKED_ANSWER = "myAnswerSelection";

    private TextView mQuestionText;
    private TextView mCorrectText;
    private AnswerView mAnswerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupActionBar();
        mQuestionText = (TextView) findViewById(R.id.text_question);
        mCorrectText = (TextView) findViewById(R.id.text_correct);
        mAnswerSelect = (AnswerView) findViewById(R.id.answer_select);

        mAnswerSelect.setOnAnswerSelectedListener(this);

        Insects insects = (Insects) getIntent().getParcelableArrayListExtra(EXTRA_INSECTS);
        Insect selected = getIntent().getParcelableExtra(EXTRA_ANSWER);
        buildQuestion(insects.getInsectsList(), selected);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CHECKED_ANSWER, mAnswerSelect.getCheckedIndex());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAnswerSelect.setCheckedIndex(savedInstanceState.getInt(CHECKED_ANSWER));
    }

    public void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildQuestion(List<Insect> insects, Insect selected) {
        String question = getString(R.string.question_text, selected.name);
        mQuestionText.setText(question);

        //Load answer strings
        ArrayList<String> options = new ArrayList<>();
        for (Insect item : insects) {
            if(options.size() < ANSWER_COUNT){
               options.add(item.scientificName);
            }
        }
        mAnswerSelect.loadAnswers(options, selected.scientificName);
    }

    /* Answer Selection Callbacks */

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCorrectAnswerSelected() {
        updateResultText();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onWrongAnswerSelected() {
        updateResultText();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateResultText() {
        mCorrectText.setTextColor(mAnswerSelect.isCorrectAnswerSelected() ?
                getColor(R.color.colorCorrect) : getColor( R.color.colorWrong)
        );
        mCorrectText.setText(mAnswerSelect.isCorrectAnswerSelected() ?
                R.string.answer_correct : R.string.answer_wrong
        );
    }
}
