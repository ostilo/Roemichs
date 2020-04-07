package com.elkanah.roemichs.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.OptionAdapter;

import java.util.ArrayList;

public class PagingAdapter extends RecyclerView.Adapter<PagingAdapter.ViewHolder> {
private ArrayList<String> list;
private Context context;
private LayoutInflater inflater;
//        private OnActionSelected actionSelected;



    private int selectedPos= -1;

    private PagingAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(PagingAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
public PagingAdapter(Context context, int size) {
        this.context = context;
        this.list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        populateList(size);

//            actionSelected = (OnActionSelected) context;
        }

    private void populateList(int size) {
    try {
        for (int i = 1; i <= size; i++) {
            this.list.add(String.valueOf(i));
        }
    }catch (Exception e){
        Log.e("Add error", e.toString());
    }
    }

    @NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.question_count_item, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.changeToSelect(selectedPos == position ? Color.parseColor("#D85B15"): Color.parseColor("#DCDCEB"));
    String item = list.get(position);
        holder.actionText.setText(String.valueOf(item));
        holder.position = position;
        }

@Override
public int getItemCount() {
        return list != null ? list.size() : 0;
        }

class ViewHolder extends RecyclerView.ViewHolder {
    TextView actionText;
    private int position;

    ViewHolder(@NonNull View itemView) {
        super(itemView);
        actionText = itemView.findViewById(R.id.tv_number);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
//                        String val = modelList.get(getAdapterPosition()).value;
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick( position);
                        notifyItemChanged(selectedPos);
                        selectedPos = getAdapterPosition();
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
