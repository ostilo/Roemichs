package com.elkanah.roemichs.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;

import com.elkanah.roemichs.R;

import com.elkanah.roemichs.classroom_and_chats.ChatListFragment;
import com.elkanah.roemichs.classroom_and_chats.ClassroomFragment;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);

        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ChatListFragment()).commit();

    }
}
