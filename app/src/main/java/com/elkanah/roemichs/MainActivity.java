package com.elkanah.roemichs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;


import com.elkanah.roemichs.db.view.Callbacks;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        //getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new ChatListFragment()).commit();

    }




    public static boolean realData(boolean value){
        return value;
    }

    @Override
    public void deleteImageAtPosition(Boolean value) {
        realData(value);

    }
}
