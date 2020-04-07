package com.elkanah.roemichs.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.models.SubjectDaoModel;
import com.elkanah.roemichs.db.repository.ClassroomRepository;
import com.elkanah.roemichs.network.JsonResponse;

import java.util.List;

public class JoinClassroomViewModel extends AndroidViewModel {

    public LiveData<List<SubjectDaoModel>> listSession;
    private ClassroomRepository repository;
    public MutableLiveData<JsonResponse> jsonResponse;

    public JoinClassroomViewModel(@NonNull Application application) {
        super(application);
        repository=ClassroomRepository.getOurInstance(application);
        jsonResponse=new MutableLiveData<>();
        listSession = repository.getRoomSubjectList();
    }

    public void getSubjectListOnline(String requestCode) {
        repository.getSubjectListOnline(jsonResponse, requestCode);
    }

    public boolean validateClassList(String jsonMessage) {
        return repository.validateSubject(jsonMessage);
    }

}
