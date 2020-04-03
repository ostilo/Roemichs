package com.elkanah.roemichs.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.models.SubjectDaoModel;

import java.util.List;

@Dao
public interface SubjectDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<SubjectDaoModel> subjectDaoModels);

  @Query("SELECT * FROM SubjectDaoModel WHERE value = :value")
  SubjectDaoModel getBranchCode(String value);

  @Query("SELECT * FROM SubjectDaoModel")
  LiveData<List<SubjectDaoModel>> getAll();

  @Query("SELECT Value FROM SubjectDaoModel WHERE Text=:text")
  String getSingular(String text);
}
