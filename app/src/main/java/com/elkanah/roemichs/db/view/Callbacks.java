package com.elkanah.roemichs.db.view;

import com.elkanah.roemichs.db.models.NoteModel;

import java.util.List;

public interface Callbacks {
    void sendSubjects(List<NoteModel> noteModels);
    void loadSub(String noteId);
}
