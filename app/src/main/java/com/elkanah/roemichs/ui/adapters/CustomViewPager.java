package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.view.AssignmentResponseImageData;


import java.util.List;

public class CustomViewPager extends PagerAdapter {
    Context context;
    List<AssignmentResponseImageData> list = AssignmentResponseImageData.getAssignmentImages();

    LayoutInflater mLayoutInflayer;
    int[] mResources = {

            R.drawable.dashboardbg,

            R.drawable.dashboaard,

            R.drawable.dashboardbg,

            R.drawable.dashboaard,

            R.drawable.dashboardbg,

            R.drawable.dashboardbg

    };

    public CustomViewPager(Context context) {
        this.context = context;
        mLayoutInflayer = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
        //return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return object == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageDrawable(context.getResources().getDrawable(getImageAt(position)));
        container.addView(view);
        return view;

//        ImageView imageView = new ImageView(context);
//        int padding = context.getResources().getDimensionPixelSize(R.dimen.dp_16);
//        imageView.setPadding(padding,padding,padding,padding);
////        imageView.setScaleType(ImageView);
//        ((ViewPager)container).addView(imageView,0);
//        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    private int getImageAt(int position) {
        switch (position) {
            case 0:
                return R.drawable.dashboaard;
            case 1:
                return R.drawable.dashboardbg;
            case 2:
                return R.drawable.portriatimg;
            case 3:
                return R.drawable.profile;
            default:
                return R.drawable.landscapeimg;
        }
    }
}
