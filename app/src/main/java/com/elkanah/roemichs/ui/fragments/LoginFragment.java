package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.RoemichsSharedPreference;
import com.elkanah.roemichs.ui.model.ScreenUtil;
import com.elkanah.roemichs.ui.viewmodels.LoginViewModel;
import com.elkanah.roemichs.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
//todo
    private TextInputEditText edtStudentParentID;
    private TextView tvDecision;
    ConstraintLayout constraintLayout;
    private Button btnLogin;
    LoginViewModel loginViewModel;
    private ProgressBar progressBar;
    private TextInputEditText edtStudentParentPass;
    public static final String LOGINREQUESTCODE ="login_request";
    public static  int dashboardD = 0;

    public LoginFragment() {
        //todo new
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        btnLogin = v.findViewById(R.id.button);
        edtStudentParentID = v.findViewById(R.id.edtLoginID);
        edtStudentParentPass = v.findViewById(R.id.edtLoginPass);
        tvDecision = v.findViewById(R.id.textView31);
        constraintLayout = v.findViewById(R.id.login_check);
        progressBar = v.findViewById(R.id.progressBar3);


        tvDecision.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        setupUI(v.findViewById(R.id.login_check));

        observeResponse(v);


        tvDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return v;
    }

    private void observeResponse(View v) {
        loginViewModel.response.observe(getViewLifecycleOwner(),jsonResponse -> {
            if (jsonResponse != null && jsonResponse.code.equals("00")) {
                    if(!loginViewModel.validateResponse(jsonResponse.jsonMessage)) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnLogin.setEnabled(true);
                        RoemichsSharedPreference.getInstance(getContext()).savePreference(getString(R.string.username_keystore), Objects.requireNonNull(edtStudentParentID.getText()).toString());
                        Snackbar.make(v, jsonResponse.msg, Snackbar.LENGTH_LONG).show();
                    }
            }else if(jsonResponse != null && jsonResponse.code.equals("-02")){
                RoemichsSharedPreference.getInstance(getContext()).savePreference(getString(R.string.username_keystore), Objects.requireNonNull(edtStudentParentID.getText()).toString());
                progressBar.setVisibility(View.INVISIBLE);
                btnLogin.setEnabled(true);
                Snackbar.make(v, jsonResponse.msg, Snackbar.LENGTH_LONG).show();

            }

        });
    }

    public void setupUI(View view){
        if(!(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        if(view instanceof  ViewGroup){
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerview = ((ViewGroup)view).getChildAt(i);
                setupUI(innerview);
            }
        }
    }

    private void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.button:
                 if(edtStudentParentPass.getText() != null && edtStudentParentID != null){
                     try {
                         //to api
                        // btnLogin.setEnabled(false);
                         progressBar.setVisibility(View.VISIBLE);
                         btnLogin.setBackgroundColor(getResources().getColor(R.color.secondColor));
                         loginViewModel.authenticate(edtStudentParentID,edtStudentParentPass,LOGINREQUESTCODE);
                         String userInput = edtStudentParentID.getText().toString().trim();
                         if(!TextUtils.isEmpty(userInput)&& userInput.equals("p")){
                             progressBar.setVisibility(View.VISIBLE);
                             NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentDashboardFragment);
                         }else if(!TextUtils.isEmpty(userInput)&&userInput.equals("s")){
                             progressBar.setVisibility(View.VISIBLE);
                             NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentFragment);
                         }
                         else if(!TextUtils.isEmpty(userInput)&&userInput.equals("a")){
                             progressBar.setVisibility(View.VISIBLE);
                             NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_adminDashboardFragment);
                         }
                         else if(!TextUtils.isEmpty(userInput)&&userInput.equals("t")){
                             dashboardD = 1;
                             progressBar.setVisibility(View.VISIBLE);
                             NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentFragment);
                         }
                         else {
                             Snackbar.make(v,"PRIVILEGE NOT ASSIGNED YET, CONTACT ADMIN",Snackbar.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.INVISIBLE);
                         }
                     }catch (Exception e){
                         e.printStackTrace();
                         Log.e("error",e.toString());
                     }
                 }

                 break;

             case R.id.textView31:

                 break;
         }


    }
}
