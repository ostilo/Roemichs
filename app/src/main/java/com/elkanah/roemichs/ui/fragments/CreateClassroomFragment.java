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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.SessionModel;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.viewmodels.CreateClassroomViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;

public class CreateClassroomFragment extends Fragment implements View.OnClickListener {

    private static final String SESSION_LIST = "SessionList";
    private CreateClassroomViewModel createClassroomViewModel;
    private Button btnCreateCls;
    private String CHAT_USER, CHAT_WITH;
    private Context context;
    private AutoCompleteTextView spSession, spClass, spClassType, spTerm, spWeek;
    private ConstraintLayout constraintLayout;
    private EditText edtSubject, edtTopic, edtDuration;
    private ArrayAdapter<SessionModel> sessionAdapter;
    public static List<SessionModel> sessionModelsList;
    public static String spSessionText;
    private ProgressBar loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_classroom_fragment, container, false);
        context = getContext();
        if (getArguments() != null) {
            CHAT_USER = getArguments().getString(CHAT_USER_KEY);
            CHAT_WITH = getArguments().getString(Constants.CHAT_WITH_KEY);
        }

        setViewById(view);
        setSpinners();
        checkSpinnerForEmpty();
        setListeners();
        observeViewModel();
        return view;
    }

    private void setViewById(View view) {
        btnCreateCls = view.findViewById(R.id.btnCreateClass);
        spSession = view.findViewById(R.id.tv_spSession_crtClass);
        spClass = view.findViewById(R.id.tv_spClass_crtClass);
        spClassType = view.findViewById(R.id.tv_spClasstype_crtCls);
        spTerm = view.findViewById(R.id.spTerm_CrtClass);
        spWeek = view.findViewById(R.id.spWeek_crtCls);
        constraintLayout = view.findViewById(R.id.constrCrtCls);
        edtSubject = view.findViewById(R.id.txtSubjectCrtCls);
        edtTopic = view.findViewById(R.id.txtTopic_crtCls);
        edtDuration = view.findViewById(R.id.txtDuration_crtCls);
        loading = view.findViewById(R.id.loading_crtCls);
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
                constraintLayout.setEnabled(true);
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
        constraintLayout.setEnabled(false);
        setSpinners();
    }

    private void setSpinners() {
        try {
            createClassroomViewModel.listSession.observe(getViewLifecycleOwner(), sessionModels -> {
                if (sessionModels != null && sessionModels.size() == 0) {
                    createClassroomViewModel.getSessionListOnline(SESSION_LIST);
                }

                if (spSessionText != null) {
                    for (int a = 0; a < sessionModels.size(); a++) {
                        SessionModel session = sessionModels.get(a);
                        if (session.getValue().equals(spSessionText)) {
                            spSession.setText(session.getText());
                        }
                    }
                }

                sessionModelsList = sessionModels;
                sessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sessionModels);
                spSession.setAdapter(sessionAdapter);
                sessionAdapter.notifyDataSetChanged();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createClassroomViewModel = ViewModelProviders.of(this).get(CreateClassroomViewModel.class);
        // TODO: Use the ViewModel
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
