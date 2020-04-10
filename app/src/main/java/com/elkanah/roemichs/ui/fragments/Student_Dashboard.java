package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapter.TeacherActionAdapter;
import com.elkanah.roemichs.ui.adapter.TeacherContentAdapter;
import com.elkanah.roemichs.ui.model.DotsIndicatorDeco;
import com.elkanah.roemichs.ui.adapter.ActionAdapter;
import com.elkanah.roemichs.ui.adapter.ContentAdapter;
import com.elkanah.roemichs.ui.model.ActionModel;
import com.elkanah.roemichs.ui.model.ContentModel;
import com.elkanah.roemichs.ui.model.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student_Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student_Dashboard extends Fragment implements View.OnClickListener {
    private RecyclerView actionRecycle;
    private RecyclerView contentRecycle;
    private ContentAdapter studentAdapter;
    private TeacherContentAdapter teacherAdapter;
    private CardView noteCard;
    private List<ImageView> dotsLinearLayout;
    private boolean mTablet;

    public Student_Dashboard() {
        // Required empty public constructor
    }

    public static Student_Dashboard newInstance(String user) {
        Student_Dashboard fragment = new Student_Dashboard();
        Bundle args = new Bundle();
        args.putString("USER", user);
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
        ImageView menu = view.findViewById(R.id.menu_select);
        menu.setOnClickListener(this);
        noteCard = view.findViewById(R.id.notice_card);
        if (getArguments()!=null){
            String presentUser = getArguments().getString("USER");
            if(presentUser.equals("TEACHER")){
                inflateRecycler("TEACHER");
            }else if(presentUser.equals("STUDENT")){
                inflateRecycler("STUDENT");
            }
        }


        noteCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NotificationSlider newInstance = NotificationSlider.newInstance();
                newInstance.show(getChildFragmentManager(), "notification_dialog_fragment");
                return false;
            }
        });

        return view;
    }
    private void inflateRecycler(String presentUSER) {
        if(presentUSER.equals("STUDENT")){
            actionRecycle.setAdapter(new ActionAdapter(getContext(),getActionList()));
            studentAdapter = new ContentAdapter(getContext(), getContents());
        }else if (presentUSER.equals("TEACHER")){
            actionRecycle.setAdapter(new TeacherActionAdapter(getContext(),getActionList()));
            teacherAdapter = new TeacherContentAdapter(getContext(), getContents());
        }
        actionRecycle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        actionRecycle.setNestedScrollingEnabled(false);
        actionRecycle.setHasFixedSize(true);
        final int radius = 8;
        final int dotsHeight = 5;
        final int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.secondColor);
        actionRecycle.addItemDecoration(new DotsIndicatorDeco(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(actionRecycle);


                ScreenUtil util = new ScreenUtil(getActivity());
                mTablet = (util.getDpWidth() >= 600);
                if (mTablet ==true) {
                    contentRecycle.setLayoutManager(new GridLayoutManager(getContext(), 4));

                }else {
                    contentRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
                }
                if (presentUSER.equals("STUDENT"))contentRecycle.setAdapter(studentAdapter); else contentRecycle.setAdapter(teacherAdapter);
    }


    private List<ContentModel> getContents() {
        List<ContentModel> list = new ArrayList<>();
        ContentModel test   = new ContentModel("Test", R.drawable.exam, "this id the assign Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        list.add(test);
        ContentModel subject = new ContentModel("Subject", R.drawable.blackboard, "Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed");
        list.add(subject);
        ContentModel profile = new ContentModel("Profile", R.drawable.exam, "Lorem ipsum dolor sit amet, consectetur ullamcorper sed");
        list.add(profile);
        ContentModel messages = new ContentModel("Classroom", R.drawable.blackboard, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisi ");
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.menu_select){
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            MenuInflater inflater = popupMenu.getMenuInflater();
            String presentUser = "TEACHER";
            if (presentUser.equals("TEACHER")){
            inflater.inflate(R.menu.dashboard_menu, popupMenu.getMenu());
            popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_logout) {
                        NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
                        return true;
                    }
                    if (item.getItemId() == R.id.change_pass) {
                    // TODO: 4/9/20
                        return true;
                    }

                    return false;
                });
            }else if (presentUser.equals("STUDENT")) {
                inflater.inflate(R.menu.student_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_logout) {
                        NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
                        return true;
                    }
                    if (item.getItemId() == R.id.change_pass) {
                        // TODO: 4/9/20
                        return true;
                    }

                    return false;
                });
            }

        }
    }
}
