package com.elkanah.roemichs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;

import com.elkanah.roemichs.classroom_and_chats.ChatListFragment;
import com.elkanah.roemichs.classroom_and_chats.ClassroomFragment;
import com.elkanah.roemichs.ui.OptionSelect;
import com.elkanah.roemichs.ui.fragments.TestPage;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements OptionSelect {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        //getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ChatListFragment()).commit();

    }

    @Override
    public void onSelect(OptiontModel selectedAnswer) {
        TestPage.answers.add(selectedAnswer);
    }
}
