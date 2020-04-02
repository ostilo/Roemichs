package com.elkanah.roemichs.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elkanah.roemichs.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSubmitAssignmentFragment extends Fragment implements View.OnClickListener {

    private Button btnViewSubmitted;
    private Button btnSubmit;
    CoordinatorLayout coordinatorLayout;
    public StudentSubmitAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_submit_assignment, container, false);
        coordinatorLayout = v.findViewById(R.id.parent_frame);
        CollapsingToolbarLayout collapsingToolbarLayout = v.findViewById(R.id.collapsing_toolbar_sa);
        //collapsingToolbarLayout.setTitle("The Mighty Hand of God");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar_sa);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Submit Assignment");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        btnViewSubmitted = v.findViewById(R.id.button3);
        btnSubmit = v.findViewById(R.id.button4);

        btnSubmit.setOnClickListener(this);
        btnViewSubmitted.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:
                btnViewSubmitted.setEnabled(false);
                break;

            case R.id.button4:
                    btnSubmit.setEnabled(true);
                    coordinatorLayout.setAlpha((float) 0.5);
                    loadAlertDailog("Are you sure you want to submit?","OK","NO");
                break;
        }
    }

    private void loadAlertDailog(String title, String positive, String negative){
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle(title);
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setButton(Dialog.BUTTON_POSITIVE, positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                coordinatorLayout.setAlpha((float) 1);
                dialog.dismiss();

            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                coordinatorLayout.setAlpha((float) 1);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
