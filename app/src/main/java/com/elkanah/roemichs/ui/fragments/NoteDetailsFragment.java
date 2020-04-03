package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.NoteModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment implements View.OnClickListener {


    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_details, container, false);
        CollapsingToolbarLayout collapsingToolbarLayout = v.findViewById(R.id.collapsing_toolbar_d);
        //collapsingToolbarLayout.setTitle("The Mighty Hand of God");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        TextView tvTeachersName = v.findViewById(R.id.textView16);
        TextView tvNoteTopic = v.findViewById(R.id.textView17);
        TextView tvNoteDate = v.findViewById(R.id.textView18);
        TextView tvNoteBody = v.findViewById(R.id.textView19);

        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar_d);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        CardView cardView = v.findViewById(R.id.tvGotoAssignment);
        cardView.setOnClickListener(this);

        if(getArguments() != null){
            NoteModel model = getArguments().getParcelable("ade");
            if(model != null){
                tvTeachersName.setText(model.getTeachersname());
                tvNoteTopic.setText(model.getNoteTitle());
                tvNoteDate.setText(model.getWeekNumber());
                tvNoteBody.setText(model.getNoteBody());

            }
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_noteDetailsFragment_to_studentAssignmentFragment);
    }
}
