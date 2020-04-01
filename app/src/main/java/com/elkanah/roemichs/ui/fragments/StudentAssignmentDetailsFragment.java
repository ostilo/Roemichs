package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.AssignmentModel;
import com.elkanah.roemichs.db.NoteModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAssignmentDetailsFragment extends Fragment {
    CarouselView carouselView;
    private AssignmentModel model;
    int[] sampleImages = {R.drawable.portriatimg, R.drawable.portriatimg, R.drawable.landscapeimg, R.drawable.portriatimg, R.drawable.portriatimg};

    public StudentAssignmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_assignment_details, container, false);
        TextView tvPostDate = v.findViewById(R.id.textView20);
        TextView tvSubDate = v.findViewById(R.id.textView23);
        TextView tvAssgnTitle = v.findViewById(R.id.textView25);
        TextView tvAssgnTeacher = v.findViewById(R.id.textView21);
        TextView tvAssgnBody = v.findViewById(R.id.textView26);
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
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
}
