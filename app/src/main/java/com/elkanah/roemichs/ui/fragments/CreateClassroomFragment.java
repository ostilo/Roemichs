package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.viewmodels.CreateClassroomViewModel;

import java.util.List;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;

public class CreateClassroomFragment extends Fragment implements View.OnClickListener {

    private static final String SESSION_LIST = "SessionList";
    private CreateClassroomViewModel createClassroomViewModel;
    private Button btnCreateCls;
    private String CHAT_USER, CHAT_WITH;
    private Context context;
    private AutoCompleteTextView spClass, spWeek;
    private ConstraintLayout constraintLayout;
    private EditText edtSubject, edtTopic, edtDurHrs, edtDurMins;
    private ArrayAdapter<ClassModel> sessionAdapter;
    public static List<ClassModel> classModelsList;
    public static String spClassText;
    private ProgressBar loading;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_classroom_fragment, container, false);
        context = getContext();
        createClassroomViewModel = ViewModelProviders.of(this).get(CreateClassroomViewModel.class);

        if (getArguments() != null) {
            CHAT_USER = getArguments().getString(CHAT_USER_KEY);
            CHAT_WITH = getArguments().getString(Constants.CHAT_WITH_KEY);
        }

        setViewById(view);
        setToolBar();
        setSpinners();
        checkSpinnerForEmpty();
        setListeners();
        observeViewModel();
        return view;
    }

    private void setViewById(View view) {
        toolbar=view.findViewById(R.id.toolbarCrtClass);
        btnCreateCls = view.findViewById(R.id.btnCreateClass);
        spClass = view.findViewById(R.id.tv_spClass_crtClass);
        spWeek = view.findViewById(R.id.spWeek_crtCls);
        constraintLayout = view.findViewById(R.id.constrCrtCls);
        edtSubject = view.findViewById(R.id.txtSubjectCrtCls);
        edtTopic = view.findViewById(R.id.txtTopic_crtCls);
        edtDurHrs = view.findViewById(R.id.txtDurHrs_crtCls);
        edtDurMins = view.findViewById(R.id.txtDurMins_crtCls);
        loading = view.findViewById(R.id.loading_crtCls);
    }

    private void setToolBar() {
        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create New Classroom");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();            }
        });
    }

    private void setListeners() {
        btnCreateCls.setOnClickListener(this);
    }

    private void observeViewModel() {
        createClassroomViewModel.jsonResponse.observe(getViewLifecycleOwner(), jsonResponse -> {
            if (sessionAdapter.getCount() == 0 /*|| accountAdapter.getCount() ==0 */) {
                disableControlForLoadingSpinner();
            }

            if (jsonResponse != null && jsonResponse.code.equals("00")) {
                if (jsonResponse.requestCode.equals(SESSION_LIST)) {
                    if (!createClassroomViewModel.validateSessionList(jsonResponse.jsonMessage)) {

                    }

                }
            }
            if (sessionAdapter.getCount() != 0 /*&& accountAdapter.getCount() != 0 */) {
                loading.setVisibility(View.INVISIBLE);
                enableControls(true);
            }
        });
    }

    private void checkSpinnerForEmpty() {
        if (sessionAdapter != null && sessionAdapter.getCount() == 0) {
            disableControlForLoadingSpinner();
        }
        /*else if(accountAdapter!= null && accountAdapter.getCount() == 0){
            disableControlForLoadingSpinner();
        }*/
    }

    private void disableControlForLoadingSpinner() {
        loading.setVisibility(View.VISIBLE);
        enableControls(false);
        setSpinners();
    }

    private void enableControls(boolean value){
        constraintLayout.setEnabled(value);
        spClass.setEnabled(value);
        spWeek.setEnabled(value);
        edtSubject.setEnabled(value);
        edtTopic.setEnabled(value);
        edtDurHrs.setEnabled(value);
        edtDurMins.setEnabled(value);
    }

    private void setSpinners() {
        try {
            createClassroomViewModel.listClass.observe(getViewLifecycleOwner(), sessionModels -> {
                if (sessionModels != null && sessionModels.size() == 0) {
                    createClassroomViewModel.getSessionListOnline(SESSION_LIST);
                }

                if (spClassText != null) {
                    for (int a = 0; a < sessionModels.size(); a++) {
                        ClassModel session = sessionModels.get(a);
                        if (session.getValue().equals(spClassText)) {
                            spClass.setText(session.getText());
                        }
                    }
                }

                classModelsList = sessionModels;
                sessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sessionModels);
                spClass.setAdapter(sessionAdapter);
                sessionAdapter.notifyDataSetChanged();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateClass:
                Bundle bundle = new Bundle();
                bundle.putString(CHAT_USER_KEY, CHAT_USER);
                bundle.putString(CHAT_WITH_KEY, CHAT_WITH);

                //TODO check if class is running
                Navigation.findNavController(v).navigate(R.id.action_create_classroom_to_classroom_fragment, bundle);
                break;
        }
    }
}
