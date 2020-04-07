package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.ui.model.TestQuestionModel;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestScore extends Fragment implements View.OnClickListener {
    private ArrayList<TestQuestionModel> questions;
    private ArrayList<OptiontModel> answers;
    private Executor executor;
    private TextView tv_incorrect;
    private TextView tv_correct;
    private TextView tv_unanswered;

    public TestScore() {
        // Required empty public constructor
    }

    public static TestScore newInstance(ArrayList<TestQuestionModel> list, ArrayList<OptiontModel> answers) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("test_question", list);
        args.putParcelableArrayList("answers", answers);
        TestScore fragment = new TestScore();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_test_score, container, false);
        tv_incorrect = view.findViewById(R.id.tv_incorrect_ans);
        tv_correct = view.findViewById(R.id.tv_correct_ans);
        tv_unanswered = view.findViewById(R.id.tv_unanswered);
        Button btn_finish = view.findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);
        if (getArguments()!=null){
            questions = getArguments().getParcelableArrayList("test_question");
            answers = getArguments().getParcelableArrayList("answers");
        }
        int correctAns = calculateScore(questions, answers);

        return view;
    }

    private int calculateScore(ArrayList<TestQuestionModel> questions, ArrayList<OptiontModel> answers) {
        int correct = 0;
//        executor.execute(()->{
        for(OptiontModel one: answers) {
            //Note that type = user answer and value = questinID
            TestQuestionModel two = questions.get(Integer.parseInt(one.value)-1);
            if (one.value.equals(two.testID)){
                if(one.type.equals(two.answer)){
                    correct++;
                }
            }
        }
//        });
        tv_correct.setText(String.valueOf(correct));
        tv_incorrect.setText(String.valueOf(answers.size()-correct));
        tv_unanswered.setText(String.valueOf(questions.size()-answers.size()));
        return correct;
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.student_Dashboard);
    }
}
