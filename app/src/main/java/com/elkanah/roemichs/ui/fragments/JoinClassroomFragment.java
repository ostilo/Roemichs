package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.viewmodels.JoinClassroomViewModel;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;

public class JoinClassroomFragment extends Fragment implements View.OnClickListener {

    private JoinClassroomViewModel mViewModel;
    private Button btnJoinClass;
    private String CHAT_USER, CHAT_WITH;

    public static JoinClassroomFragment newInstance() {
        return new JoinClassroomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_classroom_fragment, container, false);


        if(getArguments()!=null){
            CHAT_USER=getArguments().getString(CHAT_USER_KEY);
            CHAT_WITH=getArguments().getString(Constants.CHAT_WITH_KEY);
        }

        btnJoinClass=view.findViewById(R.id.btnJoinClass);
        btnJoinClass.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JoinClassroomViewModel.class);
        // TODO: Use the ViewModel
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
