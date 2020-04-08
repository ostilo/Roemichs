package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.AssignmentModel;
import com.elkanah.roemichs.db.models.SubjectModel;

import java.util.ArrayList;
import java.util.List;

public class StudentAssignmentAdapter extends RecyclerView.Adapter<StudentAssignmentAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<AssignmentModel> assignmentModels = AssignmentModel.getAssignments();
    private List<AssignmentModel> subjectFull;
    public StudentAssignmentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        subjectFull = new ArrayList<>(assignmentModels);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v = inflater.inflate(R.layout.student_assignment_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvSubDate.setText(assignmentModels.get(position).getSubmissionDate());
            holder.tvAssgnTitle.setText(assignmentModels.get(position).getAssgnTitle());
            holder.tvAssgnTeacher.setText(assignmentModels.get(position).getTeachersName());

    }

    @Override
    public int getItemCount() {
        return assignmentModels == null ? 0 : assignmentModels.size();
    }

    public Filter getFilter() {
        return filterSubject;
    }

        private Filter filterSubject  = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AssignmentModel> filterList = new ArrayList<>();
                if(constraint == null || constraint.length() == 0){
                    filterList.addAll(subjectFull);

                }else{
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    try {if(filterPattern != null){
                        for(AssignmentModel sub : subjectFull){
                            if(sub.getAssgnTitle().toLowerCase().contains(filterPattern)){
                                filterList.add(sub);
                            }
                        }
                    }

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filterList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                assignmentModels.clear();
                assignmentModels.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubDate;
        TextView tvAssgnTitle;
        TextView tvAssgnTeacher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubDate = itemView.findViewById(R.id.textView23);
            tvAssgnTeacher = itemView.findViewById(R.id.textView25);
            tvAssgnTitle = itemView.findViewById(R.id.textView21);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ass", assignmentModels.get(getAdapterPosition()));
                    Navigation.findNavController(v).navigate(R.id.action_studentAssignmentFragment_to_studentAssignmentDetailsFragment,bundle);
                }
            });


        }
    }
}
