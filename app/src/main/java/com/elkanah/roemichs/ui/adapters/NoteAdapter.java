package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.NoteModel;
import com.elkanah.roemichs.db.models.SubjectModel;

import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<NoteModel> models = NoteModel.getNotes();
    private LayoutInflater inflater;
    private List<String> newColors = SubjectModel.getColors();
    public NoteAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = inflater.inflate(R.layout.note_list_card, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.tvNoteTitle.setText(models.get(position).getNoteTitle());
        holder.tvNoteSubTopic.setText(models.get(position).getSubTopic());
        holder.tvWeek.setText(models.get(position).getWeekNumber());
        holder.tvNoteTitleFrame.setText(models.get(position).getNoteTitle().substring(0,1));
        Random r = new Random();
        int i1 = r.nextInt(3- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(newColors.get(i1)));
        holder.tvNoteTitleFrame.setBackground(draw);

    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteSubTopic;
        TextView tvWeek;
        TextView tvNoteTitleFrame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNT);
            tvNoteSubTopic = itemView.findViewById(R.id.tv_cpservice);
            tvWeek = itemView.findViewById(R.id.textView7);
            tvNoteTitleFrame = itemView.findViewById(R.id.textView3);
                                itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Bundle bundle  = new Bundle();
                                        bundle.putParcelable("ade", models.get(getAdapterPosition()));
                                        Navigation.findNavController(v).navigate(R.id.action_noteFragment_to_noteDetailsFragment,bundle);
                                    }
                                });
        }
    }
}
