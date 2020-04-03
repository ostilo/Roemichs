package com.elkanah.roemichs.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.StudentAssignmentAdapter;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAssignmentFragment extends Fragment {

    public StudentAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_student_assignment, container, false);
        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar_s);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Assignments");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        RecyclerView recyclerView = v.findViewById(R.id.student_assignment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StudentAssignmentAdapter assignmentAdapter = new StudentAssignmentAdapter(getContext());
        recyclerView.setAdapter(assignmentAdapter);
        assignmentAdapter.notifyDataSetChanged();


        return v;
    }
}
