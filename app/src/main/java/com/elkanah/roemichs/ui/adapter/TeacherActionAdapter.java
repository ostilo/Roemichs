package com.elkanah.roemichs.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.ActionModel;

import java.util.List;

public class TeacherActionAdapter extends RecyclerView.Adapter<TeacherActionAdapter.ViewHolder> {
private List<ActionModel> actionModelList;
private Context context;
private LayoutInflater inflater;
//        private OnActionSelected actionSelected;

public TeacherActionAdapter(Context context, List<ActionModel> actionModelList) {
        this.context = context;
        this.actionModelList = actionModelList;
        inflater = LayoutInflater.from(context);
//            actionSelected = (OnActionSelected) context;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionModel action = actionModelList.get(position);
        holder.actionText.setText(action.text);
        holder.iconImage.setImageResource(action.image);
        holder.position = position;
        }

@Override
public int getItemCount() {
        return actionModelList != null ? actionModelList.size() : 0;
        }

class ViewHolder extends RecyclerView.ViewHolder {
    TextView actionText;
    ImageView iconImage;
    private int position;

    ViewHolder(@NonNull View itemView) {
        super(itemView);
        iconImage = itemView.findViewById(R.id.actionImage);
        actionText = itemView.findViewById(R.id.actionText);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionModelList.get(position).text.equals("Note")){
                    Navigation.findNavController(itemView).navigate(R.id.action_student_Dashboard_to_subjectFragment2);
                }
                if(actionModelList.get(position).text.equals("Assignment")){
                    Navigation.findNavController(itemView).navigate(R.id.action_student_Dashboard_to_subjectFragment2);
                }
            }
        });

    }
}
    }

