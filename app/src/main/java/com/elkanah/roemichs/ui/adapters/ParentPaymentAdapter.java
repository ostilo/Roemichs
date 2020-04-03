package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.tvDescripShort.setText(modelList.get(position).getDescription().substring(0,1));
        if(modelList.get(position).getStatus() == 1){
            holder.tvErrprChecker.setBackground(context.getResources().getDrawable(R.drawable.parent_payment_succes_bg));
            holder.btnRetry.setVisibility(View.INVISIBLE);
        }else if(modelList.get(position).getStatus() == 1){
            holder.tvErrprChecker.setBackground(context.getResources().getDrawable(R.drawable.payment_error_bg));
            holder.btnRetry.setVisibility(View.VISIBLE);
        }
        Random r = new Random();
        int i1 = r.nextInt(3- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(newColors.get(i1)));
        holder.tvDescripShort.setBackground(draw);




    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripShort;
        TextView tvDescrip;
        TextView tvPaymentAmt;
        TextView tvErrprChecker;
        Button btnRetry;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnRetry = itemView.findViewById(R.id.btnPaymentRetry);
            tvDescripShort = itemView.findViewById(R.id.RRtextView4);
            tvDescrip = itemView.findViewById(R.id.RRtextView);
            tvPaymentAmt = itemView.findViewById(R.id.textView38);
            tvErrprChecker = itemView.findViewById(R.id.textView39);

        }
    }
}
