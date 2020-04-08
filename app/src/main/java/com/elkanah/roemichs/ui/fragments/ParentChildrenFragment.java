package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.ParentChildrenAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentChildrenFragment extends Fragment {

    private View view;

    public ParentChildrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_parent_children, container, false);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_pc);
        collapsingToolbarLayout.setTitle("Roemichs International");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));



        Toolbar toolbar = view.findViewById(R.id.withdraw_toolbar_pc);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Roemichs");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
//
//        NavController navController = Navigation.findNavController(getActivity(),R.id.parent_dasnboard__nav_host);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupWithNavController(collapsingToolbarLayout,toolbar,navController,appBarConfiguration);

        RecyclerView recyclerView = view.findViewById(R.id.parent_children_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ParentChildrenAdapter adapter = new ParentChildrenAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
