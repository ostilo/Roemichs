package com.elkanah.roemichs.ui.fragments;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapter.NoticeAdapter;
import com.elkanah.roemichs.ui.model.NoticeBoardModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.widget.LinearLayout.VERTICAL;


public class NotificationSlider extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;

    public NotificationSlider() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotificationSlider newInstance() {
        return new NotificationSlider();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_notification, container, false);
        recyclerView = view.findViewById(R.id.notice_recycler);
        inflateRecycler();
        return view;
    }
    private void inflateRecycler() {
        recyclerView.setAdapter(new NoticeAdapter(getContext(), getList()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(getContext().getDrawable(R.drawable.edt_bg_sub_two)));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));

    }

    private List<NoticeBoardModel> getList() {
        List<NoticeBoardModel> list = new ArrayList<>();
        list.add(new NoticeBoardModel("Covid 19 Break", "01/02/2020", "Principal", "there will be a general break, due to covid 19, all parents are to take note"));
        list.add(new NoticeBoardModel("Mid term break","23/03/2020", "Management", "Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedLorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedLorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed"));
        list.add(new NoticeBoardModel("Exam", "22/04/2020", "Anybdy", "Lorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedLorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sedLorem ipsum dolor sit amet, Quisque nisi arcu, ullamcorper sed"));
        return list;
    }
}
