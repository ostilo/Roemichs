package com.elkanah.roemichs.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.elkanah.roemichs.db.DataCentric;
import com.elkanah.roemichs.db.RoemichsDatabase;
import com.elkanah.roemichs.db.models.SessionModel;
import com.elkanah.roemichs.network.JsonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClassroomRepository {

    private static ClassroomRepository ourInstance;
    private static Context cx;
    private static DataCentric mproxy;
    private static Gson gson;
    private static RoomDatabase eBankingDatabase;

    public static ClassroomRepository getOurInstance(Application application) {
        if (ourInstance == null){
            ourInstance = new ClassroomRepository();
            cx = application;
            mproxy = DataCentric.getInstance(application);
            gson = new Gson();
            eBankingDatabase = RoemichsDatabase.getInstance(application);
        }
        return ourInstance;
    }

    public LiveData<List<SessionModel>> getListOfSession() {
        return mproxy.getSessionList();
    }

    public void getSessionListOnline(MutableLiveData<JsonResponse> jsonResponse, String requestCode) {
        mproxy.getSessionListOnline(requestCode, jsonResponse);
    }

    public boolean validateSessionList(String jsonMessage) {
        boolean flag = false;
        Type listType = new TypeToken<ArrayList<SessionModel>>(){}.getType();
        List<SessionModel> sessionModels = gson.fromJson(jsonMessage, listType);
        if (sessionModels != null && sessionModels.size() > 0){
            flag = true;
            insertSessionToDB(sessionModels);
        }
        return flag;
    }

    private void insertSessionToDB(List<SessionModel> sessionModels) {
        mproxy.insertSessionToDB(sessionModels);
    }

}
