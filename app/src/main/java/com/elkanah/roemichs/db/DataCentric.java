package com.elkanah.roemichs.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.dao.ClassDao;
import com.elkanah.roemichs.db.dao.SubjectDao;
import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.models.SubjectDaoModel;
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
  private static ClassDao classDao;
  private static SubjectDao subjectDao;
  private static final Object LOCK = new Object();
  private static NetworkUtils networkUtils;
  private String rawData;
  private static RoemichsDatabase roemichsDatabase;


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
    classDao = roemichsDatabase.sessionDao();
    subjectDao = roemichsDatabase.subjectDao();
  }

  public LiveData<List<ClassModel>> getRoomClassList() {
    return classDao.getAll();
  }

  public void getClassListOnline(String requestCode, MutableLiveData<JsonResponse> response) {
    ex.execute(()->{
      JsonResponse jsonResponse = networkUtils.makeApiCall("", " ", HTTPMethods.POST.toString(), requestCode );
      response.postValue(jsonResponse);
    });
  }

  public void insertClassToDB(List<ClassModel> classModels) {
    ex.execute(() -> {
      classDao.insertAll(classModels);
    });
  }

    public LiveData<List<SubjectDaoModel>> getRoomSubjectList() {
      return  subjectDao.getAll();
    }

  public void getSubjectOnline(String requestCode, MutableLiveData<JsonResponse> response) {
    ex.execute(()->{
      JsonResponse jsonResponse = networkUtils.makeApiCall("", " ", HTTPMethods.POST.toString(), requestCode );
      response.postValue(jsonResponse);
    });
  }

  public void insertSubjectToDB(List<SubjectDaoModel> subjectDaoModels) {
    ex.execute(() -> {
      subjectDao.insertAll(subjectDaoModels);
    });
  }
}
