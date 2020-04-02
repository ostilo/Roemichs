package com.elkanah.roemichs.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class NetworkUtils {
  private String BASE_URL;
  private static volatile NetworkUtils INSTANCE;
  private Application application;
  private JsonResponse  jsonResponse = new JsonResponse();


  private NetworkUtils() {
  }

  private NetworkUtils(Application application){
    this.application = application;
  }

  public static NetworkUtils getInstance(Application application) {
    if (INSTANCE == null) {
      synchronized (NetworkUtils.class){
        if (INSTANCE == null) {
          INSTANCE = new NetworkUtils(application);
        }
      }
    }
    return INSTANCE;
  }

  public void setBaseUrl(String BASE_URL){
    if (!BASE_URL.isEmpty()){
      this.BASE_URL = BASE_URL;
    }
  }

  public JsonResponse makeApiCall(String data, String actionName, String method, String requestCode) {
    if (networkConnectivityState()){
      try {
        jsonResponse.requestCode = requestCode;
        jsonResponse.code = "-01"; jsonResponse.msg = "Invalid input";
        String url = UrlString(actionName);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout((int) TimeUnit.MINUTES.toMillis(2));

        try(OutputStream os = urlConnection.getOutputStream()) {
          byte[] input = data.getBytes(StandardCharsets.UTF_8);
          os.write(input, 0, input.length);
        }

        int responseCode = urlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){

          BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
          StringBuilder responseWriter = new StringBuilder();
          String inputLine;

          while ((inputLine = in.readLine()) != null) {
            responseWriter.append(inputLine);
          }

          in.close();
          if ( responseWriter.length() != 0 && !TextUtils.isEmpty(responseWriter.toString())) {
            jsonResponse.code = "00"; jsonResponse.jsonMessage = responseWriter.toString();
          }
        } else{
          jsonResponse.msg = String.format("Error code %s", responseCode);
        }
      }
      catch (Exception ex){
        if (ex.getMessage()!= null && ex.getMessage().contains("Unable to resolve host")) {
          jsonResponse.msg = "Unable to connect to the internet";
        } else {
          jsonResponse.msg = ex.getMessage();
        }
      }
    }else{try {
      jsonResponse.code = "-02";
      jsonResponse.msg = "No Internet Access";
    }catch (Exception e){e.printStackTrace();}
    }

    return jsonResponse;
  }


  private String UrlString(String actionName){
    Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(actionName).build();
    return  uri.toString();
  }

  private boolean networkConnectivityState(){
    ConnectivityManager connectivityManager = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    assert connectivityManager != null;
    if ( Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
      return true;
    }
    return  false;
  }

}
