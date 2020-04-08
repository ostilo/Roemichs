package com.elkanah.roemichs.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.DataCentric;
import com.elkanah.roemichs.db.RoemichsDatabase;
import com.elkanah.roemichs.db.models.ClassType;
import com.elkanah.roemichs.network.JsonResponse;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private static SubjectRepository ourInstance;
    private static Context cx;
    private static DataCentric mProxy;
    private static Gson gson;
    private  static RoemichsDatabase roemichsDatabase;

    public static SubjectRepository getOurInstance(Application application){
        if(ourInstance == null){
            ourInstance = new SubjectRepository();
            cx = application;
            mProxy = DataCentric.getInstance(application);
            gson = new Gson();
            roemichsDatabase = RoemichsDatabase.getInstance(application);
        }
        return ourInstance;
    }


    public void getClassType(MutableLiveData<JsonResponse> jsonResponse, String requestCode) {
    mProxy.getClassType(requestCode, jsonResponse);
    }

    public LiveData<List<ClassType>> getClassTypeLocal() {
        return mProxy.getClassTypeLocal();
    }

    public boolean validateClassType(String jsonMessage) {
        boolean flag = false;
        try {
            Type listClassType = new TypeToken<ArrayList<ClassType>>(){}.getType();
            List<ClassType> classTypes = gson.fromJson(jsonMessage, listClassType);
            if(classTypes != null && classTypes.size() > 0){
                flag = true;
                insertClassType(classTypes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }
 public void insertClassType(List<ClassType> classTypes){
        mProxy.insertClassTypeToDB(classTypes);
 }

    public void fetchSubjectRecycler(String text, MutableLiveData<JsonResponse> jsonResponse, String requestCode) {
            mProxy.fetchSubjectRecycler(text,jsonResponse,requestCode);
    }
}
