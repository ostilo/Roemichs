package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import org.w3c.dom.Text;

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
    private TextView tv_seconds;
    private TextView tv_mts;
    private TextView tv_hr;
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
        tv_hr = view.findViewById(R.id.tv_hr);
        tv_mts = view.findViewById(R.id.tv_minutes);
        tv_seconds = view.findViewById(R.id.tv_seconds);
//        timeBG_view = view.findViewById(R.id.timeBG_view);
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

//                if (answers.contains(test)) {
//                    Log.i("exist", test.value);
//                } else {
//                    answers.add(test);
//                }
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
    }


    private void inflateOptions() {
        testQuestion.setText("WHat is noun? this id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        recyclerView.setAdapter(new OptionAdapter(getContext(), getTest()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
//            questNumber.getAdapter().notifyDataSetChanged();
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
        DecimalFormat formatter = new DecimalFormat("00");

        if(hrs > 0){
            new CountDownTimer(hrs * 60 * 60 *1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tv_seconds.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                    tv_mts.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                    tv_hr.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                }
                public void onFinish() {
                    if(mins > 0 ){
                        new CountDownTimer(mins * 10000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                tv_seconds.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                                tv_mts.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                                tv_hr.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                            }
                            @Override
                            public void onFinish() {
//                                timeBG_view.setBackgroundColor(getContext().getResources().getColor(R.color.error));
                                tv_seconds.setText("00");
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("test_question", list);
                                bundle.putParcelableArrayList("answers", answers);
                                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.testResult, bundle);
                            }
                        }.start();
                    }else {
//                        timeBG_view.setBackgroundColor(getContext().getResources().getColor(R.color.error));
                        tv_seconds.setText("00");
                    }
                }
            }.start();
        }else if(mins>0){
            new CountDownTimer(mins * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_seconds.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                    tv_mts.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                    tv_hr.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                }
                @Override
                public void onFinish() {
//                    timeBG_view.setBackgroundColor(getContext().getResources().getColor(R.color.error));
                    tv_seconds.setText("00");
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("test_question", list);
                    bundle.putParcelableArrayList("answers", answers);
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.testResult, bundle);

                }
            }.start();
        }else if(sec>0){
            new CountDownTimer(sec * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_seconds.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                    tv_mts.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                    tv_hr.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                }
                @Override
                public void onFinish() {
//                    timeBG_view.setBackgroundColor(getContext().getResources().getColor(R.color.error));
                    tv_seconds.setText("00");
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("test_question", list);
                    bundle.putParcelableArrayList("answers", answers);
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.testResult, bundle);

                }
            }.start();
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
            outState.putInt("HOURS", Integer.valueOf(tv_hr.getText().toString()));
            outState.putDouble("MINUTES", Integer.valueOf(tv_mts.getText().toString()));
            outState.putDouble("SECONDS", Integer.valueOf(tv_seconds.getText().toString()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState!=null) {
                startTimer(savedInstanceState.getInt("HOURS"), savedInstanceState.getInt("MINUTES"), savedInstanceState.getInt("SECONDS"));
            }else {
                startTimer(00, 01, 00);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
