package com.elkanah.roemichs.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.elkanah.roemichs.db.DataCentric;
import com.elkanah.roemichs.db.RoemichsDatabase;
import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.models.SubjectDaoModel;
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

    public LiveData<List<ClassModel>> getRoomClassList() {
        return mproxy.getRoomClassList();
    }

    public void getClassListOnline(MutableLiveData<JsonResponse> jsonResponse, String requestCode) {
        mproxy.getClassListOnline(requestCode, jsonResponse);
    }

    public boolean validateClassList(String jsonMessage) {
        boolean flag = false;
        Type listType = new TypeToken<ArrayList<ClassModel>>(){}.getType();
        List<ClassModel> classModels = gson.fromJson(jsonMessage, listType);
        if (classModels != null && classModels.size() > 0){
            flag = true;
            insertClassToDB(classModels);
        }
        return flag;
    }

    private void insertClassToDB(List<ClassModel> classModels) {
        mproxy.insertClassToDB(classModels);
    }

    public LiveData<List<SubjectDaoModel>> getRoomSubjectList() {
        //SubjectDaoModel
        return mproxy.getRoomSubjectList();
    }

    public void getSubjectListOnline(MutableLiveData<JsonResponse> jsonResponse, String requestCode) {
        mproxy.getSubjectOnline(requestCode, jsonResponse);
    }

    public boolean validateSubject(String jsonMessage) {
        boolean flag = false;
        Type listType = new TypeToken<ArrayList<SubjectDaoModel>>(){}.getType();
        List<SubjectDaoModel> subjectDaoModels = gson.fromJson(jsonMessage, listType);
        if (subjectDaoModels != null && subjectDaoModels.size() > 0){
            flag = true;
            insertSubjectToDB(subjectDaoModels);
        }
        return flag;
    }

    private void insertSubjectToDB(List<SubjectDaoModel> subjectDaoModels) {
        mproxy.insertSubjectToDB(subjectDaoModels);
    }
}
