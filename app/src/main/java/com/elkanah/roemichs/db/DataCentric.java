package com.elkanah.roemichs.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.dao.ClassTypeDao;
import com.elkanah.roemichs.db.dao.SessionDao;
import com.elkanah.roemichs.db.models.ClassType;
import com.elkanah.roemichs.db.models.SessionModel;
import com.elkanah.roemichs.db.models.StudentLoginEntity;
import com.elkanah.roemichs.db.repository.Constants;
import com.elkanah.roemichs.network.HTTPMethods;
import com.elkanah.roemichs.network.JsonResponse;
import com.elkanah.roemichs.network.NetworkUtils;
import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataCentric {
  private static DataCentric ourInstance;
  private static Application application;
  private static Executor ex;
  private static Gson gson;
  private static SessionDao sessionDao;
  private static final Object LOCK = new Object();
  private static NetworkUtils networkUtils;
  private String rawData;
  private static RoemichsDatabase roemichsDatabase;
  private static ClassTypeDao classTypeDao;


  private DataCentric(){}

  public static DataCentric getInstance(Application application){
    if (ourInstance == null){
      synchronized (LOCK){
        if (ourInstance == null){
          initialised(application);
        }
      }
    }
    return ourInstance;
  }

  private static void initialised(Application app){
    ourInstance = new DataCentric();
    application = app;
    ex = Executors.newSingleThreadExecutor();
    gson = new Gson();
    networkUtils = NetworkUtils.getInstance(app);
    networkUtils.setBaseUrl(Constants.BASE_URL_LIVE);
    roemichsDatabase = RoemichsDatabase.getInstance(app);
    sessionDao = roemichsDatabase.sessionDao();
    classTypeDao = roemichsDatabase.classTypeDao();
  }

  public LiveData<List<SessionModel>> getSessionList() {
    return sessionDao.getAll();
  }

  public void getSessionListOnline(String requestCode, MutableLiveData<JsonResponse> response) {
    ex.execute(()->{
      JsonResponse jsonResponse = networkUtils.makeApiCall("", " ", HTTPMethods.POST.toString(), requestCode );
      response.postValue(jsonResponse);
    });
  }

  public void insertSessionToDB(List<SessionModel> sessionModels) {
    ex.execute(() -> {
      sessionDao.insertAll(sessionModels);
    });
  }

    public void authenticateUser(StudentLoginEntity studentEntity, MutableLiveData<JsonResponse> response, String requestCode) {
    RoemichsDatabase.databaseWriteExecutor.execute(()->{
      String studentData = gson.toJson(studentEntity);
      String reqData = "userDetails=" + studentData;
      JsonResponse jsonResponse = networkUtils.makeApiCall(reqData,"ValidateUser",HTTPMethods.POST.toString(),requestCode);
         response.postValue(jsonResponse);
    });
    }

    public void getClassType(String requestCode, MutableLiveData<JsonResponse> response) {
      RoemichsDatabase.databaseWriteExecutor.execute(()->{
        JsonResponse jsonResponse  = networkUtils.makeApiCall("","custitle",HTTPMethods.POST.toString(),requestCode);
        response.postValue(jsonResponse);
      });
    }

  public LiveData<List<ClassType>> getClassTypeLocal() {
    return classTypeDao.getAll();
  }
}
