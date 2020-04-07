package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.TestData;
import com.elkanah.roemichs.ui.model.OptiontModel;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private String questionID;
    private ArrayList<OptiontModel> modelList;
    TestData testData;


    //////////

    private int selectedPos= -1;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    //////////

    public OptionAdapter(Context ctx, ArrayList<OptiontModel> options) {
        this.context = ctx;
        this.modelList = options;
        this.questionID = questionID;
        inflater = LayoutInflater.from(ctx);
//        optionSelect = (OptionSelect) ctx;
    }

    @NonNull
    @Override
    public OptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.option, parent, false);
        return new OptionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.changeToSelect(selectedPos == position ? Color.parseColor("#D85B15"): Color.parseColor("#DCDCEB"));
        OptiontModel model = modelList.get(position);
        holder.optionID.setText(String.valueOf(position+1));
        if(model.type.equals("IMAGE")){
            holder.optionImage.setImageBitmap(BitmapFactory.decodeFile(model.value));
        }else if (model.type.equals("TEXT")){
            holder.optionText.setText(model.value);
        }
    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionID;
        TextView optionText;
        ImageView optionImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            optionID = itemView.findViewById(R.id.tv_optionID);
            optionText = itemView.findViewById(R.id.tv_optionText);
            optionImage = itemView.findViewById(R.id.iv_optionImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                  itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                  //re-using the optionModel to pass selected option back to UI
                    if (listener != null) {
                        int position = getAdapterPosition();
//                        String val = modelList.get(getAdapterPosition()).value;
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick( position);
                            notifyItemChanged(selectedPos);
                            selectedPos =getAdapterPosition();
                            notifyItemChanged(selectedPos);
                        }
                    }
                }
            });
        }
        public void changeToSelect(int colorBackground) {
            itemView.setBackgroundColor(colorBackground);
        }
    }
}

