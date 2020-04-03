package com.elkanah.roemichs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elkanah.roemichs.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {

    public static boolean edtTextIsEmpty(EditText edt) {
        if (TextUtils.isEmpty(edt.getText()))
            return true;
        else
            return false;
    }

    public static boolean textViewTextIsEmpty(TextView txt) {
        if (TextUtils.isEmpty(txt.getText()))
            return true;
        else
            return false;
    }


    public static String textOfEditText(EditText edt) {
        return edt.getText().toString().trim();
    }

    public static String textOfTextView(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(context.getResources().getString(R.string.app_name), text);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean stringIsEmpty(String str){
        if(str==null){
            return true;
        }else if(str.isEmpty()) {
            return true;
        }else if(str.trim().equals("")){
            return true;
        }else {
            return false;
        }
    }

    public static String toCurrencyString(int amount){
        return "\u20a6" +amount;
    }

    public static double toCurrencyInt(String amountString){
        return Integer.parseInt(amountString.substring(1));
    }

    public static String millisToDateString(long dateInMillis){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
            return formatter.format(new Date(dateInMillis));
        } catch (Exception ex){
            return null;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        boolean flag=false;
       try {
           ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
           assert connectivityManager != null;
           if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
               flag = true;
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return flag;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return " " + sdf.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return " " + sdf.format(new Date());
    }

    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return " "+sdf.format(cal.getTime());
    }

    private static boolean isRunning = false;
    private static int resetInTime = 500;
    private static int counter = 0;
    public static boolean isDoubleClick(){
        boolean flag=false;
        if (isRunning) {
            if (counter == 1)
                flag = true;
        }

        counter++;

        if (!isRunning) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
            return flag;
    }

}
