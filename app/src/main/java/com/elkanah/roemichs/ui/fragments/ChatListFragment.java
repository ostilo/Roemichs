package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.elkanah.roemichs.R;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;

public class ChatListFragment extends Fragment{

    Context context;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.chat_list_fragment, container, false);

        context=getContext();

        final EditText editText=view.findViewById(R.id.editText);
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(TextUtils.isEmpty(editText.getText().toString().trim())){
                    Toast.makeText(context, "Enter student or teacher", Toast.LENGTH_SHORT).show();
                }else {
                    if(editText.getText().toString().trim().toLowerCase().equals("teacher")){
                        bundle.putString(CHAT_USER_KEY, "teacher");
                        bundle.putString(CHAT_WITH_KEY, "student");
                        Navigation.findNavController(v).navigate(R.id.action_chatListFragment_to_create_classroom, bundle);
                    }else if(editText.getText().toString().trim().toLowerCase().equals("student")){
                        bundle.putString(CHAT_USER_KEY, "student");
                        bundle.putString(CHAT_WITH_KEY, "teacher");
                        Navigation.findNavController(v).navigate(R.id.action_chatListFragment_to_join_classroom, bundle);
                    }
                    else {
                        Toast.makeText(context, "Enter student or teacher", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }
}
