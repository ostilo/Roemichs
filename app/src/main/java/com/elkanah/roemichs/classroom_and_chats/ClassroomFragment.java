package com.elkanah.roemichs.classroom_and_chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.elkanah.roemichs.classroom_and_chats.CommonUtils.getCurrentDate;
import static com.elkanah.roemichs.classroom_and_chats.CommonUtils.getCurrentTime;
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
    private String userName;

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

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Subject or Topic");
            setHasOptionsMenu(true);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Dashboard.class);
//                startActivity(intent);
                Toast.makeText(context, "Go back", Toast.LENGTH_SHORT).show();
            }
        });

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
                    map.put("date", getCurrentDate());
                    map.put("time", getCurrentTime());

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
                userName = map.get("user").toString();
                String date = map.get("date").toString();
                String time = map.get("time").toString();

                if(userName.equals(UserDetails.username) && userName.equals("teacher")){
                    addMessageBox( message, date, time, 1);
                }
                else if(userName.equals("teacher") && !userName.equals(UserDetails.username)){
                    addMessageBox( message, date, time,1);
                }
                else{
                    //student name here
                    addMessageBox( message, date, time,2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_video_class) {
            Toast.makeText(context, "To Video Class", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_audio_class) {
            Toast.makeText(context, "To Audio Class", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu , menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void addMessageBox(String message, String date, String time, int type){
        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);

        TextView txtDateTime = new TextView(context);
        txtDateTime.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        txtDateTime.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        TextView txtStudentName = new TextView(context);
        txtStudentName.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        txtStudentName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;
        lp2.topMargin= 10;

        if(type == 1 ) {
            textView.setBackgroundResource(R.drawable.note_line);
            textView.setText(message);
            lp2.gravity = Gravity.LEFT;
            textView.setLayoutParams(lp2);
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
        else{
            textView.setBackgroundResource(R.drawable.chat_msg_bg);
            if(date.equals(UserDetails.date)) {
                txtDateTime.setText( "Today " + time );
            }else {
                txtDateTime.setText(date + " " + time);
            }
            lp2.gravity = Gravity.RIGHT;
            txtStudentName.setText(userName);
            txtStudentName.setTextSize(14);
            txtStudentName.setLayoutParams(lp2);
            lp2.topMargin=0;
            textView.setText(message);
            textView.setLayoutParams(lp2);
            txtDateTime.setTextSize(12);
            txtDateTime.setLayoutParams(lp2);
            layout2.addView(txtStudentName);
            layout2.addView(textView);
            layout2.addView(txtDateTime);

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
