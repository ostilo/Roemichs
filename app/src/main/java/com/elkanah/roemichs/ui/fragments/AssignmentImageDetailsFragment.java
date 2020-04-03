package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.view.AssignmentResponseImageData;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentImageDetailsFragment extends Fragment {

    public AssignmentImageDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_assignment_image_details, container, false);
        ImageView imageView = v.findViewById(R.id.assimageedetails);
        if(getArguments() != null){

            AssignmentResponseImageData    model = getArguments().getParcelable("me");
            if(model != null){
                imageView.setImageResource(model.getImages());
            }
        }
        return v;
    }
}
