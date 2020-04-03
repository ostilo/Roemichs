package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.ScreenUtil;
import com.elkanah.roemichs.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
//todo
    private TextInputEditText edtStudentParentID;
    private TextView tvDecision;
    boolean flag;
    int counterer = 0;
    ConstraintLayout constraintLayout;
    private Button btnLogin;
    private boolean mTablet;

    public LoginFragment() {
        //todo new
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = v.findViewById(R.id.button);
        edtStudentParentID = v.findViewById(R.id.edtLoginID);
        TextInputEditText edtStudentParentPass = v.findViewById(R.id.edtLoginPass);
        tvDecision = v.findViewById(R.id.textView31);
        tvDecision.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        constraintLayout = v.findViewById(R.id.login_check);
        setupUI(v.findViewById(R.id.login_check));


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

    public void getScreen(View v){
        ScreenUtil util = new ScreenUtil(getActivity());
                String result = String.format("Screen Width : %s and Height : %s. Display two Fragment :: %s", util.getDpWidth(), util.getDpHeight(), mTablet);
            Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.button:
                 try {
                     String userInput = edtStudentParentID.getText().toString().trim();
                     if(!TextUtils.isEmpty(userInput)&&userInput.equals("p")){
                         NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentDashboardFragment);
                     }else if(!TextUtils.isEmpty(userInput)&&userInput.equals("s")){
                         NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_parentFragment);
                     }
                     else {
                         //Snackbar.make(v,"PRIVILEGE NOT ASSIGNED YET, CONTACT ADMIN",Snackbar.LENGTH_SHORT).show();
                     }
                 }catch (Exception e){
                     e.printStackTrace();
                     Log.e("error",e.toString());
                 }
                 break;

//             case R.id.textView31:
//                 flag = true;
//                 int count = 1;
//                    edtStudentParentID.setHint("Enter Parent's Number");
//                    tvDecision.setText("Sign in Student");
//                    count = count+1;
//                    flag = false;
//                 break;
         }


    }
}
