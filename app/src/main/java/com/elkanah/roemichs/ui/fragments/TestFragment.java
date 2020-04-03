package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elkanah.roemichs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_test, container, false);
        Button btn_start = view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.testPage);
    }
}
