package com.elkanah.roemichs.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.ClassType;
import com.elkanah.roemichs.ui.adapters.SubjectAdapter;
import com.elkanah.roemichs.ui.viewmodels.SubjectViewmodel;
import com.elkanah.roemichs.utils.ProgressFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    private AppCompatSpinner spinnerClassType;
    private SearchView searchView;
    private SubjectAdapter adapter;
    private SubjectViewmodel subjectViewmodel;
    private static final String CLASS_TYPE = "200";
    private static final String SUBJECT_TYPE = "300";
    private ArrayAdapter<ClassType> classAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private View view;

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search by title...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subject, container, false);
        view = v.findViewById(android.R.id.content);
        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("My Subjects");
        dialog = new ProgressDialog(getContext());

        subjectViewmodel = new ViewModelProvider(this).get(SubjectViewmodel.class);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                Navigation.findNavController(v).navigateUp();

            }
        });
        fetchSubjectRecycler();
        //setSpinner();
        //inflateSubjectRecycler();
        observeViewModel();

        //todo for the classTYpe Spinner
        spinnerClassType = v.findViewById(R.id.appCompatSpinner);
        String[] acctType = {"RECEPTION", "KINDER GARTEN", "NRS 1","NRS 2", "PRM 1", "PRM 2","JSS 1", "JSS2", "SS3","SS1", "SS2", "SS3"};
        ArrayAdapter<String> acctAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, acctType);
        spinnerClassType.setAdapter(acctAdapter);
        acctAdapter.notifyDataSetChanged();
        spinnerClassType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                   // spinnerAccType.setBackgroundColor(Color.parseColor("#6c2067"));
                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //todo for the subject recycler
        recyclerView = v.findViewById(R.id.subject_recycler);
        recyclerView.setHasFixedSize(true);
        adapter = new SubjectAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();


        return v;
    }

    private void inflateSubjectRecycler() {
        subjectViewmodel.listSubjectModel.observe(getViewLifecycleOwner(),subjectModels -> {
            if(subjectModels != null && subjectModels.size() > 0){
                try {
                    adapter = new SubjectAdapter(getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void observeViewModel() {
        subjectViewmodel.jsonResponse.observe(getViewLifecycleOwner(), jsonResponse -> {
            if(jsonResponse != null && jsonResponse.code.equals("00")){
                if(jsonResponse.requestCode.equals(CLASS_TYPE)){
                    if(!subjectViewmodel.validateClassType(jsonResponse.jsonMessage)){

                    }
                }
            }
        });
    }

    private void setSpinner() {
        try {
            subjectViewmodel.listClassType.observe(getViewLifecycleOwner(), classType -> {
                if(classType != null && classType.size() == 0){
                    subjectViewmodel.getClassType(CLASS_TYPE);

                }
                classAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classType);
                spinnerClassType.setAdapter(classAdapter);
                classAdapter.notifyDataSetChanged();
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void fetchSubjectRecycler(){
        ProgressFragment progressFragment = new ProgressFragment();
        progressFragment.show(getChildFragmentManager(),"Aede");
        subjectViewmodel.fetchSubjectRecycler("text",SUBJECT_TYPE);
    }

      private CountDownTimer getCountDown(){
        return new CountDownTimer(7000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(),"Your subject has been retrieved",Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        }.start();
      }
}
