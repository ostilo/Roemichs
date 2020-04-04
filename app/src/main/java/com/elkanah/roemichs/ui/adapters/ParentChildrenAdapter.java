package com.elkanah.roemichs.ui.adapters;

import android.annotation.SuppressLint;
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
import com.elkanah.roemichs.db.models.ParentModel;
import com.elkanah.roemichs.db.models.SubjectModel;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class ParentChildrenAdapter extends RecyclerView.Adapter<ParentChildrenAdapter.Viewholder> {

    private Context context;
    private List<ParentModel> parentModels = ParentModel.getParentModel();
    private LayoutInflater inflater;
    private List<String> newColors = SubjectModel.getColors();

    public ParentChildrenAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.parent_children_list_card, parent, false);
        return new Viewholder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            holder.tvName.setText(parentModels.get(position).getModelList().getSurname()+" "+ parentModels.get(position).getModelList().getFirst_name());
            holder.tvNameShort.setText(parentModels.get(position).getModelList().getFirst_name().substring(0,1));
            Random r = new Random();
        int i1 = r.nextInt(3- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(newColors.get(i1)));
        holder.tvNameShort.setBackground(draw);
    }

    @Override
    public int getItemCount() {
        return parentModels == null ?0 : parentModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNameShort;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvChildName);
            tvNameShort = itemView.findViewById(R.id.tvChildShort);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("sp",parentModels.get(getAdapterPosition()));
                    Navigation.findNavController(itemView).navigate(R.id.action_parentChildrenFragment_to_parentChildrenDetailsFragment,bundle);
                }
            });
        }
    }
}
