package com.elkanah.roemichs.ui.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.MainActivity;
import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.OptionAdapter;
import com.elkanah.roemichs.ui.OptionSelect;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.ui.model.TestQuestionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestPage extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView testQuestion;
    public static ArrayList<OptiontModel> answers;
    private TextView next;
    private TextView previous;
    private int index = 0;
    private ArrayList<TestQuestionModel> list;
    private OptiontModel test;


    public TestPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_test_page, container, false);
        recyclerView = view.findViewById(R.id.rcyl_testOptions);
        testQuestion = view.findViewById(R.id.tv_testQuestion);
        next = view.findViewById(R.id.tv_next);
        previous = view.findViewById(R.id.tv_previous);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        answers = new ArrayList<>();
//        inflateOptions();
        inflateQuestions();
        loadQuestion(index);
        enableNav();
        return view;
    }

    private void enableNav() {
        if(index==list.size()-1){ next.setEnabled(false); }else {next.setEnabled(true);}
        if(index==0){ previous.setEnabled(false); }else {previous.setEnabled(true);}
    }

    private void loadQuestion(int pos) {
        TestQuestionModel model = list.get(pos);
        testQuestion.setText(model.questionText);
        OptionAdapter adapter = new OptionAdapter(getContext(),model.testID, model.options);
        adapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String viewItem, int position) {
                test = new OptiontModel(viewItem, Integer.toString(position));

//                if (answers.contains(test)) {
//                    Log.i("exist", test.value);
//                } else {
//                    answers.add(test);
//                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void inflateQuestions() {
        list = new ArrayList<>();
        list.add(new TestQuestionModel("1", "Describe his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "1"));
        list.add(new TestQuestionModel("2", "Explain his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "2"));
        list.add(new TestQuestionModel("3", "Zxpanciate his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "4"));
        list.add(new TestQuestionModel("4", "Distinguish his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "3"));
        list.add(new TestQuestionModel("5", "What ever his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "1"));
    }


    private void inflateOptions() {
        testQuestion.setText("WHat is noun? this id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        recyclerView.setAdapter(new OptionAdapter(getContext(),"1", getTest()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private ArrayList<OptiontModel> getTest() {
        ArrayList<OptiontModel> option = new ArrayList<>();
        option.add(new OptiontModel( "Name", "TEXT"));
        option.add(new OptiontModel( "School", "TEXT"));
        option.add(new OptiontModel( "Place", "TEXT"));
        option.add(new OptiontModel( "Office", "TEXT"));

        return option;
    }
    private void updateList(){
        if(test!=null) {
            ArrayList<OptiontModel> ans = answers;
            for (OptiontModel item : ans) {
                if (item.value.equals(test)) {
                    answers.remove(item);
                    answers.add(test);
                } else {
                    answers.add(test);
                }
            }
            if (answers.size() == 0) answers.add(test);
        }
    }

    @Override
    public void onClick(View v) {
        updateList();
        if(v.getId()== R.id.tv_next) {
            if(index<list.size()){
                index++;
                loadQuestion(index);
            }
        }else if (v.getId()== R.id.tv_previous){
            if(index!=0){
                index--;
                loadQuestion(index);
            }
        }
        enableNav();
    }
}
