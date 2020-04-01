package com.elkanah.roemichs.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.ContentModel;

import java.util.List;
    public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
        private List<ContentModel> modelList;
        private Context context;
        private LayoutInflater inflater;
//        private OnActionSelected actionSelected;

        public ContentAdapter(Context context, List<ContentModel> list) {
            this.context = context;
            this.modelList = list;
            inflater = LayoutInflater.from(context);
//            actionSelected = (OnActionSelected) context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.dashboard_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ContentModel model = modelList.get(position);
            holder.actionText.setText(model.text);
            holder.iconImage.setImageResource(model.image);
            holder.description.setText(model.description);
            holder.position = position;
        }

        @Override
        public int getItemCount() {
            return modelList != null ? modelList.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView actionText;
            ImageView iconImage;
            TextView description;
            private int position;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                iconImage = itemView.findViewById(R.id.contentImage);
                actionText = itemView.findViewById(R.id.contentText);
                description = itemView.findViewById(R.id.contentDesc);

            }
        }
    }


