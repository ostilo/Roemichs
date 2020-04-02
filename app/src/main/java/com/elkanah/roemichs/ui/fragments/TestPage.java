package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.OptionAdapter;
import com.elkanah.roemichs.ui.model.OptionSelect;
import com.elkanah.roemichs.ui.model.OptiontModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestPage extends Fragment implements OptionSelect {

    private RecyclerView recyclerView;
    private TextView testQuestion;

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
        inflateOptions();
        return view;
    }

    private void inflateOptions() {
        testQuestion.setText("WHat is noun? this id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedthis id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        recyclerView.setAdapter(new OptionAdapter(getContext(),"1", getTest()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
    }

    private ArrayList<OptiontModel> getTest() {
        ArrayList<OptiontModel> option = new ArrayList<>();
        option.add(new OptiontModel( "1", "Name", "TEXT"));
        option.add(new OptiontModel( "2", "School", "TEXT"));
        option.add(new OptiontModel( "3", "Place", "TEXT"));
        option.add(new OptiontModel( "4", "Office", "TEXT"));

        return option;
    }

    @Override
    public void onSelect(OptiontModel selectedAnswer) {
        Toast.makeText(getContext(), selectedAnswer.id, Toast.LENGTH_LONG).show();
    }
}
