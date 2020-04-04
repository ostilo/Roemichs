package com.elkanah.roemichs.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.repository.ClassroomRepository;
import com.elkanah.roemichs.network.JsonResponse;

import java.util.List;

public class CreateClassroomViewModel extends AndroidViewModel {
    public LiveData<List<ClassModel>> listClass;
    private ClassroomRepository repository;
    public MutableLiveData<JsonResponse> jsonResponse;

    public CreateClassroomViewModel(@NonNull Application application) {
        super(application);
        repository=ClassroomRepository.getOurInstance(application);
        jsonResponse=new MutableLiveData<>();
        listClass = repository.getRoomClassList();
    }

    public void getSessionListOnline(String requestCode) {
        repository.getClassListOnline(jsonResponse, requestCode);
    }

    public boolean validateSessionList(String jsonMessage) {
        return repository.validateClassList(jsonMessage);
    }

    // TODO: Implement the ViewModel
}
