package com.elkanah.roemichs.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.models.StudentLoginEntity;
import com.elkanah.roemichs.db.repository.LoginRepository;
import com.elkanah.roemichs.network.JsonResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginViewModel extends AndroidViewModel {
   public MutableLiveData<JsonResponse> response = new MutableLiveData<>();
   private LoginRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = LoginRepository.getInstance(application);
    }

    public void authenticate(TextInputEditText edtStudentParentID, TextInputEditText edtStudentParentPass, String requestCode) {
        String username = Objects.requireNonNull(edtStudentParentID.getText()).toString();
        String password = Objects.requireNonNull(edtStudentParentPass.getText()).toString();
        if(validateInputs(username, password)){
            StudentLoginEntity studentEntity = new StudentLoginEntity(username, password);
            repository.authenticate(studentEntity, response, requestCode);
        }

    }

    private boolean validateInputs(String username, String password){
        return repository.validateInputs(username,password);
    }

    public boolean validateResponse(String jsonMessage) {
    return repository.validateResponse(jsonMessage);
    }
}
