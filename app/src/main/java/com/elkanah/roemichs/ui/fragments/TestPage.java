package com.elkanah.roemichs.ui.fragments;

import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapter.PagingAdapter;
import com.elkanah.roemichs.ui.adapters.OptionAdapter;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.ui.model.TestQuestionModel;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

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
    public CountDownTimer countDownTimer;
    private ImageView testImage;
    //    private View timeBG_view;


    public TestPage() {
        // Required empty public constructor
    }

    public static TestPage newInstance() {
        Bundle args = new Bundle();

        TestPage fragment = new TestPage();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_test_page, container, false);

        questNumber = view.findViewById(R.id.paging_rcly);
        recyclerView = view.findViewById(R.id.rcyl_testOptions);
        testQuestion = view.findViewById(R.id.tv_testQuestion);
        testImage = view.findViewById(R.id.img_test_image);
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
            //Note that type = user_answer and value = questinID
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
        list.add(new TestQuestionModel("1", "Describe his id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Q", "https://media-exp1.licdn.com/dms/image/C4D03AQHR5uSjvBF_bw/profile-displayphoto-shrink_100_100/0?e=2159024400&v=beta&t=LaNCTEmfarsxvZdqE1FyG0q9hvUcsqtjBaMVj5RDwhk", getTest(), "1"));
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
//        option.add(new OptiontModel( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQZyNLAujH0V9Ey4eczI0m5luBz8IsWSsXIa19WHxy77PXRIqW0&usqp=CAU", "IMAGE"));


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        countDownTimer.cancel();
    }
}
