package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.NoteAdapter;
import com.elkanah.roemichs.utils.CommonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;

import static com.elkanah.roemichs.utils.CommonUtils.generatePDF;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {

    FloatingActionButton fabAddNote;
    private RecyclerView recyclerView;
    private Context context;

    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        context = getContext();

        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbarr);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Notes");

        recyclerView = v.findViewById(R.id.note_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NoteAdapter adapter = new NoteAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fabAddNote=v.findViewById(R.id.fabAddNoteList);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if it is teacher
                //Navigation.findNavController(v).navigate(R.id.action_noteFragment_to_addNoteFragment);

                //TODO request permission before generate
                generatePDF(recyclerView, context);
            }
        });


        return v;
    }
}
