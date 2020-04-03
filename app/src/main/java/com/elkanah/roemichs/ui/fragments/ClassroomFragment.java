package com.elkanah.roemichs.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.repository.ChatDetails;
import com.elkanah.roemichs.ui.viewmodels.ClassroomViewModel;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;
import static com.elkanah.roemichs.utils.CommonUtils.getCurrentDate;
import static com.elkanah.roemichs.utils.CommonUtils.getCurrentTime;
import static com.elkanah.roemichs.db.repository.Constants.FIREBASE_URL;

public class ClassroomFragment extends Fragment implements View.OnClickListener {

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
    private String messageText;
    private Toolbar toolbar;
    private TextView txtCountStudent;
    private int intStudentCount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classroom_fragment, container, false);
        context=getContext();
        mViewModel = ViewModelProviders.of(this).get(ClassroomViewModel.class);


        if(getArguments() !=null){
            ChatDetails.username=getArguments().getString(CHAT_USER_KEY);
            ChatDetails.chatWith=getArguments().getString(CHAT_WITH_KEY);
        }

        setViewById(view);
        setToolBar();
        setFirebaseUp();
        setListeners();
        //TODO if there is network, else loading
        observeChatting();

        return view;
    }

    private void setViewById(View view) {
        toolbar = view.findViewById(R.id.toolbarClassroom);
        layout = view.findViewById(R.id.linLayoutTeacher);
        layout2=view.findViewById(R.id.linLayoutStudent);
        sendButton = view.findViewById(R.id.sendButton);
        messageArea = view.findViewById(R.id.messageArea);
        scrollView = view.findViewById(R.id.scrollViewTeacher);
        scrollView2 = view.findViewById(R.id.scrollViewStudents);
    }

    private void setListeners() {
        sendButton.setOnClickListener(this);
    }

    private void setToolBar() {
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
    }

    private void setFirebaseUp() {
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference =  mDatabase.getReferenceFromUrl(FIREBASE_URL).child("group_messages_");
        Firebase.setAndroidContext(context);
        reference1 = new Firebase(mDatabaseReference + ChatDetails.username + "_" + ChatDetails.chatWith);
        reference2 = new Firebase(mDatabaseReference + ChatDetails.chatWith + "_" + ChatDetails.username);
    }

    private void observeChatting() {
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                userName = map.get("user").toString();
                String date = map.get("date").toString();
                String time = map.get("time").toString();

                if(userName.equals(ChatDetails.username) && userName.equals("teacher")){
                    addMessageBox( message, date, time, 1);
                }
                else if(userName.equals("teacher") && !userName.equals(ChatDetails.username)){
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        switch (itm.getItemId()){
            case R.id.action_menu_more_class:
             /*   PopupMenu popupMenu = new PopupMenu(getContext(), toolbar);
                popupMenu.setGravity(Gravity.END);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.classroom_more_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_video_class) {
                        Toast.makeText(context, "To Video Class", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if (item.getItemId() == R.id.action_audio_class) {
                        Toast.makeText(context, "To Audio Class", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if (item.getItemId() == R.id.action_stop_class) {
                        Toast.makeText(context, "Stop the class", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;*/
                intStudentCount +=1;
                txtCountStudent.setText(String.valueOf(intStudentCount));
                Toast.makeText(context, "Go to List of Student Online", Toast.LENGTH_SHORT).show();
     //   });
                break;
        }
        return super.onOptionsItemSelected(itm);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.classroom_menu, menu);
        final MenuItem alertMenuItem = menu.findItem(R.id.action_student_counter);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        txtCountStudent = rootView.findViewById(R.id.txtStudentCount);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void addMessageBox(String message, String date, String time, int type){
        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);

        TextView txtDateTime = new TextView(context);
        txtDateTime.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        txtDateTime.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        TextView txtStudentName = new TextView(context);
        txtStudentName.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        txtStudentName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;
        lp2.topMargin= 10;

        if(type == 1 ) {
            textView.setBackgroundResource(R.drawable.note_line);
            textView.setText(message);
            lp2.gravity = Gravity.START;
            textView.setLayoutParams(lp2);
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
        else{
            textView.setBackgroundResource(R.drawable.chat_msg_bg);
            if(date.equals(ChatDetails.date)) {
                txtDateTime.setText( "Today " + time );
            }else {
                txtDateTime.setText(date + " " + time);
            }
            lp2.gravity = Gravity.END;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendButton:
                messageText = messageArea.getText().toString();
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", ChatDetails.username);
                    map.put("date", getCurrentDate());
                    map.put("time", getCurrentTime());
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
                break;
        }
    }
}
