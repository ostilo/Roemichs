package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.SubjectModel;
import com.elkanah.roemichs.ui.adapter.ActionAdapter;
import com.elkanah.roemichs.ui.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
   private List<SubjectModel> subjectList = SubjectModel.mySubjects();
   private Context context;
   private LayoutInflater inflater;
   private List<String> newColors = SubjectModel.getColors();
   private List<SubjectModel> subjectFull;

    public SubjectAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        subjectFull = new ArrayList<>(subjectList);
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.subject_list_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(subjectList.get(position).getSubjectTitle());
        holder.tvFrameTitle.setText(subjectList.get(position).getSubjectTitle().substring(0,1));
        Random r = new Random();
        int i1 = r.nextInt(3- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(newColors.get(i1)));
        holder.tvFrameTitle.setBackground(draw);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return subjectList == null ? 0 : subjectList.size();
    }

    public Filter getFilter() {
        return  filterSubject;
    }

    private Filter filterSubject  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SubjectModel> filterList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterList.addAll(subjectFull);

            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                try {if(filterPattern != null){
                    for(SubjectModel sub : subjectFull){
                        if(sub.getSubjectTitle().toLowerCase().contains(filterPattern)){
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
            subjectList.clear();
            subjectList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvFrameTitle;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView);
            tvFrameTitle = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ContentAdapter.me == 1 ||ContentAdapter.me == 4){
                        Navigation.findNavController(itemView).navigate(R.id.action_subjectFragment2_to_noteFragment);
                    }else if(ContentAdapter.me == 3){
                        Navigation.findNavController(itemView).navigate(R.id.action_subjectFragment2_to_studentAssignmentFragment);
                    }
                }
            });
        }

    }
}
