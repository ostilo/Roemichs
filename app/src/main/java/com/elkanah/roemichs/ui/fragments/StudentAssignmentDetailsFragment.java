package com.elkanah.roemichs.ui.fragments;

import android.graphics.Color;
import android.opengl.Matrix;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.AssignmentModel;
import com.elkanah.roemichs.db.view.AssignmentResponseImageData;
import com.elkanah.roemichs.ui.adapters.AssignmentImagesAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAssignmentDetailsFragment extends Fragment implements View.OnClickListener {
    private AssignmentModel model;
    private TextView tvPostDate;
    private TextView tvSubDate;
    private TextView tvAssgnTitle;
    //todo
    private TextView tvAssgnTeacher;
    private TextView tvAssgnBody;
    private Button button;
    private RecyclerView recyclerView;
    List<AssignmentResponseImageData> data = AssignmentResponseImageData.getAssignmentImages();
    ScaleGestureDetector scaleGestureDetector;
    Matrix matrix;
    public StudentAssignmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_assignment_details, container, false);
        tvSubDate = v.findViewById(R.id.textView23);
        tvAssgnTitle = v.findViewById(R.id.textView25);
        tvAssgnTeacher = v.findViewById(R.id.textView21);
        tvAssgnBody = v.findViewById(R.id.textView26);
        button = v.findViewById(R.id.button2);
        recyclerView = v.findViewById(R.id.recyclerView);

        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbarSAS);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Assignment");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });


        recyclerView.setHasFixedSize(true);
        AssignmentImagesAdapter adapter = new AssignmentImagesAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        matrix = new Matrix();
//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector());

//        ViewPager viewPager = (ViewPager)v.findViewById(R.id.cvViewPager);
//        CustomViewPager cusAdapter = new CustomViewPager(getContext());
//        viewPager.setAdapter(cusAdapter);
        button.setOnClickListener(this);

        if(getArguments() != null){
            model = getArguments().getParcelable("ass");
            if(model != null){
                tvSubDate.setText(model.getSubmissionDate());
                tvAssgnTeacher.setText(model.getAssgnTitle());
                tvAssgnTitle.setText(model.getTeachersName());
                tvAssgnBody.setText(model.getAsshnBody());

            }
        }

        return v;
    }


    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_studentAssignmentDetailsFragment_to_studentSubmitAssignmentFragment);
    }
}
