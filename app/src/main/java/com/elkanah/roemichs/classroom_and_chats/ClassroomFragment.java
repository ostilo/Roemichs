package com.elkanah.roemichs.classroom_and_chats;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.elkanah.roemichs.classroom_and_chats.Constants.FIREBASE_URL;

public class ClassroomFragment extends Fragment {

    private ClassroomViewModel mViewModel;
    LinearLayout layout, layout2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView, scrollView2;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    Firebase reference1, reference2;
    Context context;

    public static ClassroomFragment newInstance(String user, String chatwith) {
        Bundle args = new Bundle();
        args.putString("user", user);
        args.putString("chatwith", chatwith);
        ClassroomFragment fragment = new ClassroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classroom_fragment, container, false);

        if(getArguments() !=null){
            UserDetails.username=getArguments().getString("user");
            UserDetails.chatWith=getArguments().getString("chatwith");
        }

        context=getContext();
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference =  mDatabase.getReferenceFromUrl(FIREBASE_URL).child("group_messages_");

        layout = view.findViewById(R.id.linLayoutTeacher);
        layout2=view.findViewById(R.id.linLayoutStudent);
        sendButton = view.findViewById(R.id.sendButton);
        messageArea = view.findViewById(R.id.messageArea);
        scrollView = view.findViewById(R.id.scrollViewTeacher);
        scrollView2 = view.findViewById(R.id.scrollViewStudents);

        Firebase.setAndroidContext(context);
        reference1 = new Firebase(mDatabaseReference + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase(mDatabaseReference + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);

                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username) && userName.equals("teacher")){
                    addMessageBox( message, 1);
                }
                else if(userName.equals("teacher") && !userName.equals(UserDetails.username)){
                    addMessageBox( message, 1);
                }
                else{
                    //student name here
                    addMessageBox( message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(context);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1 ) {
            textView.setText(message);
            textView.setTextColor(Color.WHITE);
            lp2.gravity = Gravity.LEFT;
            textView.setLayoutParams(lp2);
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
        else{
            textView.setText(UserDetails.username + ":- " +message);
            textView.setTextColor(Color.BLACK);
            lp2.gravity = Gravity.LEFT;
            textView.setLayoutParams(lp2);
            layout2.addView(textView);
            scrollView2.fullScroll(View.FOCUS_DOWN);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClassroomViewModel.class);
        // TODO: Use the ViewModel
    }

}
