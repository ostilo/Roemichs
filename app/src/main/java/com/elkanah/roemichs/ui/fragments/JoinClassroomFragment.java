package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.elkanah.roemichs.db.models.SubjectDaoModel;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.viewmodels.JoinClassroomViewModel;

import java.util.List;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;

public class JoinClassroomFragment extends Fragment implements View.OnClickListener {

    private static final String SESSION_LIST = "session_list" ;
    private JoinClassroomViewModel joinClassroomViewModel;
    private Button btnJoinClass;
    private String CHAT_USER, CHAT_WITH;
    private Context context;
    private AutoCompleteTextView spWeek, spSubject;
    private ConstraintLayout constraintLayout;
    private ArrayAdapter<SubjectDaoModel> subjectAdapter;
    public static List<SubjectDaoModel> subjectDaoModel;
    private ProgressBar loading;
    private String spSubjectText;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_classroom_fragment, container, false);
        context=getContext();
        joinClassroomViewModel = ViewModelProviders.of(this).get(JoinClassroomViewModel.class);

        if(getArguments()!=null){
            CHAT_USER=getArguments().getString(CHAT_USER_KEY);
            CHAT_WITH=getArguments().getString(Constants.CHAT_WITH_KEY);
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
        toolbar=view.findViewById(R.id.toolbarJoinClass);
        btnJoinClass=view.findViewById(R.id.btnJoinClass);
        spWeek = view.findViewById(R.id.spWeekJoinCls);
        spSubject = view.findViewById(R.id.spSubjectjoinCls);
        constraintLayout = view.findViewById(R.id.constrJoinCls);
        loading = view.findViewById(R.id.loading_joinCls);
    }

    private void setListeners() {
        btnJoinClass.setOnClickListener(this);
    }

    private void setToolBar() {
        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Join Classroom");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
    }

    private void observeViewModel() {
        joinClassroomViewModel.jsonResponse.observe(getViewLifecycleOwner(), jsonResponse -> {
            if (subjectAdapter.getCount() == 0 /*|| accountAdapter.getCount() ==0 */) {
                disableControlForLoadingSpinner();
            }

            if (jsonResponse != null && jsonResponse.code.equals("00")) {
                if (jsonResponse.requestCode.equals(SESSION_LIST)) {
                    if (!joinClassroomViewModel.validateClassList(jsonResponse.jsonMessage)) {

                    }

                }
            }
            if (subjectAdapter.getCount() != 0 /*&& accountAdapter.getCount() != 0 */) {
                loading.setVisibility(View.INVISIBLE);
                enableControls(true);
            }
        });
    }

    private void checkSpinnerForEmpty() {
        if (subjectAdapter != null && subjectAdapter.getCount() == 0) {
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
        spSubject.setEnabled(value);
        spWeek.setEnabled(value);
    }

    private void setSpinners() {
        try {
            joinClassroomViewModel.listSession.observe(getViewLifecycleOwner(), subjectDaoModels -> {
                if (subjectDaoModels != null && subjectDaoModels.size() == 0) {
                    joinClassroomViewModel.getSubjectListOnline(SESSION_LIST);
                }

                if (spSubjectText != null) {
                    for (int a = 0; a < subjectDaoModels.size(); a++) {
                        SubjectDaoModel session = subjectDaoModels.get(a);
                        if (session.getValue().equals(spSubjectText)) {
                            spSubject.setText(session.getText());
                        }
                    }
                }

                subjectDaoModel = subjectDaoModels;
                subjectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectDaoModels);
                spSubject.setAdapter(subjectAdapter);
                subjectAdapter.notifyDataSetChanged();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnJoinClass:
                Bundle bundle=new Bundle();
                bundle.putString(CHAT_USER_KEY, CHAT_USER);
                bundle.putString(CHAT_WITH_KEY, CHAT_WITH);
                Navigation.findNavController(v).navigate(R.id.action_join_classroom_to_classroom_fragment, bundle);
                break;
        }
    }
}
