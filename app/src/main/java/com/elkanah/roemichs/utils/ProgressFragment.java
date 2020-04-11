package com.elkanah.roemichs.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ProgressFragment extends DialogFragment {

    private ProgressDialog progressDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        //progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Please wait while we connect you");
        progressDialog.setCancelable(false);
        getCountDown();
        return progressDialog;
    }

    private CountDownTimer getCountDown(){
        return new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(),"Your subject has been retrieved",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        }.start();
    }
}
