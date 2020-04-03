package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText edtStudentParentID;
    private TextView tvDecision;
    boolean flag;
    int counterer = 0;
    public LoginFragment() {
        //todo new
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button btnLogin = v.findViewById(R.id.button);
        edtStudentParentID = v.findViewById(R.id.edtLoginID);
        TextInputEditText edtStudentParentPass = v.findViewById(R.id.edtLoginPass);
        tvDecision = v.findViewById(R.id.textView31);
        tvDecision.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        tvDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterer == 0){
                    edtStudentParentID.setHint("Enter Student ID");
                    tvDecision.setText("Sign In? Parent");
                    counterer += 1;
                }
                else {
                    if(counterer ==1){
                        edtStudentParentID.setHint("Enter Parent ID");
                        tvDecision.setText("Sign In? Student");
                            counterer -= 1;
                    }
                }

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.button:
                 String userInput = edtStudentParentID.getText().toString().trim();
                 if(userInput.equals("p")){
                     NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentDashboardFragment);
                 }else if(userInput.equals("s")){
                     NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentFragment);
             }
                 else {
                     Snackbar.make(v,"PRIVILEGE NOT ASSIGNED YET, CONTACT ADMIN",Snackbar.LENGTH_SHORT).show();
                 }

                 break;

             case R.id.textView31:
                 flag = true;
                 int count = 1;
                    edtStudentParentID.setHint("Enter Parent's Number");
                    tvDecision.setText("Sign in Student");
                    count = count+1;
                    flag = false;
                 break;
         }


    }
}
