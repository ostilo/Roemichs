package com.elkanah.roemichs.ui.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.adapters.ParentPaymentAdapter;
import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentPaymentFragment extends Fragment implements View.OnClickListener {

    final int amount_1 = 5000;
    final int amount_2 = 2500;

    String email = "example@email.com";
    String fName = "First_Name";
    String lName = "Last_Name";
    String narration = "payment for food";
    String txRef;
    String country = "NG";
    String currency = "NGN";

    final String publicKey = "[INSERT YOUR PUBLIC KEY]"; //Get your public key from your account
    final String encryptionKey = "[INSERT YOUR ENCRYPTION KEY]"; //Get your encryption key from your account

    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final String ANDROID_ID = "key_text_reply.ANDROID";
    private static final String ANDROID_ID2 = "key_text_reply.ANDROID";
    public ParentPaymentFragment() {
        // Required empty public constructor
    }
    public void makePayment(int amount){
        txRef = email +" "+  UUID.randomUUID().toString();

        /*
        Create instance of RavePayManager
         */
        new RavePayManager(this).setAmount(amount)
                .setCountry(country)
                .setCurrency(currency)
                .setEmail(email)
                .setfName(fName)
                .setlName(lName)
                .setNarration(narration)
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(txRef)
                .acceptAccountPayments(true)
                .acceptCardPayments(
                        true)
                .acceptMpesaPayments(false)
                .acceptUssdPayments(true)
                .acceptGHMobileMoneyPayments(false)
                .onStagingEnv(false).
                allowSaveCardFeature(true)
                .withTheme(R.style.DefaultPayTheme)
                .initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_payment, container, false);
          // makePayment(amount_1);
        ImageView dashMenu = v.findViewById(R.id.Pmenu_select);
        dashMenu.setOnClickListener(this);
        RecyclerView recyclerView = v.findViewById(R.id.parent_payment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ParentPaymentAdapter adapter = new ParentPaymentAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        CardView paymentCard = v.findViewById(R.id.cardView4);
        paymentCard.setOnClickListener(this);
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Gson myGson = new Gson();
                Toast.makeText(getContext(), "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(getContext(), "ERROR " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(getContext(), "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ANDROID_ID2, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Pmenu_select:
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.parent_dashboard_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if(item.getItemId() == R.id.pd_menu_logout){
                        Snackbar.make(v,"Go back to start",Snackbar.LENGTH_SHORT).show();
                    }
                    if(item.getItemId() == R.id.change_password){
                        Snackbar.make(v,"Change Password",Snackbar.LENGTH_SHORT).show();
                    }
                    if(item.getItemId() == R.id.print_account){
                        Snackbar.make(v,"Print Account Statement",Snackbar.LENGTH_SHORT).show();
                    }
                    return  false;
                });
                break;
            case R.id.cardView4:
                makePayment(amount_1);
                break;


        }
    }
}
