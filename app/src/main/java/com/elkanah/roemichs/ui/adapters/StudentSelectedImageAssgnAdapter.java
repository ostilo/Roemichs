package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.view.Callbacks;


import java.util.ArrayList;

public class StudentSelectedImageAssgnAdapter extends RecyclerView.Adapter<StudentSelectedImageAssgnAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> items;
    private LayoutInflater inflater;
    Callbacks callbacks;

    public StudentSelectedImageAssgnAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.callbacks = (Callbacks)context;

    }



    @NonNull
    @Override
    public StudentSelectedImageAssgnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = inflater.inflate(R.layout.student_selected_item_image, parent, false);
        return new ViewHolder(v);
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StudentSelectedImageAssgnAdapter.ViewHolder holder, int position) {
        holder.itemImage.setImageBitmap(imageDecode(items.get(position)));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private AppCompatImageButton deleteBtn;
    private AppCompatImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.btn_delete_image_me);
            itemImage = itemView.findViewById(R.id.item_image_me);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.deleteImageAtPosition(true);
                }
            });
        }
    }

    private Bitmap imageDecode(String path){
        return BitmapFactory.decodeFile(path);
    }

}
