package com.elkanah.roemichs.roemichsService;

import android.app.Application;

import com.elkanah.roemichs.BuildConfig;
import com.elkanah.roemichs.db.RoemichsDatabase;
import com.elkanah.roemichs.db.models.ForceUpdate_entity;
import com.elkanah.roemichs.network.HTTPMethods;
import com.elkanah.roemichs.network.JsonResponse;
import com.elkanah.roemichs.network.NetworkUtils;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckSubjectClassState {

    private static CheckSubjectClassState INSTANCE;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static Application app;
    private static Gson gson = new Gson();
    private static NetworkUtils networkUtils;
    private static RoemichsDatabase roemichsDatabase;
    private ForceUpdate_entity newObject;

    private CheckSubjectClassState(){ }


    public static CheckSubjectClassState getInstance(Application application){
        if(INSTANCE == null){
         INSTANCE = new CheckSubjectClassState();
         roemichsDatabase = RoemichsDatabase.getInstance(application);
         networkUtils = NetworkUtils.getInstance(application);
         app = application;

        }
        return INSTANCE;
    }

    private void checkAuthState(){
        executor.execute(()->{
            versionCheckUpload();
        });
    }

    private void versionCheckUpload() {
        JsonResponse response = NetworkUtils.getInstance(app).makeApiCall("","getUpdaterMobileApi", HTTPMethods.GET.toString(),"11");
        if(response != null && (response.code != null && response.code.equals("00"))){
            compareVersionUpdate(response);
        }
    }

    private void compareVersionUpdate(JsonResponse response) {
        if(response != null){
            JsonResponse mresponse = gson.fromJson(response.jsonMessage, JsonResponse.class);
           if(mresponse != null && (response.code != null && mresponse.code.equals("00"))){
               ForceUpdate_entity entity = gson.fromJson(mresponse.jsonMessage,ForceUpdate_entity.class);
               if(entity.getVersion() > getAppVersion()){
                   ForceUpdate_entity object = roemichsDatabase.updateDao().entity();
                   if (object == null) {
                       setDefaultRoomValues(object, entity.getLink());
                       newObject = roemichsDatabase.updateDao().entity();
                       if (newObject.getUpdateStatus() == 0) {
                                newObject.setUpdateStatus(1);
                                roemichsDatabase.updateDao().InsertForceUpdate(newObject);
                               //todo set another value to room to block the UI
                               //downloadFlag = downloadUpdate();

                       }
                   }else if (object.getUpdateStatus() == 0 ){ //&& object.getVersion()!=entity.getVersion()
                       //todo set another value to room to block the UI
                          // downloadFlag = downloadUpdate();
                       newObject.setUpdateStatus(1);
                       roemichsDatabase.updateDao().InsertForceUpdate(newObject);
                   }
               }
           }
        }
    }
    private void setDefaultRoomValues(ForceUpdate_entity object, String link) {
        if(object==null){
            object = new ForceUpdate_entity(1,getAppVersion(),"",0,link);
        }else{
            object.setUpdateID(1);
            object.setUpdateStatus(0);
            object.setLink(link);
            object.setVersion(getAppVersion());
        }
        roemichsDatabase.updateDao().InsertForceUpdate(object);

    }
    private double getAppVersion() {return Double.parseDouble(BuildConfig.VERSION_NAME);}
}
