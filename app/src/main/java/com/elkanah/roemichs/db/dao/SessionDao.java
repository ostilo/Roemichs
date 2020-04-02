package com.elkanah.roemichs.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elkanah.roemichs.db.models.SessionModel;

import java.util.List;

@Dao
public interface SessionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<SessionModel> branchCodeList);

  @Query("SELECT * FROM sessionmodel WHERE value = :value")
  SessionModel getBranchCode(String value);

  @Query("SELECT * FROM sessionmodel")
  LiveData<List<SessionModel>> getAll();

  @Query("SELECT Value FROM sessionmodel WHERE Text=:text")
  String getSingular(String text);
}
