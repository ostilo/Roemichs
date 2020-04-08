package com.elkanah.roemichs.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.StudentSelectedImageAssgnAdapter;
import com.elkanah.roemichs.utils.CommonUtils;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opensooq.supernova.gligar.GligarPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elkanah.roemichs.utils.CommonUtils.textIsEmpty;
import static com.elkanah.roemichs.utils.CommonUtils.textOfEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTest extends Fragment implements View.OnClickListener{

    private static final int PICKER_REQUEST_CODE = 22;
    private static final int READ_PERMISSION = 33 ;
    private static final int WRITE_PERMISSION = 44 ;
    private Toolbar toolbar;
    private ArrayList<String> newMe;
    private int imgCount;
    private StudentSelectedImageAssgnAdapter imageAdapter;
    private Context context;
    private LinearLayout layout;
    private Button btnGoToAddQuestn;

    public CreateTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_test, container, false);

        context = getContext();

        btnGoToAddQuestn=view.findViewById(R.id.btnGoToAddTestQuestn);
        btnGoToAddQuestn.setOnClickListener(this);

        toolbar=view.findViewById(R.id.toolbarCrtTest);
        layout = view.findViewById(R.id.linLayImgDescrCrtTest);

        setToolBar();
        return view;
    }

    private void setToolBar() {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Test");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGoToAddTestQuestn:
                Navigation.findNavController(v).navigate(R.id.create_test_to_add_question);
                break;
        }
    }

}
