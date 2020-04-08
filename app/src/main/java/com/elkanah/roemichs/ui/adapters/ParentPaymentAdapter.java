package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.models.ParentModel;
import com.elkanah.roemichs.db.models.ParentPaymentModel;
import com.elkanah.roemichs.db.models.SubjectModel;

import java.util.List;
import java.util.Random;

public class ParentPaymentAdapter extends RecyclerView.Adapter<ParentPaymentAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ParentPaymentModel> modelList = ParentPaymentModel.getPaymentList();
    private List<String> newColors = SubjectModel.getColors();

    public ParentPaymentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ParentPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.parent_dashboard_payment_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentPaymentAdapter.ViewHolder holder, int position) {
        holder.tvDescrip.setText(modelList.get(position).getDescription());
        holder.tvPaymentAmt.setText(modelList.get(position).getAmount());
        if(modelList.get(position).getStatus() == 1){
            holder.tvDescripShort.setImageResource(R.drawable.ic_check);
            holder.btnRetry.setVisibility(View.INVISIBLE);
        }else if(modelList.get(position).getStatus() == 0){
            holder.btnRetry.setVisibility(View.VISIBLE);
            holder.tvDescripShort.setImageResource(R.drawable.ic_error_black_24dp);
        }





    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton tvDescripShort;
        TextView tvDescrip;
        TextView tvPaymentAmt;
        Button btnRetry;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRetry = itemView.findViewById(R.id.btnPaymentRetry);
            tvDescripShort = itemView.findViewById(R.id.RRtextView4);
            tvDescrip = itemView.findViewById(R.id.RRtextView);
            tvPaymentAmt = itemView.findViewById(R.id.textView38);


        }
    }
}
