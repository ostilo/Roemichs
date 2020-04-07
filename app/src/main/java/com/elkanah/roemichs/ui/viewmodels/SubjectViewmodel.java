package com.elkanah.roemichs.ui.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elkanah.roemichs.db.models.ClassType;
import com.elkanah.roemichs.db.models.SubjectModel;
import com.elkanah.roemichs.db.repository.SubjectRepository;
import com.elkanah.roemichs.network.JsonResponse;

import java.net.PortUnreachableException;
import java.util.List;

public class SubjectViewmodel  extends AndroidViewModel {
    private SubjectRepository repository;
    public MutableLiveData<JsonResponse> jsonResponse;
    public LiveData<List<ClassType>> listClassType;
    public LiveData<List<SubjectModel>> listSubjectModel;


    public SubjectViewmodel(@NonNull Application application) {
        super(application);
        repository = SubjectRepository.getOurInstance(application);
        jsonResponse = new MutableLiveData<>();
        listClassType = repository.getClassTypeLocal();

    }

    public void getClassType(String requestCode) {
        repository.getClassType(jsonResponse,requestCode);

    }

    public boolean validateClassType(String jsonMessage) {
        return repository.validateClassType(jsonMessage);
    }
}
