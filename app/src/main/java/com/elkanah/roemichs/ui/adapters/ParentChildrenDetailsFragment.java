package com.elkanah.roemichs.ui.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.ParentModel;
import com.elkanah.roemichs.db.models.SubjectModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentChildrenDetailsFragment extends Fragment {

    private CarouselView carouselView;

    public ParentChildrenDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_children_details, container, false);
        carouselView = v.findViewById(R.id.carouselView_psd);
        TextView tvStdFName = v.findViewById(R.id.txtDPFirstNameProfile);
        TextView tvStdSName = v.findViewById(R.id.txtDVSSurnameProfile);
        TextView tvStdClass = v.findViewById(R.id.txtStudentClassProfile);
        TextView tvStdAcct = v.findViewById(R.id.txtDateOfBirth);
        TextView tvStdCT = v.findViewById(R.id.topj);
        TextView tvStdRrk = v.findViewById(R.id.textView34);
        inflateCarousel();

        if(getArguments() != null){
           ParentModel model = getArguments().getParcelable("ass");
            if(model != null){
                tvStdFName.setText(model.getModelList().getFirst_name());
                tvStdSName.setText(model.getModelList().getSurname());
                tvStdAcct.setText(model.getAcctBalance());
                tvStdClass.setText(model.getModelList().getStudent_class());

            }
        }

        return v;
    }
    private  void inflateCarousel(){
        carouselView.setPageCount(SubjectModel.getCarouselArray().length);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(SubjectModel.getCarouselArray()[position]);
            }
        };
        carouselView.setImageListener(imageListener);
    }
}
