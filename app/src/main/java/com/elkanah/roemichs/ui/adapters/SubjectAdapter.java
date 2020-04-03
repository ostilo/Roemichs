package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.SubjectModel;

import java.util.List;
import java.util.Random;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
   private List<SubjectModel> subjectList = SubjectModel.mySubjects();
   private Context context;
   private LayoutInflater inflater;
   private List<String> newColors = SubjectModel.getColors();

    public SubjectAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
    public int getItemCount() {
        return subjectList == null ? 0 : subjectList.size();
    }

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
                    Navigation.findNavController(itemView).navigate(R.id.action_subjectFragment2_to_noteFragment);

                }
            });
        }

    }
}
