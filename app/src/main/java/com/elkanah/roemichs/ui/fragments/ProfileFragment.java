package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.classroom_and_chats.Constants;
import com.elkanah.roemichs.ui.model.StudentProfileModel;

import static com.elkanah.roemichs.classroom_and_chats.Constants.PROFILE_MODEL;

public class ProfileFragment extends Fragment {

    private StudentProfileModel profileModel;
    TextView txtFirstName, txtSurname, txtUsername, txtClass, txtDateOfBirth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(StudentProfileModel studentProfileModel) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PROFILE_MODEL, studentProfileModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profileModel = getArguments().getParcelable(PROFILE_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtFirstName=view.findViewById(R.id.txtFirstNameProfile);
        txtSurname=view.findViewById(R.id.txtSurnameProfile);
        txtClass=view.findViewById(R.id.txtStudentClassProfile);
        txtUsername=view.findViewById(R.id.txtStudentUsernameProfile);

//        txtFirstName.setText(profileModel.getFirst_name());
//        txtSurname.setText(profileModel.getSurname());
//        txtUsername.setText(profileModel.getStudent_id());
//        txtClass.setText(profileModel.getStudent_class());

        return view;
    }
}
