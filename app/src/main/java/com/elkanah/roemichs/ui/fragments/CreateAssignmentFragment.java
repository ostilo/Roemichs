package com.elkanah.roemichs.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.StudentSelectedImageAssgnAdapter;
import com.firebase.client.annotations.NotNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.opensooq.supernova.gligar.GligarPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elkanah.roemichs.utils.CommonUtils.isDoubleClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAssignmentFragment extends Fragment implements View.OnClickListener{

    private static final int PICKER_REQUEST_CODE = 22;
    private static final int READ_PERMISSION = 33 ;
    private static final int WRITE_PERMISSION = 44 ;
    FloatingActionButton fab;
    private List<String> imagePaths;
    private ArrayList<String> returnValue;
    private int imageCount = 5;
    private int pixRequestCode = 100;
    private Toolbar toolbar;
    private ArrayList<String> newMe;
    private int imgCount;
    private StudentSelectedImageAssgnAdapter imageAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private LinearLayout layout;

    public CreateAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_assignment, container, false);
        context = getContext();

        fab=view.findViewById(R.id.addImagebtnCrtAsgmt);
        fab.setOnClickListener(this);

        imagePaths = new ArrayList<>();
        returnValue = new ArrayList<>();
        toolbar=view.findViewById(R.id.toolbarCrtAsgmt);
        recyclerView=view.findViewById(R.id.addImageRecyclerCrtAsgmt);
        layout = view.findViewById(R.id.linLayImgDescriCrtAsgmt);
        setToolBar();

        return view;
    }

    private void setToolBar() {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Assignment");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Navigation.findNavController(v).navigateUp();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addImagebtnCrtAsgmt:
                if(checkTrue())
                new GligarPicker().disableCamera(false).cameraDirect(false).requestCode(PICKER_REQUEST_CODE).withFragment(this).show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(getActivity(), Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(getContext(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICKER_REQUEST_CODE && data != null) {
            String pathList[] = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); //return list of selected images
            if (pathList != null) {
                newMe = new ArrayList<String>();
                for (String s : pathList) {
                    newMe.add(s);
                }
                setAdapter(newMe);
                createEditText(newMe.size());
            }
            imgCount = pathList.length;
        }
    }

    private void createEditText(int size) {
        layout.removeAllViews();
        if(size>0) {
            //get it back using their index and make this list global
            List<EditText> edtList = new ArrayList<EditText>(size);
            for (int i = 0; i < size; i++) {
            EditText edt = new EditText(context);
            edt.setHint("Image "+ i + " Description");
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.weight = 1.0f;
            lp2.bottomMargin = 15;
            edt.setLayoutParams(lp2);
            layout.addView(edt);
            edtList.add(edt);
            }
        }
    }

    private void setAdapter(ArrayList<String> newMe) {
            imageAdapter = new StudentSelectedImageAssgnAdapter(getContext());
            imageAdapter.setItems(newMe);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true));
            recyclerView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
    }

        private void requestStoragePermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
                }
                if (!(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                }
            }else {
                if (!(getActivity().getPackageManager().checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
                }
                if (!(getContext().getPackageManager().checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                }
            }
        }

        private boolean checkTrue(){
            requestStoragePermission();
            return  true;
        }
}
