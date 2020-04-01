package com.elkanah.roemichs.ui.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.DotsIndicatorDeco;
import com.elkanah.roemichs.ui.adapter.ActionAdapter;
import com.elkanah.roemichs.ui.adapter.ContentAdapter;
import com.elkanah.roemichs.ui.model.ActionModel;
import com.elkanah.roemichs.ui.model.ContentModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student_Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student_Dashboard extends Fragment {
    private RecyclerView actionRecycle;
    private RecyclerView contentRecycle;
    private ContentAdapter adapter;
    private MaterialCardView noteCard1;
    private CardView noteCard;
    private List<ImageView> dotsLinearLayout;

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
        noteCard = view.findViewById(R.id.notice_card);
        noteCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NotificationSlider newInstance = NotificationSlider.newInstance();
                newInstance.show(getChildFragmentManager(), "add_photo_dialog_fragment");
                return false;
            }
        });
        inflateRecycler();
        return view;
    }
    private void inflateRecycler() {
        actionRecycle.setAdapter(new ActionAdapter(getContext(),getActionList()));
        actionRecycle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        actionRecycle.setNestedScrollingEnabled(false);
        actionRecycle.setHasFixedSize(true);
        final int radius = 8;
        final int dotsHeight = 5;
        final int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.secondColor);
        actionRecycle.addItemDecoration(new DotsIndicatorDeco(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(actionRecycle);

        contentRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ContentAdapter(getContext(), getContents());
        contentRecycle.setAdapter(adapter);
    }


    private List<ContentModel> getContents() {
        List<ContentModel> list = new ArrayList<>();
        ContentModel test   = new ContentModel("Test", R.drawable.notes, "this id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        list.add(test);
        ContentModel subject = new ContentModel("Subject", R.drawable.homework, "Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        list.add(subject);
        ContentModel profile = new ContentModel("Profile", R.drawable.exam, "Lorem ipsum dolor sit amet, consectetur ullamcorper sed");
        list.add(profile);
        ContentModel messages = new ContentModel("Classroom", R.drawable.exam, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi ");
        list.add(messages);
        return list;
    }


    private List<ActionModel> getActionList() {
        List<ActionModel> list = new ArrayList<>();
        ActionModel note = new ActionModel("Note", R.drawable.notes);
        list.add(note);
        ActionModel assignment = new ActionModel("Assignment", R.drawable.homework);
        list.add(assignment);
        ActionModel exam = new ActionModel("Exam", R.drawable.exam);
        list.add(exam);
        return list;
    }
}
