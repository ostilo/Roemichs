package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.ui.model.TestQuestionModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationDialog extends DialogFragment implements View.OnClickListener {

    private TextView text;
    private Button btn_yes;
    private Button btn_no;
    private ArrayList<TestQuestionModel> questions;
    private ArrayList<OptiontModel> answers;

    public ConfirmationDialog() {
        // Required empty public constructor
    }

    public static ConfirmationDialog newInstance(String text, ArrayList<TestQuestionModel> list, ArrayList<OptiontModel> answers) {
        Bundle args = new Bundle();
        args.putString("message", text);
        args.putParcelableArrayList("test_question", list);
        args.putParcelableArrayList("answers", answers);
        ConfirmationDialog fragment = new ConfirmationDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation_dialog, container, false);
        text = view.findViewById(R.id.tv_notice_text);
        btn_no = view.findViewById(R.id.btn_no);
        btn_yes = view.findViewById(R.id.btn_yes);
        btn_no.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        ImageView noticeIcon = view.findViewById(R.id.notice_icon);
        if (getArguments()!=null){
            text.setText(getArguments().getString("message"));

            noticeIcon.setImageResource(R.drawable.conversation);
            questions = getArguments().getParcelableArrayList("test_question");
            answers = getArguments().getParcelableArrayList("answers");
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_no){
            dismiss();
        }else if (v.getId() == R.id.btn_yes){
//            TestPage.newInstance().countDownTimer.cancel();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("test_question", questions);
            bundle.putParcelableArrayList("answers", answers);
//            Navigation.findNavController(getView()).navigate(R.id.confirmationDialog, bundle);
            NavHostFragment.findNavController(this).navigate(R.id.testResult, bundle);
        }
    }
}
