package com.elkanah.roemichs.db.repository;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.DataCentric;
import com.elkanah.roemichs.db.models.StudentLoginEntity;
import com.elkanah.roemichs.network.JsonResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class LoginRepository {
    private static LoginRepository INSTANCE;
    private static DataCentric dataCentric;
    private static Gson gson;
    private static Application app;


    private  LoginRepository(){}


    public static LoginRepository getInstance(Application application){
        if(INSTANCE == null){
            INSTANCE = new LoginRepository();
            dataCentric = DataCentric.getInstance(application);
            app = application;
            gson = new Gson();
        }
        return INSTANCE;
    }

    public boolean validateInputs(String username, String password) {
        boolean flag = true;
        if (TextUtils.isEmpty(username)){

            flag = false;
        } else{

        }
        if (TextUtils.isEmpty(password)){

            flag = false;
        } else {

        }
        return flag;

    }


    public void authenticate(StudentLoginEntity studentEntity, MutableLiveData<JsonResponse> response, String requestCode) {
        dataCentric.authenticateUser(studentEntity, response, requestCode);
    }

    public boolean validateResponse(String jsonMessage) {
        boolean flag  = false;

        return flag;
    }
}
