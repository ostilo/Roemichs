package com.elkanah.roemichs.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.firebase.client.annotations.NotNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elkanah.roemichs.utils.CommonUtils.isDoubleClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAssignmentFragment extends Fragment implements View.OnClickListener{

    FloatingActionButton fab;
    LinearLayout pixLinLayout;
    private List<String> imagePaths;
    private ArrayList<String> returnValue;
    private int imageCount = 5;
    private int pixRequestCode = 100;
    private Toolbar toolbar;

    public CreateAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_assignment, container, false);

        fab=view.findViewById(R.id.addImagebtnCrtAsgmt);
        fab.setOnClickListener(this);

        pixLinLayout = view.findViewById(R.id.addImageLinearLayoutCrtAsgmt);
        imagePaths = new ArrayList<>();
        returnValue = new ArrayList<>();
        toolbar=view.findViewById(R.id.toolbarCrtAsgmt);
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
                Options options = Options.init()
                        .setRequestCode(pixRequestCode)
                        .setCount(imageCount)
                        .setFrontfacing(false)
                        .setImageQuality(ImageQuality.REGULAR)
                        .setPreSelectedUrls(returnValue)
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                        .setPath("/pix/images");

                Pix.start(getActivity(), options);
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

    @NotNull
    private ImageView createImageFunction(String imagePath) {
        ImageView image = new ImageView(getActivity());
        image.setLayoutParams(new ViewGroup.LayoutParams(150,150));
        image.setPaddingRelative(4,4,4,4);
        image.setMaxHeight(100);
        image.setMaxWidth(100);
        image.setMinimumHeight(100);
        image.setMaxHeight(100);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        Bitmap theImage = BitmapFactory.decodeFile(imagePath);
        image.setImageBitmap(theImage);
        return image;
    }

    private void loadImage(ArrayList<String> returnValue) {
        for(int i=0;i<returnValue.size();i++)
        {
            ImageView image = createImageFunction(returnValue.get(i));
            pixLinLayout.addView(image);
            imagePaths.add(returnValue.get(i));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == pixRequestCode) {
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                imageCount = imageCount - returnValue.size();
                if (imageCount == 0) {
                    fab.setEnabled(false);
                    Toast.makeText(getContext(), "Maximum number of image(s) selected", Toast.LENGTH_LONG).show();
                }
                    loadImage(returnValue);
            }
        }
    }

    private void clearImageFunction() {
        if (pixLinLayout !=null) {
            pixLinLayout.removeAllViews();
            imageCount = 5;
            fab.setEnabled(true);
            imagePaths.clear();
        }
    }
}
