package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.model.StudentProfileModel;


public class ProfileFragment extends Fragment {

    private StudentProfileModel profileModel;
    TextView txtFirstName, txtSurname, txtUsername, txtClass, txtDateOfBirth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (getArguments() != null) {
            profileModel = getArguments().getParcelable(Constants.PROFILE_MODEL);
        }

        txtFirstName=view.findViewById(R.id.txtFirstNameProfile);
        txtSurname=view.findViewById(R.id.txtSurnameProfile);
        txtClass=view.findViewById(R.id.txtStudentClassProfile);
        txtUsername=view.findViewById(R.id.txtStudentUsernameProfile);

        txtFirstName.setText(profileModel.getFirst_name());
        txtSurname.setText(profileModel.getSurname());
        txtUsername.setText(profileModel.getStudent_id());
        txtClass.setText(profileModel.getStudent_class());

        return view;
    }
}
