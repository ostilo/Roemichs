package com.elkanah.roemichs.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.MainActivity;
import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.StudentSelectedImageAssgnAdapter;
import com.elkanah.roemichs.ui.adapters.SubjectAdapter;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.opensooq.supernova.gligar.GligarPicker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSubmitAssignmentFragment extends Fragment implements View.OnClickListener {
    public static final int READ_PERMISSION=2;
    public static final int WRITE_PERMISSION=3;
    private static final int PICKFILE_RESULT_CODE =4 ;
    private Button btnViewSubmitted;
    private int PICKER_REQUEST_CODE = 30;
    private Button btnSubmit;
    CoordinatorLayout coordinatorLayout;
    public int imgCount;

    public RecyclerView recyclerView;
    private TextView tvImageCounter;
    private View v;
    private StudentSelectedImageAssgnAdapter imageAdapter;
    private ArrayList<String> newMe;
    private Button btnUploadDoc;
    private EditText edtDocUrl;


    public StudentSubmitAssignmentFragment() {
        // Required empty public constructor
    }

    private void doClick(ArrayList<String> item){
        try {
            imageAdapter = new StudentSelectedImageAssgnAdapter(getContext());
           // newMe.clear();
            imgCount += 1;
            newMe = item;
            checkImageCount();
        }catch (Exception e){
            Log.e("img", e.toString());
        }
    }

    private void checkImageCount() {
        tvImageCounter.setText(String.valueOf(newMe.size()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_student_submit_assignment, container, false);
        coordinatorLayout = v.findViewById(R.id.parent_frame);


        CollapsingToolbarLayout collapsingToolbarLayout = v.findViewById(R.id.collapsing_toolbar_sa);
        //collapsingToolbarLayout.setTitle("The Mighty Hand of God");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        Toolbar toolbar = v.findViewById(R.id.withdraw_toolbar_sa);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Submit Assignment");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        btnViewSubmitted = v.findViewById(R.id.button3);
        btnSubmit = v.findViewById(R.id.button4);
        FloatingActionButton floatChooseImage = v.findViewById(R.id.floatSelectPix);
        floatChooseImage.setOnClickListener(this);
        //imageView = v.findViewById(R.id.imageView3);
        setupUI(v.findViewById(R.id.parent_frame));
        recyclerView = v.findViewById(R.id.addImageScrollView);
        tvImageCounter = v.findViewById(R.id.tv_imagesCount);

        btnSubmit.setOnClickListener(this);
        btnViewSubmitted.setOnClickListener(this);

        btnUploadDoc = v.findViewById(R.id.btnUploadDocSubmtAsgmt);
        btnUploadDoc.setOnClickListener(this);

        edtDocUrl = v.findViewById(R.id.edtDocUrl);

        return v;
    }

    public void setupUI(View view){
        if(!(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        if(view instanceof  ViewGroup){
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerview = ((ViewGroup)view).getChildAt(i);
                setupUI(innerview);
            }
        }
    }

    private void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:
                btnViewSubmitted.setEnabled(false);
                break;

            case R.id.button4:
                    btnSubmit.setEnabled(true);
                    coordinatorLayout.setAlpha((float) 0.5);
                    loadAlertDailog("Are you sure you want to submit?","OK","NO");
                break;
            case R.id.floatSelectPix:
                    if(checkTrue()){
                        new GligarPicker().disableCamera(false).cameraDirect(false).requestCode(PICKER_REQUEST_CODE).withFragment(this).show();
                    }
                break;
            case R.id.btnUploadDocSubmtAsgmt:
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("*/*");
                try {
                    startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
                } catch (ActivityNotFoundException e) {
                    Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
                }
                break;
        }
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
    private void loadAlertDailog(String title, String positive, String negative){
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle(title);
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setButton(Dialog.BUTTON_POSITIVE, positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                coordinatorLayout.setAlpha((float) 1);
                dialog.dismiss();

            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                coordinatorLayout.setAlpha((float) 1);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode == Activity.RESULT_OK && requestCode == PICKER_REQUEST_CODE && data != null){
           String pathList [] = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); //return list of selected images
             if(pathList != null){
                 newMe = new ArrayList<String>();
                 for(String s : pathList)
                 {
                    newMe.add(s);
                 }
                 setAdapter(newMe);
             }
                 imgCount = pathList.length;
                 checkImageCount();
        }

        if(resultCode == Activity.RESULT_OK && requestCode == PICKFILE_RESULT_CODE && data != null) {
            //FilePath is your file as a string
            edtDocUrl.setText(data.getData().getPath());
        }
    }

    private void setAdapter(ArrayList<String> newMe) {
        imageAdapter = new StudentSelectedImageAssgnAdapter(getContext());
        imageAdapter.setItems(newMe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }

    private Bitmap imageDecode(String path){
        return BitmapFactory.decodeFile(path);
    }

}
