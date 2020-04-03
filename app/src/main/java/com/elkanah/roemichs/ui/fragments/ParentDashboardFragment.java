package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentDashboardFragment extends Fragment {
//todo
    public ParentDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_dashboard, container, false);
        setUpNavigation(v);
        return v;
    }

    private void setUpNavigation(View rootView){
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.DNavigation);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.parent_dasnboard__nav_host);
        if (navHostFragment != null)
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

}
