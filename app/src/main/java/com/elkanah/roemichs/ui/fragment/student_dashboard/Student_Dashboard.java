package com.elkanah.roemichs.ui.fragment.student_dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student_Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student_Dashboard extends Fragment {
    private RecyclerView actionRecycle;
    private RecyclerView contentRecycle;
    private ContentAdapter adapter;

    public Student_Dashboard() {
        // Required empty public constructor
    }

    public static Student_Dashboard newInstance() {
        Student_Dashboard fragment = new Student_Dashboard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);
        actionRecycle = view.findViewById(R.id.action_recycler);
        contentRecycle = view.findViewById(R.id.contentRecycler);
        inflateRecycler();
        return view;
    }
    private void inflateRecycler() {
        actionRecycle.setAdapter(new ActionAdapter(getContext(),getActionList()));
        actionRecycle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));

        contentRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ContentAdapter(getContext(), getContents());
        contentRecycle.setAdapter(adapter);
    }

    private List<ContentModel> getContents() {
        List<ContentModel> list = new ArrayList<>();
        ContentModel test   = new ContentModel("Test", R.drawable.notes, "this id the assign Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi arcu, ullamcorper sed");
        list.add(test);
        ContentModel subject = new ContentModel("Subject", R.drawable.homework, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi arcu, ullamcorper sed");
        list.add(subject);
        ContentModel profile = new ContentModel("Profile", R.drawable.exam, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi arcu, ullamcorper sed");
        list.add(profile);
        ContentModel messages = new ContentModel("Messages", R.drawable.exam, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi arcu, ullamcorper sed");
        list.add(messages);
        return list;
    }


    private List<ActionModel> getActionList() {
        List<ActionModel> list = new ArrayList<>();
        ActionModel note = new ActionModel("CASH", R.drawable.notes);
        list.add(note);
        ActionModel assignment = new ActionModel("CASH", R.drawable.homework);
        list.add(assignment);
        ActionModel exam = new ActionModel("ACCOUNT", R.drawable.exam);
        list.add(exam);
        return list;
    }
}
