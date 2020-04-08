package com.elkanah.roemichs.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapter.PagingAdapter;
import com.elkanah.roemichs.ui.adapters.OptionAdapter;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.ui.model.TestQuestionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestPage extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView testQuestion;
    public static ArrayList<OptiontModel> answers;
    private TextView next;
    private TextView previous;
    private TextView finish;
    private int index = 0;
    private ArrayList<TestQuestionModel> list;
    private OptiontModel test;
    private String questionID;
    private RecyclerView questNumber;
    private TextView tv_time;
    private TextView tv_mts;
    private TextView tv_hr;
    private CountDownTimer countDownTimer;
//    private View timeBG_view;


    public TestPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_test_page, container, false);

        questNumber = view.findViewById(R.id.paging_rcly);
        recyclerView = view.findViewById(R.id.rcyl_testOptions);
        testQuestion = view.findViewById(R.id.tv_testQuestion);
        tv_time = view.findViewById(R.id.tv_time);
        next = view.findViewById(R.id.tv_next);
        previous = view.findViewById(R.id.tv_previous);
        finish = view.findViewById(R.id.tv_fiinish);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        finish.setOnClickListener(this);
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
        questionID = model.testID;
        OptionAdapter adapter = new OptionAdapter(getContext(), model.options);
        adapter.setOnItemClickListener(position -> {
            //Note that type = user answer and value = questinID
            test = new OptiontModel(questionID, Integer.toString(position));
        });
        PagingAdapter pageAdapter = new PagingAdapter(getContext(), 5);
        pageAdapter.setOnItemClickListener(position -> {
            loadQuestion(position);
            index= position;
            updateList();
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        questNumber.setAdapter(pageAdapter);
        questNumber.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));

        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
            questNumber.getAdapter().notifyDataSetChanged();
        }
    }

    private void inflateQuestions() {
        list = new ArrayList<>();
        list.add(new TestQuestionModel("1", "Describe his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "1"));
        list.add(new TestQuestionModel("2", "Explain his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "2"));
        list.add(new TestQuestionModel("3", "Zxpanciate his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "4"));
        list.add(new TestQuestionModel("4", "Distinguish his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "3"));
        list.add(new TestQuestionModel("5", "What ever his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "1"));
        list.add(new TestQuestionModel("6", "OOOOOOOOOO ever his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "", getTest(), "1"));

    }


    private ArrayList<OptiontModel> getTest() {
        ArrayList<OptiontModel> option = new ArrayList<>();
        option.add(new OptiontModel( "Name", "TEXT"));
        option.add(new OptiontModel( "School", "TEXT"));
        option.add(new OptiontModel( "Place", "TEXT"));
        option.add(new OptiontModel( "Office", "TEXT"));
//        option.add(new OptiontModel( "data:image/jpeg;base64,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", "IMAGE"));


        return option;
    }
    private void updateList(){
            if (test != null) {
                ArrayList<OptiontModel> ans = answers;
                if (ans.size() != 0) {
                    for (OptiontModel item : ans) {
                        if (item.value.equals(test.value)) {
                                answers.set(ans.indexOf(item), test);
                        }
                    }
                    answers.add(test);
                } else {
                    answers.add(test);
                }
                ans = null;
            }

            test = null;
    }

    private void startTimer(int hrs, int mins, int sec) {
        DecimalFormat f = new DecimalFormat("00");
        int time = ( hrs ==0? 0 : hrs * 60 * 60 * 1000) + ( mins ==0? 0 : mins * 60 * 1000) + ( sec ==0? 0 : sec * 1000);
            new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    long seconds = (millisUntilFinished / 1000) % 60;
                    long minutes =((millisUntilFinished / 1000) / 60) % 60;
                    long hours = ((millisUntilFinished / 1000) / 3600);
                    if(millisUntilFinished<=30000) tv_time.setTextColor(Color.parseColor("#D85B15"));
                    tv_time.setText(f.format(hours) + ":" + f.format(minutes) + ":" + f.format(seconds));
                }
                public void onFinish() {
                                tv_time.setText("00:00:00");
                    updateList();
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("test_question", list);
                                bundle.putParcelableArrayList("answers", answers);
                                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.testResult, bundle);
                    Log.i("nav", getParentFragment().toString());
                    //2020-04-08 21:53:33.906 4172-4172/com.elkanah.roemichs I/nav: NavHostFragment{f8612b5} (1aa8b512-9299-4138-a626-8a36bae93ce4) id=0x7f0a01e9}
                }
            }.start();
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
        }else if (v.getId()== R.id.tv_fiinish){
            ConfirmationDialog dialog = ConfirmationDialog.newInstance("Are you sure you want to submit?", list, answers);
            dialog.show(getChildFragmentManager(), "message");
            dialog.setCancelable(false);
        }
        enableNav();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString("TIME", tv_time.getText().toString());
            countDownTimer.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState!=null) {
                String[] time =  savedInstanceState.getString("TIME").split(":");
                int hr = Integer.parseInt(time[0]);
                int mts = Integer.parseInt(time[1]);
                int secs = Integer.parseInt(time[2]);
                startTimer(hr, mts, secs);
            }else {
                startTimer(00, 00, 30);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
