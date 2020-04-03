package com.elkanah.roemichs.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elkanah.roemichs.db.models.SessionModel;
import com.elkanah.roemichs.db.repository.ClassroomRepository;
import com.elkanah.roemichs.network.JsonResponse;

import java.util.List;

public class JoinClassroomViewModel extends AndroidViewModel {

    public LiveData<List<SessionModel>> listSession;
    private ClassroomRepository repository;
    public MutableLiveData<JsonResponse> jsonResponse;

    public JoinClassroomViewModel(@NonNull Application application) {
        super(application);
        repository=ClassroomRepository.getOurInstance(application);
        jsonResponse=new MutableLiveData<>();
        listSession = repository.getListOfSession();
    }

    public void getSessionListOnline(String requestCode) {
        repository.getSessionListOnline(jsonResponse, requestCode);
    }

    public boolean validateSessionList(String jsonMessage) {
        return repository.validateSessionList(jsonMessage);
    }

}
