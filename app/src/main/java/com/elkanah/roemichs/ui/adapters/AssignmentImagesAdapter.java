package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.SubjectModel;
import com.elkanah.roemichs.db.view.AssignmentResponseImageData;

import java.util.List;
import java.util.Random;

public class AssignmentImagesAdapter extends RecyclerView.Adapter<AssignmentImagesAdapter.ViewHolder> {
    private Context context;
    private List<AssignmentResponseImageData> imageData = AssignmentResponseImageData.getAssignmentImages();
    private LayoutInflater inflater;
    private List<String> newColors = SubjectModel.getColors();

    public AssignmentImagesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AssignmentImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.assignment_images_list_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentImagesAdapter.ViewHolder holder, int position) {
  holder.tvImageStatus.setText(imageData.get(position).getItemImageStatus());
  holder.imgAssImage.setImageResource(imageData.get(position).getImages());

    }

    @Override
    public int getItemCount() {
        return imageData == null ? 0 : imageData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView tvImageStatus;
      ImageView imgAssImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImageStatus = itemView.findViewById(R.id.tim);
            imgAssImage = itemView.findViewById(R.id.tiv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("me",imageData.get(getAdapterPosition()));
                    Navigation.findNavController(itemView).navigate(R.id.action_studentAssignmentDetailsFragment_to_assignmentImageDetailsFragment, bundle);
                }
            });

        }
    }



}
