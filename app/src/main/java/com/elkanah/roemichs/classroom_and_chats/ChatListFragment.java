package com.elkanah.roemichs.classroom_and_chats;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elkanah.roemichs.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static com.elkanah.roemichs.classroom_and_chats.Constants.CLASSROOM_TAG;
import static com.elkanah.roemichs.classroom_and_chats.Constants.FIREBASE_USER_LIST_URL;

public class ChatListFragment extends Fragment implements View.OnClickListener{

    private ChatListViewModel mViewModel;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    Context context;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.chat_list_fragment, container, false);

        context=getContext();

        usersList = view.findViewById(R.id.usersList);
        noUsersText = view.findViewById(R.id.noUsersText);

        pd = new ProgressDialog(context);
//        pd.setMessage("Loading...");
//        pd.show();
//
//        loadChatList();

        final EditText editText=view.findViewById(R.id.editText);
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString().trim())){
                    Toast.makeText(context, "Enter student or teacher", Toast.LENGTH_SHORT).show();
                }else {
                    if(editText.getText().toString().trim().toLowerCase().equals("teacher")){
                        loadFragment(ClassroomFragment.newInstance("teacher", "student"), "class");
                    }else if(editText.getText().toString().trim().toLowerCase().equals("student")){
                        loadFragment(ClassroomFragment.newInstance("student", "teacher"), "class");
                    }
                    else {
                        Toast.makeText(context, "Enter student or teacher", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    private void loadChatList() {
        StringRequest request = new StringRequest(Request.Method.GET, FIREBASE_USER_LIST_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, " " + volleyError, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                   loadFragment(new ClassroomFragment(), CLASSROOM_TAG);
            }
        });

    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

}
