package com.elkanah.roemichs.ui.adapter;

import android.content.Context;
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
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.ui.model.ContentModel;
import com.elkanah.roemichs.ui.model.StudentProfileModel;

import java.util.List;
    public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
        private List<ContentModel> modelList;
        private Context context;
        private LayoutInflater inflater;
        public static int me = 0;
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
            holder.Position = position;
        }

        @Override
        public int getItemCount() {
            return modelList != null ? modelList.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView actionText;
            ImageView iconImage;
            TextView description;
            private int Position;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(R.id.contentImage);
                actionText = itemView.findViewById(R.id.contentText);
                description = itemView.findViewById(R.id.contentDesc);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if (modelList.get(getAdapterPosition()).text.equals("Test")) {
                                Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_testFragment);
                            } else if (modelList.get(getAdapterPosition()).text.equals("Subject")) {
                                me = 1;
                                Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_subjectFragment2);
                            }

                        else if(modelList.get(getAdapterPosition()).text.equals("Classroom")){
                         Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_chatListFragment);
                        }
                        else if(modelList.get(getAdapterPosition()).text.equals("Profile")){

                            Bundle bundle=new Bundle();
                            StudentProfileModel profileModel=new StudentProfileModel();
                            profileModel.setFirst_name("Ayodele");
                            profileModel.setSurname("Afolabi");
                            profileModel.setProfile_pic_url("url");
                            profileModel.setStudent_class("Year XX");
                            profileModel.setStudent_id("Ayodele123");
                            bundle.putParcelable(Constants.PROFILE_MODEL, profileModel);
                            Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_subjectFragment2);
                            //Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_profileFragment, bundle);
                            Navigation.findNavController(v).navigate(R.id.action_student_Dashboard_to_profileFragment, bundle);
                        }

                }

                });
        }
    }
    }



