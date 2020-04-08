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
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.repository.ChatDetails;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.viewmodels.ClassroomViewModel;
import com.elkanah.roemichs.utils.CommonUtils;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.elkanah.roemichs.db.repository.Constants.CHAT_USER_KEY;
import static com.elkanah.roemichs.db.repository.Constants.CHAT_WITH_KEY;
import static com.elkanah.roemichs.db.repository.Constants.IS_FIRST_TEACHER_MESSAGE;
import static com.elkanah.roemichs.db.repository.Constants.JOIN_BEFORE;
import static com.elkanah.roemichs.utils.CommonUtils.*;
import static com.elkanah.roemichs.utils.CommonUtils.getCurrentDate;
import static com.elkanah.roemichs.utils.CommonUtils.getCurrentTime;
import static com.elkanah.roemichs.db.repository.Constants.FIREBASE_URL;
import static com.elkanah.roemichs.utils.CommonUtils.isDoubleClick;
import static com.elkanah.roemichs.utils.CommonUtils.isNetworkConnected;

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
    private Toolbar toolbar;
    private TextView txtCountStudent, txtTimerHrs, txtTimerMin, txtTimerSecs;
    private FrameLayout frameStudentCount;
    private LinearLayout linLayTimer;
    private int intStudentCount = 0;
    private ProgressBar loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classroom_fragment, container, false);
        context = getContext();
        mViewModel = ViewModelProviders.of(this).get(ClassroomViewModel.class);
        JOIN_BEFORE = false;
        IS_FIRST_TEACHER_MESSAGE=true;

        if (getArguments() != null) {
            ChatDetails.username = getArguments().getString(CHAT_USER_KEY);
            ChatDetails.chatWith = getArguments().getString(CHAT_WITH_KEY);
        }

        setViewById(view);
        setToolBar();
        setFirebaseUp();
        setListeners();
        setBackPress(view);
        observeChatting();

        return view;
    }

    private void setViewById(View view) {
        toolbar = view.findViewById(R.id.toolbarClassroom);
        layout = view.findViewById(R.id.linLayoutTeacher);
        layout2 = view.findViewById(R.id.linLayoutStudent);
        sendButton = view.findViewById(R.id.sendButton);
        messageArea = view.findViewById(R.id.messageArea);
        scrollView = view.findViewById(R.id.scrollViewTeacher);
        scrollView2 = view.findViewById(R.id.scrollViewStudents);
        loading = view.findViewById(R.id.loading_ClsRoom);
    }

    private void setListeners() {
        sendButton.setOnClickListener(this);
    }

    private void setBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(context, "Press top back button", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void setToolBar() {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Subject or Topic");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    sendFirstMessage("left");
                    Navigation.findNavController(v).navigateUp();
                } else {
                    Toast.makeText(context, "Double Click Me", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setFirebaseUp() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReferenceFromUrl(FIREBASE_URL).child("group_messages_");
        Firebase.setAndroidContext(context);
        reference1 = new Firebase(mDatabaseReference + ChatDetails.username + "_" + ChatDetails.chatWith);
        reference2 = new Firebase(mDatabaseReference + ChatDetails.chatWith + "_" + ChatDetails.username);
        loading.setVisibility(View.VISIBLE);
    }

    long checkChatList = 0;
    boolean joined = false;

    private void observeChatting() {
        if (isNetworkConnected(context)) {
            reference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try {
                        checkChatList += 1;
                        Map map = dataSnapshot.getValue(Map.class);
                        String message = map.get("message").toString();
                        userName = map.get("user").toString();
                        String date = map.get("date").toString();
                        String time = map.get("time").toString();

                        if (userName.equals(ChatDetails.username) && userName.equals("teacher") && !message.equals(" class started at")) {
                            addMessageBox(message, date, time, 1);
                        } else if (userName.equals("teacher") && !userName.equals(ChatDetails.username)) {
                            addMessageBox(message, date, time, 1);
                        } else if (userName.equals("student") && message.equals(" join the class")) {
                            addMessageBox(message, getCurrentDate(), getCurrentTime(), 3);
                        } else if (userName.equals("student") && message.equals(" left the class")) {
                            addMessageBox(message, getCurrentDate(), getCurrentTime(), 4);
                        } else if (userName.equals("teacher") && message.equals(" class started at")) {
                            addMessageBox(message, getCurrentDate(), getCurrentTime(), 5);
                        } else if (userName.equals("student") && !message.equals(" join the class") && !message.equals(" left the class")) {
                            addMessageBox(message, date, time, 2);
                        }

                        if (checkChatList == dataSnapshot.getChildrenCount() - 1 && !joined) {
                            if (ChatDetails.username.equals("student")) {
                                sendFirstMessage("join");
                            }
                            joined = true;
                            loading.setVisibility(View.INVISIBLE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
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
        } else {
            Toast.makeText(context, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendFirstMessage(String joinLeft) {
        Map<String, String> map = new HashMap<String, String>();
        if (!ChatDetails.username.equals("teacher")) {
            if (joinLeft.equals("join")) {
                map.put("message", " join the class");
                intStudentCount += 1;
                txtCountStudent.setText(String.valueOf(intStudentCount));
            } else if (joinLeft.equals("left")) {
                map.put("message", " left the class");
                intStudentCount -= 1;
                txtCountStudent.setText(String.valueOf(intStudentCount));
            }
        }else if(ChatDetails.username.equals("teacher") && joinLeft.equals("create")){
                map.put("message", " class started at");
            }
            map.put("user", ChatDetails.username);
            map.put("date", getCurrentDate());
            map.put("time", getCurrentTime());
            reference1.push().setValue(map);
            reference2.push().setValue(map);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        switch (itm.getItemId()) {
            case R.id.action_menu_more_class:
                PopupMenu popupMenu = new PopupMenu(getContext(), toolbar);
                popupMenu.setGravity(Gravity.END);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.classroom_more_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_video_class) {
                        Toast.makeText(context, "To Video Class", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (item.getItemId() == R.id.action_audio_class) {
                        Toast.makeText(context, "To Audio Class", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (item.getItemId() == R.id.action_stop_class) {
                        Toast.makeText(context, "Stop the class", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                break;
        }
        return super.onOptionsItemSelected(itm);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.classroom_menu, menu);
        final MenuItem alertMenuItem = menu.findItem(R.id.action_student_counter);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        frameStudentCount = rootView.findViewById(R.id.frame_studentCount);
        frameStudentCount.setOnClickListener(this);
        txtCountStudent = rootView.findViewById(R.id.txtStudentCount);

        final MenuItem timerMenu = menu.findItem(R.id.action_time_counter);
        LinearLayout timerRoot = (LinearLayout) timerMenu.getActionView();
        linLayTimer = timerRoot.findViewById(R.id.linLayotCountDown);
        txtTimerHrs = timerRoot.findViewById(R.id.txtClassTimeHrs);
        txtTimerMin = timerRoot.findViewById(R.id.txtClassTimeMins);
        txtTimerSecs = timerRoot.findViewById(R.id.txtClassTimeSecs);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void addMessageBox(String message, String date, String time, int type) {
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
        lp2.topMargin = 10;

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.note_line);
            textView.setText(message);
            lp2.gravity = Gravity.START;
            textView.setLayoutParams(lp2);
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
            if(IS_FIRST_TEACHER_MESSAGE) {
                sendFirstMessage("create");
                IS_FIRST_TEACHER_MESSAGE=false;
            }
        } else if (type == 2) {
            textView.setBackgroundResource(R.drawable.chat_msg_bg);
            if (date.equals(ChatDetails.date)) {
                txtDateTime.setText("Today " + time);
            } else if (date.equals(getYesterdayDate())) {
                txtDateTime.setText("Yesterday " + time);
            } else {
                txtDateTime.setText(date + " " + time);
            }
            lp2.gravity = Gravity.END;
            txtStudentName.setText(userName);
            txtStudentName.setTextSize(14);
            txtStudentName.setLayoutParams(lp2);
            lp2.topMargin = 0;
            textView.setText(message);
            textView.setLayoutParams(lp2);
            txtDateTime.setTextSize(12);
            txtDateTime.setLayoutParams(lp2);
            layout2.addView(txtStudentName);
            layout2.addView(textView);
            layout2.addView(txtDateTime);

            scrollView2.fullScroll(View.FOCUS_DOWN);
        } else if (type == 3) {
            if (date.equals(ChatDetails.date)) {
                txtDateTime.setText(userName + " join class : Today " + time);
            } else if (date.equals(getYesterdayDate())) {
                txtDateTime.setText(userName + " join class : Yesterday " + time);
            } else {
                txtDateTime.setText(userName + " join class : " + date + " " + time);
            }
            lp2.gravity = Gravity.CENTER;
            lp2.topMargin = 0;
            txtDateTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtDateTime.setTextColor(context.getResources().getColor(R.color.black));
            txtDateTime.setTextSize(10);
            txtDateTime.setLayoutParams(lp2);
            layout2.addView(txtDateTime);

            scrollView2.fullScroll(View.FOCUS_DOWN);
        } else if (type == 4) {
            if (date.equals(ChatDetails.date)) {
                txtDateTime.setText(userName + " left the class : Today " + time);
            } else if (date.equals(getYesterdayDate())) {
                txtDateTime.setText(userName + " left the class : Yesterday " + time);
            } else {
                txtDateTime.setText(userName + " left the class : " + date + " " + time);
            }
            lp2.gravity = Gravity.CENTER;
            lp2.topMargin = 0;
            txtDateTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtDateTime.setTextColor(context.getResources().getColor(R.color.error));
            txtDateTime.setTextSize(10);
            txtDateTime.setLayoutParams(lp2);
            layout2.addView(txtDateTime);

            scrollView2.fullScroll(View.FOCUS_DOWN);
        }else if(type==5){
            if (date.equals(ChatDetails.date)) {
                txtDateTime.setText("class started at : Today " + time);
            } else if (date.equals(getYesterdayDate())) {
                txtDateTime.setText("class started at : Yesterday " + time);
            } else {
                txtDateTime.setText("class started at : " + date + " " + time);
            }
            lp2.gravity = Gravity.CENTER;
            lp2.topMargin = 0;
            txtDateTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtDateTime.setTextColor(context.getResources().getColor(R.color.yellow));
            txtDateTime.setTextSize(10);
            txtDateTime.setLayoutParams(lp2);
            layout.addView(txtDateTime);
            scrollView.fullScroll(View.FOCUS_DOWN);
            startTimer(01, 02);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                String messageText = messageArea.getText().toString();
                if (!stringIsEmpty(messageText)) {
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
            case R.id.frame_studentCount:
                intStudentCount += 1;
                txtCountStudent.setText(String.valueOf(intStudentCount));
                Toast.makeText(context, "Go to List of Student Online", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startTimer(int hrs, int mins) {
        DecimalFormat formatter = new DecimalFormat("00");

        if(hrs > 0){
            new CountDownTimer(hrs * 60 * 60 *1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    txtTimerSecs.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                    txtTimerMin.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                    txtTimerHrs.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                }
                public void onFinish() {
                    if(mins > 0 ){
                        new CountDownTimer(mins * 10000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                txtTimerSecs.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                                txtTimerMin.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                                txtTimerHrs.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                            }
                            @Override
                            public void onFinish() {
                                linLayTimer.setBackgroundColor(context.getResources().getColor(R.color.error));
                                txtTimerSecs.setText("00");
                            }
                        }.start();
                    }else {
                        linLayTimer.setBackgroundColor(context.getResources().getColor(R.color.error));
                        txtTimerSecs.setText("00");
                    }
                }
            }.start();
        }else if(mins>0){
            new CountDownTimer(mins * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtTimerSecs.setText(formatter.format((int) ((millisUntilFinished / 1000) % 60)));
                    txtTimerMin.setText(formatter.format((int) (((millisUntilFinished / 1000) / 60) % 60)));
                    txtTimerHrs.setText(formatter.format((int) ((millisUntilFinished / 1000) / 3600)));
                }
                @Override
                public void onFinish() {
                    linLayTimer.setBackgroundColor(context.getResources().getColor(R.color.error));
                    txtTimerSecs.setText("00");
                }
            }.start();
        }
    }
}
