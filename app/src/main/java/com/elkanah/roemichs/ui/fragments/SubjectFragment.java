package com.elkanah.roemichs.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.SubjectAdapter;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    private AppCompatSpinner spinnerAccType;

    public SubjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subject, container, false);
        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("My Subjects");

        spinnerAccType = v.findViewById(R.id.appCompatSpinner);
        String[] acctType = {"RECEPTION", "KINDER GARTEN", "NRS 1","NRS 2", "PRM 1", "PRM 2","JSS 1", "JSS2", "SS3","SS1", "SS2", "SS3"};
        ArrayAdapter<String> acctAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, acctType);
        spinnerAccType.setAdapter(acctAdapter);
        acctAdapter.notifyDataSetChanged();
        spinnerAccType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    spinnerAccType.setBackgroundColor(Color.parseColor("#6c2067"));
                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = v.findViewById(R.id.subject_recycler);
        recyclerView.setHasFixedSize(true);
        SubjectAdapter adapter = new SubjectAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();


        return v;
    }
}
