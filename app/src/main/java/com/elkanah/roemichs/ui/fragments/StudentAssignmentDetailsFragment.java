package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.AssignmentModel;
import com.elkanah.roemichs.db.NoteModel;
import com.elkanah.roemichs.ui.adapters.SubjectAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAssignmentDetailsFragment extends Fragment implements View.OnClickListener {
    CarouselView carouselView;
    private AssignmentModel model;
    int[] sampleImages = {R.drawable.portriatimg, R.drawable.portriatimg, R.drawable.landscapeimg, R.drawable.portriatimg, R.drawable.portriatimg};
    private TextView tvPostDate;
    private TextView tvSubDate;
    private TextView tvAssgnTitle;
    private TextView tvAssgnTeacher;
    private TextView tvAssgnBody;
    private Button button;
    private RecyclerView recyclerView;

    public StudentAssignmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_assignment_details, container, false);
        tvPostDate = v.findViewById(R.id.textView20);
        tvSubDate = v.findViewById(R.id.textView23);
        tvAssgnTitle = v.findViewById(R.id.textView25);
        tvAssgnTeacher = v.findViewById(R.id.textView21);
        tvAssgnBody = v.findViewById(R.id.textView26);
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        button = v.findViewById(R.id.button2);
        recyclerView = v.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        SubjectAdapter adapter = new SubjectAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        button.setOnClickListener(this);
            inflateCarousel();
        if(getArguments() != null){
            model = getArguments().getParcelable("ass");
            if(model != null){
                tvPostDate.setText(model.getPostDate());
                tvSubDate.setText(model.getSubmissionDate());
                tvAssgnTeacher.setText(model.getAssgnTitle());
                tvAssgnTitle.setText(model.getTeachersName());
                tvAssgnBody.setText(model.getAsshnBody());

            }
        }

        return v;
    }

    private  void inflateCarousel(){
        carouselView.setPageCount(sampleImages.length);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_studentAssignmentDetailsFragment_to_studentSubmitAssignmentFragment);
    }
}
