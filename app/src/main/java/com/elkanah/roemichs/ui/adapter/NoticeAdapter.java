package com.elkanah.roemichs.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.NoticeBoardModel;
import com.elkanah.roemichs.ui.model.ReadMoreOrLess;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewModel> {
    private List<NoticeBoardModel> noticeList;
    private Context context;
    private LayoutInflater inflater;

    public NoticeAdapter(Context context, List<NoticeBoardModel> list) {
        this.noticeList = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewModel(inflater.inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewModel holder, int position) {
        NoticeBoardModel model = noticeList.get(position);
        holder.title.setText(model.title);
        holder.date.setText(model.date);
        holder.announcer.setText(model.announcer);
        holder.description.setText(model.description);
        ReadMoreOrLess.ReadMoreOrLess(holder.description, 2, "View More", true);
    }

    @Override
    public int getItemCount() {
        return noticeList != null ? noticeList.size() : 0;
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView announcer;
        TextView description;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
            announcer = itemView.findViewById(R.id.tv_announcer);
            description = itemView.findViewById(R.id.tv_description);
        }
    }
}
